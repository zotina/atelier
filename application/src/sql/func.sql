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




-- commission 
CREATE OR REPLACE VIEW vue_commission_employe AS
SELECT 
    e.id_employe,
    e.nom as nom_employe,
    r.id_reparation,
    CAST(r.date_debut AS DATE) as date_reparation,
    r.prix as prix_reparation,
    r.id_appareil,
    ROUND(r.prix * (select valeur from commission), 2) as commission
FROM employe e
JOIN reparation_employe re ON e.id_employe = re.id_employe
JOIN reparation r ON re.id_reparation = r.id_reparation
WHERE r.prix IS NOT NULL
ORDER BY e.id_employe, r.date_debut;


CREATE OR REPLACE VIEW v_commission_employe AS
SELECT 
    e.id_employe,
    e.nom AS nom_employe,
    CAST(r.date_debut AS DATE) AS date_reparation,
    ROUND(SUM(r.prix * (SELECT valeur FROM commission)), 2) AS total_commission
FROM employe e
JOIN reparation_employe re ON e.id_employe = re.id_employe
JOIN reparation r ON re.id_reparation = r.id_reparation
WHERE r.prix IS NOT NULL
GROUP BY e.id_employe, e.nom, CAST(r.date_debut AS DATE)
ORDER BY e.id_employe, date_reparation;

