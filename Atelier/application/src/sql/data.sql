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

-- Appareil data
INSERT INTO appareil (id_appareil, numero_serie, est_repare, desc_probleme, date_deposition, date_recuperation, estRecuperer, id_client, id_model)
VALUES 
('APP-000001', 'DEL12345678', FALSE, 'ecran bleu aleatoire', '2025-01-01 10:00:00', NULL, FALSE, 'CLT-000001', 'MOD-000001'),
('APP-000002', 'HP98765432', FALSE, 'Batterie qui ne charge pas', '2025-01-02 11:00:00', NULL, FALSE, 'CLT-000002', 'MOD-000002'),
('APP-000003', 'LEN11223344', FALSE, 'Surchauffe au demarrage', '2025-01-03 09:30:00', NULL, FALSE, 'CLT-000003', 'MOD-000003'),
('APP-000004', 'ASU55667788', FALSE, 'Clavier ne repond pas', '2025-01-04 14:00:00', NULL, FALSE, 'CLT-000004', 'MOD-000004'),
('APP-000005', 'ACE99887766', FALSE, 'Probleme avec la carte graphique', '2025-01-05 15:30:00', NULL, FALSE, 'CLT-000005', 'MOD-000005');

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

-- Etat reparation data
INSERT INTO etat_reparation (id_etat, libelle)
VALUES 
('ETR-000001', 'En cours'),
('ETR-000002', 'Termine'),
('ETR-000003', 'Annule');

-- Categorie data
INSERT INTO categorie (id_categorie, nom)
VALUES 
('CAT-000001', 'Carte Mere'),
('CAT-000002', 'ecran LCD'),
('CAT-000003', 'Batterie'),
('CAT-000004', 'Clavier'),
('CAT-000005', 'Disque Dur');

-- Fornisseur data
INSERT INTO fornisseur (id_fornisseur, nom, email, addresse)
VALUES 
('FOU-000001', 'Fournisseur A', 'contact@fournisseura.com', '12 Rue des Fleurs, Paris'),
('FOU-000002', 'Fournisseur B', 'contact@fournisseurb.com', '45 Boulevard Haussmann, Lyon'),
('FOU-000003', 'Fournisseur C', 'contact@fournisseurc.com', '78 Avenue de Marseille, Lille');

-- Piece data
INSERT INTO piece (id_piece, numero_serie, prix_unitaire, reference, description, id_categorie, id_marque)
VALUES 
('PIE-000001', 'SN12345', 50.00, 'REF-A1', 'Carte Mere pour appareil', 'CAT-000001', 'MAR-000001'),
('PIE-000002', 'SN12346', 75.00, 'REF-B2', 'ecran LCD haute resolution', 'CAT-000002', 'MAR-000002'),
('PIE-000003', 'SN12347', 30.00, 'REF-C3', 'Batterie longue duree', 'CAT-000003', 'MAR-000003'),
('PIE-000004', 'SN12348', 25.00, 'REF-D4', 'Clavier ergonomique', 'CAT-000004', 'MAR-000004'),
('PIE-000005', 'SN12349', 100.00, 'REF-E5', 'Disque Dur 1TB', 'CAT-000005', 'MAR-000005');

-- Entree piece data
INSERT INTO entree_piece (id_entree_piece, date_entree, quantite, prix_unitaire, id_fornisseur, id_piece)
VALUES 
('ENT-000001', '2024-12-01', 10, 45.00, 'FOU-000001', 'PIE-000001'),
('ENT-000002', '2024-12-02', 15, 70.00, 'FOU-000002', 'PIE-000002'),
('ENT-000003', '2024-12-03', 20, 25.00, 'FOU-000003', 'PIE-000003'),
('ENT-000004', '2024-12-04', 30, 22.00, 'FOU-000001', 'PIE-000004'),
('ENT-000005', '2024-12-05', 8, 90.00, 'FOU-000002', 'PIE-000005');

-- Appareil employe data
INSERT INTO appareil_employe (id_appareil, id_employe)
VALUES 
( 'APP-000001', 'EMP-000001'),
( 'APP-000001', 'EMP-000002'),
( 'APP-000002', 'EMP-000003'),
( 'APP-000003', 'EMP-000004'),
( 'APP-000004', 'EMP-000001'),
( 'APP-000005', 'EMP-000002');


-- INSERT INTO categorie (id_categorie, nom)
-- VALUES 
-- ('CAT-000001', 'Carte Mere'),
-- ('CAT-000002', 'ecran LCD'),
-- ('CAT-000003', 'Batterie'),
-- ('CAT-000004', 'Clavier'),
-- ('CAT-000005', 'Disque Dur');

-- Reparation data
INSERT INTO reparation (id_reparation, diagnostic, date_debut, prix, id_appareil_employe, id_etat,id_categorie)
VALUES 
('REP-000001', TRUE, '2025-01-01 10:00:00', 120.50, 'AE-000001', 'ETR-000001','CAT-000001'),
('REP-000002', TRUE, '2025-01-02 11:00:00', 150.00, 'AE-000003', 'ETR-000002','CAT-000003'),
('REP-000003', TRUE, '2025-01-03 09:30:00', 200.00, 'AE-000004', 'ETR-000003','CAT-000005'),
('REP-000004', TRUE, '2025-01-04 14:00:00', 100.00, 'AE-000005', 'ETR-000001','CAT-000004'),
('REP-000005', TRUE, '2025-01-05 15:30:00', 300.00, 'AE-000006', 'ETR-000002','CAT-000002');
-- alea
INSERT INTO reparation (id_reparation, diagnostic, date_debut, prix, id_appareil_employe, id_etat,id_categorie)
VALUES 
('REP-000006', TRUE, '2025-01-05 15:30:00', 300.00, 'AE-000005', 'ETR-000002','CAT-000002'),
('REP-000007', TRUE, '2025-01-05 15:30:00', 300.00, 'AE-000006', 'ETR-000002','CAT-000002');

-- Reparation reel data
INSERT INTO reparation_reel (id_reparation, id_piece, quantite)
VALUES 
('REP-000001', 'PIE-000001', 1),
('REP-000001', 'PIE-000003', 1),
('REP-000002', 'PIE-000002', 1),
('REP-000003', 'PIE-000005', 1),
('REP-000004', 'PIE-000004', 1),
('REP-000005', 'PIE-000001', 1),
('REP-000005', 'PIE-000002', 1);

-- Facture client data
INSERT INTO facture_client (id_facture_client, TVA, prix_total_pieces, prix_supplementaire, prix_total_employe, date_facturation, id_appareil)
VALUES 
('FAC-000001', 20.00, 80.00, 20.00, 120.00, '2025-01-04 10:00:00', 'APP-000001');

-- Ligne facture client data
INSERT INTO ligne_facture_client (id_ligne_facture_client, quantite, prix_unitaire, id_piece, id_facture_client)
VALUES
('LFC-000001', 1, '50.00', 'PIE-000001', 'FAC-000001'),
('LFC-000002', 1, '30.00', 'PIE-000003', 'FAC-000001');