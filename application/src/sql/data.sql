-- Client data
INSERT INTO client (id_client, email, nom, telephone, addresse)
VALUES 
('CLT-000001', 'jean.dupont@gmail.com', 'Jean Dupont', 331234, '123 Rue de Paris, France'),
('CLT-000002', 'marie.curie@yahoo.com', 'Marie Curie', 33234, '45 Rue des Lilas, France'),
('CLT-000003', 'paul.martin@outlook.com', 'Paul Martin', 33345, '10 Avenue des Champs, France'),
('CLT-000004', 'sophie.leclerc@hotmail.com', 'Sophie Leclerc', 3345678, '89 Boulevard Saint-Michel, France'),
('CLT-000005', 'luc.durand@gmail.com', 'Luc Durand', 33567890, '5 Place de la Concorde, France');

-- Marque data
INSERT INTO marque (id_marque, nom)
VALUES 
('MAR-000001', 'Dell'),
('MAR-000002', 'HP'),
('MAR-000003', 'Lenovo'),
('MAR-000004', 'Asus'),
('MAR-000005', 'Acer');

-- Model data
INSERT INTO model (id_model, libelle, id_marque)
VALUES 
('MOD-000001', 'XPS 15', 'MAR-000001'),
('MOD-000002', 'Pavilion 14', 'MAR-000002'),
('MOD-000003', 'ThinkPad X1 Carbon', 'MAR-000003'),
('MOD-000004', 'ZenBook 14', 'MAR-000004'),
('MOD-000005', 'Aspire 5', 'MAR-000005');

-- Type appareil data
INSERT INTO typa (id_typa, nom)
VALUES 
('TYPA-000001', 'Laptop'),
('TYPA-000002', 'Gameur');

-- Appareil data
INSERT INTO appareil (id_appareil, numero_serie, est_repare, desc_probleme, date_deposition, id_typa, id_client, id_model)
VALUES 
('APP-000001', 'DEL12345678', FALSE, 'Écran bleu aléatoire', '2025-01-01 10:00:00', 'TYPA-000002', 'CLT-000001', 'MOD-000001'),
('APP-000002', 'HP98765432', FALSE, 'Batterie qui ne charge pas', '2025-01-02 11:00:00', 'TYPA-000002', 'CLT-000002', 'MOD-000002'),
('APP-000003', 'LEN11223344', FALSE, 'Surchauffe au démarrage', '2025-01-03 09:30:00', 'TYPA-000002', 'CLT-000003', 'MOD-000003'),
('APP-000004', 'ASU55667788', FALSE, 'Clavier ne répond pas', '2025-01-04 14:00:00', 'TYPA-000001', 'CLT-000004', 'MOD-000004'),
('APP-000005', 'ACE99887766', FALSE, 'Problème avec la carte graphique', '2025-01-05 15:30:00', 'TYPA-000001', 'CLT-000005', 'MOD-000005');

-- Role data
INSERT INTO role (id_role, libelle, salaire_base)
VALUES 
('ROL-000001', 'Technicien', 3000.00);

-- Employe data
INSERT INTO employe (id_employe, nom, telephone, email, salaire_personnalise, date_embauche, addresse, id_role)
VALUES 
('EMP-000001', 'Jean Martin', '123456789', 'jean.martin@technicien.com', NULL, '2023-01-15', '12 Rue des Lilas, France', 'ROL-000001'),
('EMP-000002', 'Sophie Leroy', '987654321', 'sophie.leroy@technicien.com', NULL, '2022-09-20', '45 Boulevard Saint-Michel, France', 'ROL-000001'),
('EMP-000003', 'Paul Durand', '567890123', 'paul.durand@technicien.com', NULL, '2024-03-10', '78 Avenue de Paris, France', 'ROL-000001'),
('EMP-000004', 'Lucie Dupont', '678901234', 'lucie.dupont@technicien.com', NULL, '2021-06-25', '9 Place de la Concorde, France', 'ROL-000001');

-- État réparation data
INSERT INTO etat_reparation (id_etat, libelle)
VALUES 
('ETR-000001', 'En cours'),
('ETR-000002', 'Terminé'),
('ETR-000003', 'Annulé');

-- Catégorie data
INSERT INTO categorie (id_categorie, nom)
VALUES 
('CAT-000001', 'Carte Mère'),
('CAT-000002', 'Écran LCD'),
('CAT-000003', 'Batterie'),
('CAT-000004', 'Clavier'),
('CAT-000005', 'Disque Dur'),
('CAT-000006', 'Carte graphique');

-- Fournisseur data
INSERT INTO fornisseur (id_fornisseur, nom, email, addresse)
VALUES 
('FOU-000001', 'Fournisseur A', 'contact@fournisseura.com', '12 Rue des Fleurs, Paris'),
('FOU-000002', 'Fournisseur B', 'contact@fournisseurb.com', '45 Boulevard Haussmann, Lyon'),
('FOU-000003', 'Fournisseur C', 'contact@fournisseurc.com', '78 Avenue de Marseille, Lille');

