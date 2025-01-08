CREATE OR REPLACE PROCEDURE reset_sequences(
    table_name TEXT DEFAULT NULL,
    start_value INT DEFAULT 1
)
LANGUAGE plpgsql
AS $$
DECLARE
    seq RECORD;       -- Pour parcourir les séquences de la base
    col RECORD;       -- Pour parcourir les colonnes d'une table spécifique
    seq_name TEXT;    -- Nom de la séquence associée à une colonne
    prefix TEXT;      -- Préfixe trouvé dans les colonnes de type varchar
BEGIN
    -- Si un nom de table est fourni
    IF table_name IS NOT NULL THEN
        RAISE NOTICE 'Réinitialisation des séquences et colonnes pour la table : %', table_name;
        FOR col IN
            SELECT column_name, data_type
            FROM information_schema.columns
            WHERE table_name = table_name
              AND table_schema = 'public'
        LOOP
            -- Vérifier si une séquence est associée à la colonne (serial/bigserial)
            SELECT pg_get_serial_sequence('public.' || table_name, col.column_name) INTO seq_name;

            IF seq_name IS NOT NULL THEN
                -- Réinitialiser la séquence associée
                EXECUTE format('ALTER SEQUENCE %I RESTART WITH %s', seq_name, start_value);
                RAISE NOTICE 'Séquence % réinitialisée avec succès à %', seq_name, start_value;
            ELSE
                -- Si aucune séquence, vérifier si la colonne contient des préfixes numériques (type varchar)
                IF col.data_type = 'character varying' OR col.data_type = 'text' THEN
                    EXECUTE format(
                        'UPDATE public.%I SET %I = regexp_replace(%I::TEXT, ''\d+$'', ''' || start_value || ''')',
                        table_name, col.column_name, col.column_name
                    );
                    RAISE NOTICE 'Colonne % mise à jour avec le nouveau suffixe : %', col.column_name, start_value;
                END IF;
            END IF;
        END LOOP;
    ELSE
        -- Si aucun nom de table n'est fourni, réinitialiser toutes les séquences dans le schéma public
        RAISE NOTICE 'Réinitialisation de toutes les séquences dans le schéma public';
        FOR seq IN
            SELECT c.oid::regclass::text AS sequence_name
            FROM pg_class c
            JOIN pg_namespace n ON n.oid = c.relnamespace
            WHERE c.relkind = 'S'  -- 'S' indique une séquence
              AND n.nspname = 'public'
        LOOP
            -- Réinitialiser chaque séquence
            EXECUTE format('ALTER SEQUENCE %I RESTART WITH %s', seq.sequence_name, start_value);
            RAISE NOTICE 'Séquence % réinitialisée avec succès à %', seq.sequence_name, start_value;
        END LOOP;
    END IF;

    -- Message de succès global
    RAISE NOTICE 'Réinitialisation terminée avec succès.';
END;
$$;
