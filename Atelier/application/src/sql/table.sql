CREATE TABLE client(
   id_client VARCHAR(255) ,
   email VARCHAR(255)  NOT NULL,
   nom VARCHAR(50)  NOT NULL,
   telephone BIGINT,
   addresse TEXT,
   PRIMARY KEY(id_client)
);

CREATE TABLE marque(
   id_marque VARCHAR(50) ,
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_marque)
);

CREATE TABLE model(
   id_model VARCHAR(50) ,
   libelle VARCHAR(255)  NOT NULL,
   id_marque VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_model),
   FOREIGN KEY(id_marque) REFERENCES marque(id_marque)
);

CREATE TABLE role(
   id_role VARCHAR(50) ,
   libelle VARCHAR(255)  NOT NULL,
   salaire_base NUMERIC(15,2)   NOT NULL,
   PRIMARY KEY(id_role)
);

CREATE TABLE categorie(
   id_categorie VARCHAR(50) ,
   nom VARCHAR(255)  NOT NULL,
   PRIMARY KEY(id_categorie)
);

CREATE TABLE fornisseur(
   id_fornisseur VARCHAR(50) ,
   nom VARCHAR(255) ,
   email VARCHAR(255) ,
   addresse VARCHAR(255) ,
   PRIMARY KEY(id_fornisseur)
);

CREATE TABLE etat_reparation(
   id_etat VARCHAR(50) ,
   libelle VARCHAR(50) ,
   PRIMARY KEY(id_etat)
);

CREATE TABLE typa(
   id_typa VARCHAR(50) ,
   libelle VARCHAR(100)  NOT NULL,
   PRIMARY KEY(id_typa),
   UNIQUE(libelle)
);

CREATE TABLE appareil(
   id_appareil VARCHAR(50) ,
   numero_serie TEXT NOT NULL,
   est_repare BOOLEAN NOT NULL,
   desc_probleme TEXT NOT NULL,
   date_deposition TIMESTAMP NOT NULL,
   date_recuperation TIMESTAMP,
   estRecuperer BOOLEAN NOT NULL,
   id_client VARCHAR(255)  NOT NULL,
   id_model VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_appareil),
   FOREIGN KEY(id_typa) REFERENCES typa(id_typa),
   FOREIGN KEY(id_client) REFERENCES client(id_client),
   FOREIGN KEY(id_model) REFERENCES model(id_model)
);

CREATE TABLE employe(
   id_employe VARCHAR(50) ,
   nom VARCHAR(255)  NOT NULL,
   telephone VARCHAR(50) ,
   email VARCHAR(255) ,
   salaire_personnalise VARCHAR(255) ,
   date_embauche DATE NOT NULL,
   addresse VARCHAR(255) ,
   id_role VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_employe),
   FOREIGN KEY(id_role) REFERENCES role(id_role)
);

CREATE TABLE piece(
   id_piece VARCHAR(50) ,
   numero_serie VARCHAR(50) ,
   prix_unitaire NUMERIC(15,2)   NOT NULL,
   reference TEXT NOT NULL,
   description VARCHAR(255) ,
   id_typa VARCHAR(50)  NOT NULL,
   id_categorie VARCHAR(50)  NOT NULL,
   id_marque VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_piece),
   FOREIGN KEY(id_typa) REFERENCES typa(id_typa),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id_categorie),
   FOREIGN KEY(id_marque) REFERENCES marque(id_marque)
);

CREATE TABLE entree_piece(
   id_entree_piece VARCHAR(50) ,
   date_entree DATE NOT NULL,
   quantite INTEGER NOT NULL,
   prix_unitaire NUMERIC(15,2)   NOT NULL,
   id_fornisseur VARCHAR(50)  NOT NULL,
   id_piece VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_entree_piece),
   FOREIGN KEY(id_fornisseur) REFERENCES fornisseur(id_fornisseur),
   FOREIGN KEY(id_piece) REFERENCES piece(id_piece)
);

CREATE TABLE facture_client(
   id_facture_client VARCHAR(50) ,
   TVA NUMERIC(15,2)  ,
   prix_total_pieces NUMERIC(15,2)  ,
   prix_supplementaire NUMERIC(15,2)  ,
   prix_total_employe NUMERIC(15,2)  ,
   date_facturation TIMESTAMP NOT NULL,
   id_appareil VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_facture_client),
   UNIQUE(id_appareil),
   FOREIGN KEY(id_appareil) REFERENCES appareil(id_appareil)
);

CREATE TABLE ligne_facture_client(
   id_ligne_facture_client VARCHAR(50) ,
   quantite INTEGER,
   prix_unitaire VARCHAR(50) ,
   id_piece VARCHAR(50)  NOT NULL,
   id_facture_client VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_ligne_facture_client),
   FOREIGN KEY(id_piece) REFERENCES piece(id_piece),
   FOREIGN KEY(id_facture_client) REFERENCES facture_client(id_facture_client)
);

CREATE TABLE paiement_facture_client(
   id_paiement_facture_client SERIAL,
   date_paiment TIMESTAMP NOT NULL,
   montant_payer NUMERIC(15,2)   NOT NULL,
   id_facture_client VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_paiement_facture_client),
   FOREIGN KEY(id_facture_client) REFERENCES facture_client(id_facture_client)
);

CREATE TABLE appareil_employe(
   id_appareil_employe VARCHAR(50) ,
   id_appareil VARCHAR(50)  NOT NULL,
   id_employe VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_appareil_employe),
   FOREIGN KEY(id_appareil) REFERENCES appareil(id_appareil),
   FOREIGN KEY(id_employe) REFERENCES employe(id_employe)
);

CREATE TABLE reparation(
   id_reparation VARCHAR(50) ,
   diagnostic BOOLEAN,
   date_debut TIMESTAMP NOT NULL,
   prix NUMERIC(15,2)  ,
   id_appareil_employe VARCHAR(50)  NOT NULL,
   id_etat VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_reparation),
   FOREIGN KEY(id_appareil_employe) REFERENCES appareil_employe(id_appareil_employe),
   FOREIGN KEY(id_etat) REFERENCES etat_reparation(id_etat)
);

CREATE TABLE reparation_reel(
   id_reparation VARCHAR(50) ,
   id_piece VARCHAR(50) ,
   quantite INTEGER NOT NULL,
   PRIMARY KEY(id_reparation, id_piece),
   FOREIGN KEY(id_reparation) REFERENCES reparation(id_reparation),
   FOREIGN KEY(id_piece) REFERENCES piece(id_piece)
);
-- Ajouter la colonne id_typa
ALTER TABLE appareil
ADD COLUMN id_typa VARCHAR(50) ;

-- Ajouter la contrainte de clé étrangère
ALTER TABLE appareil
ADD CONSTRAINT fk_id_typa
FOREIGN KEY (id_typa) REFERENCES typa(id_typa);


ALTER TABLE piece
ADD COLUMN id_typa VARCHAR(50) ;

-- Ajouter la contrainte de clé étrangère
ALTER TABLE piece
ADD CONSTRAINT fk_id_typa
FOREIGN KEY (id_typa) REFERENCES typa(id_typa);


ALTER TABLE reparation
ADD COLUMN id_categorie VARCHAR(50) ;
ALTER TABLE reparation
ADD CONSTRAINT fk_id_categorie
FOREIGN KEY (id_categorie) REFERENCES categorie(id_categorie);