-- Pièce data
INSERT INTO piece (id_piece, numero_serie, prix_unitaire, reference, description, id_categorie, id_typa, id_marque)
VALUES 
('PIE-000001', 'SN12345', 50.00, 'REF-A1', 'Carte Mère pour appareil', 'CAT-000001', 'TYPA-000002', 'MAR-000001'),
('PIE-000002', 'SN12346', 75.00, 'REF-B2', 'Écran LCD haute résolution', 'CAT-000002', 'TYPA-000002', 'MAR-000002'),
('PIE-000003', 'SN12347', 30.00, 'REF-C3', 'Batterie longue durée', 'CAT-000003', 'TYPA-000002', 'MAR-000003'),
('PIE-000004', 'SN12348', 25.00, 'REF-D4', 'Clavier ergonomique', 'CAT-000004', 'TYPA-000001', 'MAR-000004'),
('PIE-000005', 'SN12349', 100.00, 'REF-E5', 'Disque Dur 1TB', 'CAT-000005', 'TYPA-000001', 'MAR-000005');

('PIE-000006', 'SN12349', 100.00, 'REF-E5', 'RTX 5090', 'CAT-000006', 'TYPA-000001', 'MAR-000005');

-- Entrée pièce data
INSERT INTO entree_piece (id_entree_piece, date_entree, quantite, prix_unitaire, id_piece, id_fornisseur)
VALUES 
('ENT-000001', '2024-12-01', 10, 45.00, 'PIE-000001', 'FOU-000001'),
('ENT-000002', '2024-12-02', 15, 70.00, 'PIE-000002', 'FOU-000002'),
('ENT-000003', '2024-12-03', 20, 25.00, 'PIE-000003', 'FOU-000003'),
('ENT-000004', '2024-12-04', 30, 22.00, 'PIE-000004', 'FOU-000001'),
('ENT-000005', '2024-12-05', 8, 90.00, 'PIE-000005', 'FOU-000002');

-- Réparation data
INSERT INTO reparation (id_reparation, diagnostic, date_debut, prix, id_etat, id_categorie,id_appareil)
VALUES 
('REP-000001', TRUE, '2025-01-01 10:00:00', 120.50, 'ETR-000001', 'CAT-000001','APP-000001'),
('REP-000002', TRUE, '2025-01-02 11:00:00', 150.00, 'ETR-000002', 'CAT-000003','APP-000002'),
('REP-000003', TRUE, '2025-01-03 09:30:00', 200.00, 'ETR-000003', 'CAT-000005','APP-000003'),
('REP-000004', TRUE, '2025-01-04 14:00:00', 100.00, 'ETR-000001', 'CAT-000004','APP-000004'),
('REP-000005', TRUE, '2025-01-05 15:30:00', 300.00, 'ETR-000002', 'CAT-000002','APP-000005');


-- Réparation employé data
INSERT INTO reparation_employe (id_reparation_employe, id_reparation, id_employe)
VALUES 
('RE-000001', 'REP-000001', 'EMP-000001'),
('RE-000002', 'REP-000001', 'EMP-000002'),
('RE-000003', 'REP-000002', 'EMP-000003'),
('RE-000004', 'REP-000003', 'EMP-000004'),
('RE-000005', 'REP-000004', 'EMP-000001'),
('RE-000006', 'REP-000005', 'EMP-000002');

-- Réparation réel data
-- Mise à jour des données dans la table reparation_reel avec les quantités et les dates de sortie
INSERT INTO reparation_reel (id_reparation_reel, id_reparation, id_piece, quantite, date_sortie)
VALUES 
('RR-000001', 'REP-000001', 'PIE-000001', 2, '2025-01-01'),
('RR-000002', 'REP-000001', 'PIE-000003', 1, '2025-01-01'),
('RR-000003', 'REP-000002', 'PIE-000002', 1, '2025-01-02'),
('RR-000004', 'REP-000003', 'PIE-000005', 1, '2025-01-03'),
('RR-000005', 'REP-000004', 'PIE-000004', 1, '2025-01-04'),
('RR-000006', 'REP-000005', 'PIE-000001', 1, '2025-01-05'),
('RR-000007', 'REP-000005', 'PIE-000002', 1, '2025-01-05');


-- Retour data
INSERT INTO retour ( prix_total_main_doeuvre, prix_total_piece, date_retour, id_appareil)
VALUES 
( 120.00, 80.00, '2025-01-04', 'APP-000001');

-- Facture client data
INSERT INTO facture_client (id_facture_client, TVA, prix_total_pieces, prix_supplementaire, prix_total_employe, date_facturation, id_retour)
VALUES 
('FAC-000001', 20.00, 80.00, 20.00, 120.00, '2025-01-04 10:00:00', 'RET-000001');

-- Ligne facture client data
INSERT INTO ligne_facture_client (id_ligne_facture_client, quantite, id_facture_client, id_piece)
VALUES
('LFC-000001', 1, 'FAC-000001', 'PIE-000001'),
('LFC-000002', 1, 'FAC-000001', 'PIE-000003');

-- Paiement facture client data
INSERT INTO paiement_facture_client (date_paiment, montant_payer, id_facture_client)
VALUES 
('2025-01-04 10:30:00', 240.00, 'FAC-000001');

INSERT INTO commission (valeur)
VALUES 
(0.05);

