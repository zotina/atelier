-- Suppression des données de facturation
DELETE FROM paiement_facture_client;
DELETE FROM ligne_facture_client;
DELETE FROM facture_client;

-- Suppression des données de réparation
DELETE FROM reparation_reel;
DELETE FROM reparation;
DELETE FROM appareil_employe;

-- Suppression des données de pièces
DELETE FROM entree_piece;
DELETE FROM piece;
DELETE FROM fornisseur;
DELETE FROM categorie;

-- Suppression des données d'employés
DELETE FROM employe;
DELETE FROM role;
DELETE FROM etat_reparation;

-- Suppression des données d'appareils
DELETE FROM appareil;
DELETE FROM model;
DELETE FROM marque;
DELETE FROM client;

-- Réinitialisation des séquences
CALL reset_sequences();