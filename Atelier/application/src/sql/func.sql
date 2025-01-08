-- Vue pour l'historique des appareils clients
CREATE OR REPLACE VIEW historique_appareils_clients AS
SELECT 
    c.id_client,
    c.nom AS nom_client,
    c.email AS email_client,
    c.telephone AS telephone_client,
    a.numero_serie,
    a.desc_probleme,
    a.date_deposition,
    a.date_recuperation,
    CASE 
        WHEN a.estRecuperer THEN 'Oui'
        ELSE 'Non'
    END AS est_recupere,
    m.libelle AS model_appareil,
    ma.nom AS marque_appareil
FROM 
    client c
JOIN 
    appareil a ON c.id_client = a.id_client
JOIN 
    model m ON a.id_model = m.id_model
JOIN 
    marque ma ON m.id_marque = ma.id_marque
ORDER BY 
    c.id_client, a.date_deposition DESC;

-- Vue pour l'historique des interventions sur les appareils
CREATE OR REPLACE VIEW historique_interventions_appareils AS
SELECT 
    a.id_appareil AS id_appareil,
    a.numero_serie AS numero_serie_appareil,
    a.desc_probleme AS probleme_signale,
    r.diagnostic AS diagnostic_effectue,
    r.date_debut AS date_debut_intervention,
    r.prix AS prix_intervention,
    et.libelle AS etat_reparation,
    e.id_employe ,
    e.nom ,
    e.email ,
    e.telephone
FROM 
    appareil a
JOIN 
    appareil_employe ae ON a.id_appareil = ae.id_appareil
JOIN 
    reparation r ON ae.id_appareil_employe = r.id_appareil_employe
JOIN 
    etat_reparation et ON r.id_etat = et.id_etat
JOIN 
    employe e ON ae.id_employe = e.id_employe
WHERE 
    e.id_role = 'ROL-000001' -- Filtrer uniquement pour le rôle de technicien
ORDER BY 
    a.numero_serie, r.date_debut DESC;

-- Vue pour les statistiques des réparations par mois
CREATE OR REPLACE VIEW statistiques_reparations_par_mois AS
SELECT
    TO_CHAR(date_debut, 'YYYY-MM') AS mois_annee,
    COUNT(id_reparation) AS nombre_reparations
FROM
    reparation
GROUP BY
    TO_CHAR(date_debut, 'YYYY-MM')
ORDER BY
    mois_annee;
 

-- CREATE OR REPLACE VIEW historique_revenus AS
-- SELECT 
--     fc.id_facture_client,
--     fc.date_facturation,
--     fc.prix_total_pieces, -- Total des prix des pièces utilisées
--     fc.prix_total_employe, -- Total des frais de main-d'œuvre
--     fc.prix_supplementaire, -- Autres frais (s'il y en a)
--     fc.TVA, -- TVA associée
--     (fc.prix_total_pieces + fc.prix_total_employe + fc.prix_supplementaire + fc.TVA) AS total_revenu -- Total global
-- FROM 
--     facture_client fc
-- ORDER BY 
--     fc.date_facturation DESC;


-- CREATE OR REPLACE VIEW historique_depenses_pieces AS
-- SELECT 
--     rp.id_piece,
--     p.description AS piece_description,
--     SUM(rp.quantite * ep.prix_unitaire) AS total_depenses, -- Total dépensé pour cette pièce (quantité x prix unitaire d'achat)
--     SUM(rp.quantite) AS total_quantite_utilisee, -- Quantité totale de cette pièce utilisée
--     ep.prix_unitaire AS prix_unitaire_achat -- Prix unitaire d'achat pour la pièce
-- FROM 
--     reparation_reel rp
-- JOIN 
--     piece p ON rp.id_piece = p.id_piece
-- JOIN 
--     entree_piece ep ON p.id_piece = ep.id_piece
-- GROUP BY 
--     rp.id_piece, p.description, ep.prix_unitaire
-- ORDER BY 
--     total_depenses DESC;


-- CREATE OR REPLACE VIEW historique_financier AS
-- SELECT 
--     r.date_facturation,
--     COALESCE(SUM(r.total_revenu), 0) AS total_revenus,
--     COALESCE(SUM(d.total_depenses), 0) AS total_depenses,
--     COALESCE(SUM(r.total_revenu), 0) - COALESCE(SUM(d.total_depenses), 0) AS profit -- Revenus - Dépenses
-- FROM 
--     historique_revenus r
-- LEFT JOIN 
--     historique_depenses_pieces d ON r.date_facturation::DATE = d.date_entree -- Join sur les dates
-- GROUP BY 
--     r.date_facturation
-- ORDER BY 
--     r.date_facturation;
