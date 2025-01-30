-- Vue pour l'historique des interventions sur les appareils
CREATE OR REPLACE VIEW v_historique_interventions AS
SELECT 
    a.id_appareil,
    a.numero_serie AS numero_serie_appareil,
    a.desc_probleme AS probleme_signale,
    r.diagnostic AS diagnostic_effectue,
    r.date_debut AS date_debut_intervention,
    r.prix AS prix_intervention,
    et.libelle AS etat_reparation,
    e.id_employe,
    e.nom,
    e.email,
    e.telephone
FROM 
    appareil a
LEFT JOIN 
    reparation r ON a.id_appareil = r.id_appareil
LEFT JOIN 
    etat_reparation et ON r.id_etat = et.id_etat
LEFT JOIN 
    reparation_employe re ON r.id_reparation = re.id_reparation
LEFT JOIN 
    employe e ON re.id_employe = e.id_employe
LEFT JOIN 
    role ro ON e.id_role = ro.id_role
WHERE 
    e.id_role = 'ROL-000001' -- Filtrer uniquement pour le rôle de technicien
ORDER BY 
    a.numero_serie, r.date_debut DESC;

-- Vue pour les retours
CREATE OR REPLACE VIEW v_getretours AS 
SELECT DISTINCT 
    r.id_retour,
    r.prix_total_main_doeuvre,
    r.prix_total_piece,
    r.date_retour,
    r.id_appareil,
    a.numero_serie,
    a.est_repare,
    a.desc_probleme,
    a.date_deposition,
    t.id_typa,
    t.nom as type_libelle,
    cat.id_categorie
FROM 
    retour r
LEFT JOIN 
    appareil a ON r.id_appareil = a.id_appareil
LEFT JOIN 
    typa t ON a.id_typa = t.id_typa
LEFT JOIN 
    reparation rep ON a.id_appareil = rep.id_appareil
LEFT JOIN 
    categorie cat ON rep.id_categorie = cat.id_categorie;


CREATE OR REPLACE VIEW vue_commission_employe AS 
SELECT 
    e.id_employe,
    e.nom AS nom_employe,
    r.id_reparation,
    g.id_genre,
    CAST(r.date_debut AS DATE) AS date_reparation,
    r.prix AS prix_reparation,
    r.id_appareil,
    CASE 
        WHEN r.prix >= (SELECT min_prix FROM condition_commission) 
        THEN ROUND(r.prix * (SELECT valeur FROM commission), 2)
        ELSE 0 
    END AS commission
FROM 
    employe e 
JOIN 
    reparation_employe re ON e.id_employe = re.id_employe 
JOIN 
    reparation r ON re.id_reparation = r.id_reparation 
JOIN 
    genre g ON g.id_genre = e.id_genre
WHERE 
    r.prix IS NOT NULL
ORDER BY 
    e.id_employe, r.date_debut;


CREATE OR REPLACE FUNCTION get_total_prix_pieces(date_filtre DATE, id_appareil_filtre VARCHAR(50))
RETURNS NUMERIC(15,2) AS $$
DECLARE
    total_prix NUMERIC(15,2) := 0;
    prix_historique NUMERIC(15,2);
    prix_actuel NUMERIC(15,2);
    quantite_piece INTEGER;
    id_piece_filtre VARCHAR(50);
BEGIN
    -- Boucle sur toutes les réparations liées à l'appareil
    FOR id_piece_filtre, quantite_piece IN
        SELECT rr.id_piece, rr.quantite
        FROM reparation_reel rr
        JOIN reparation r ON rr.id_reparation = r.id_reparation
        WHERE r.id_appareil = id_appareil_filtre
    LOOP
        -- Vérifier s'il y a un historique pour la date spécifiée
        SELECT hp.prix_unitaire INTO prix_historique
        FROM histo_piece hp
        WHERE hp.id_piece = id_piece_filtre
          AND hp.changed_at <= date_filtre
        ORDER BY hp.changed_at DESC
        LIMIT 1;

        -- Si un historique est trouvé, utiliser ce prix
        IF FOUND THEN
            total_prix := total_prix + (prix_historique * quantite_piece);
        ELSE
            -- Sinon, utiliser le prix actuel de la table `piece`
            SELECT p.prix_unitaire INTO prix_actuel
            FROM piece p
            WHERE p.id_piece = id_piece_filtre;

            IF FOUND THEN
                total_prix := total_prix + (prix_actuel * quantite_piece);
            END IF;
        END IF;
    END LOOP;

    -- Retourner le total_prix
    RETURN total_prix;
END;
$$ LANGUAGE plpgsql;