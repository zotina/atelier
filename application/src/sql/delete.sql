DELETE FROM reparation_reel;
DELETE FROM reparation_employe;

DELETE FROM paiement_facture_client;
DELETE FROM ligne_facture_client;
DELETE FROM facture_client;
DELETE FROM retour;

DELETE FROM entree_piece;
DELETE FROM piece;

DELETE FROM employe;

DELETE FROM reparation;
DELETE FROM appareil;

DELETE FROM typa;
DELETE FROM etat_reparation;
DELETE FROM fornisseur;
DELETE FROM categorie;
DELETE FROM role;
DELETE FROM model;
DELETE FROM marque;
DELETE FROM client;

CALL reset_sequences();