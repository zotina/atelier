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
   nom VARCHAR(50) ,
   PRIMARY KEY(id_typa)
);


CREATE TABLE appareil(
   id_appareil VARCHAR(50) ,
   numero_serie TEXT NOT NULL,
   est_repare BOOLEAN NOT NULL,
   desc_probleme TEXT NOT NULL,
   date_deposition TIMESTAMP NOT NULL,
   id_typa VARCHAR(50)  NOT NULL,
   id_client VARCHAR(255)  NOT NULL,
   id_model VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_appareil),
   FOREIGN KEY(id_typa) REFERENCES typa(id_typa),
   FOREIGN KEY(id_client) REFERENCES client(id_client),
   FOREIGN KEY(id_model) REFERENCES model(id_model)
);

CREATE TABLE reparation(
   id_reparation VARCHAR(50) ,
   diagnostic BOOLEAN,
   date_debut TIMESTAMP NOT NULL,
   prix NUMERIC(15,2)  ,
   id_etat VARCHAR(50)  NOT NULL,
   id_categorie VARCHAR(50)  NOT NULL,
   id_appareil VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_reparation),
   FOREIGN KEY(id_etat) REFERENCES etat_reparation(id_etat),
   FOREIGN KEY(id_appareil) REFERENCES appareil(id_appareil),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id_categorie)
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
   id_categorie VARCHAR(50)  NOT NULL,
   id_typa VARCHAR(50)  NOT NULL,
   id_marque VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_piece),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id_categorie),
   FOREIGN KEY(id_typa) REFERENCES typa(id_typa),
   FOREIGN KEY(id_marque) REFERENCES marque(id_marque)
);

CREATE TABLE entree_piece(
   id_entree_piece VARCHAR(50) ,
   date_entree DATE NOT NULL,
   quantite INTEGER NOT NULL,
   prix_unitaire NUMERIC(15,2)   NOT NULL,
   id_piece VARCHAR(50)  NOT NULL,
   id_fornisseur VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_entree_piece),
   FOREIGN KEY(id_piece) REFERENCES piece(id_piece),
   FOREIGN KEY(id_fornisseur) REFERENCES fornisseur(id_fornisseur)
);

CREATE TABLE retour(
   id_retour VARCHAR(50) ,
   prix_total_main_doeuvre NUMERIC(15,2)  ,
   prix_total_piece NUMERIC(15,2)  ,
   date_retour DATE,
   id_appareil VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_retour),
   UNIQUE(id_appareil),
   FOREIGN KEY(id_appareil) REFERENCES appareil(id_appareil)
);

CREATE TABLE recommandation(
   id_recommandation VARCHAR(50) ,
   date_recommandation DATE,
   id_piece VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_recommandation),
   FOREIGN KEY(id_piece) REFERENCES piece(id_piece)
);

CREATE TABLE facture_client(
   id_facture_client VARCHAR(50) ,
   TVA NUMERIC(15,2)  ,
   prix_total_pieces NUMERIC(15,2)  ,
   prix_supplementaire NUMERIC(15,2)  ,
   prix_total_employe NUMERIC(15,2)  ,
   date_facturation TIMESTAMP NOT NULL,
   id_retour VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_facture_client),
   FOREIGN KEY(id_retour) REFERENCES retour(id_retour)
);

CREATE TABLE ligne_facture_client(
   id_ligne_facture_client VARCHAR(50) ,
   quantite INTEGER,
   id_facture_client VARCHAR(50)  NOT NULL,
   id_piece VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_ligne_facture_client),
   FOREIGN KEY(id_facture_client) REFERENCES facture_client(id_facture_client),
   FOREIGN KEY(id_piece) REFERENCES piece(id_piece)
);

CREATE TABLE paiement_facture_client(
   id_paiement_facture_client SERIAL,
   date_paiment TIMESTAMP NOT NULL,
   montant_payer NUMERIC(15,2)   NOT NULL,
   id_facture_client VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_paiement_facture_client),
   FOREIGN KEY(id_facture_client) REFERENCES facture_client(id_facture_client)
);

CREATE TABLE reparation_employe(
   id_reparation_employe VARCHAR(50),
   id_reparation VARCHAR(50) ,
   id_employe VARCHAR(50) ,
   PRIMARY KEY(id_reparation_employe),
   FOREIGN KEY(id_reparation) REFERENCES reparation(id_reparation),
   FOREIGN KEY(id_employe) REFERENCES employe(id_employe)
);

   CREATE TABLE reparation_reel(
      id_reparation_reel VARCHAR(50), 
      id_reparation VARCHAR(50) ,
      id_piece VARCHAR(50) ,
      quantite INTEGER NOT NULL,
      date_sortie DATE,
      PRIMARY KEY(id_reparation_reel),
      FOREIGN KEY(id_reparation) REFERENCES reparation(id_reparation),
      FOREIGN KEY(id_piece) REFERENCES piece(id_piece)
   );

CREATE TABLE commission(
   id_commission SERIAL,
   valeur NUMERIC(15,2) NOT NULL,
   date_changement date
);


CREATE TABLE genre(
   id_genre VARCHAR(50) ,
   libelle VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_genre)
);

ALTER TABLE employe 
ADD COLUMN id_genre VARCHAR(50),
ADD CONSTRAINT fk_genre FOREIGN KEY (id_genre) REFERENCES genre(id_genre);

CREATE TABLE condition_commission (
   id_condition_commission SERIAL,
   min_prix NUMERIC(15,2),
   date_changement date
);

ALTER TABLE piece
ADD COLUMN date_changement_prix DATE DEFAULT CURRENT_DATE;


CREATE TABLE histo_piece (
    id_histo VARCHAR(50) PRIMARY KEY,
    id_piece VARCHAR(50) NOT NULL,
    prix_unitaire NUMERIC(15,2) NOT NULL,
    change_type VARCHAR(10) NOT NULL,
    changed_at DATE DEFAULT CURRENT_DATE NOT NULL,
    changed_by VARCHAR(100),
    FOREIGN KEY (id_piece) REFERENCES piece(id_piece) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION trg_piece_history() RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO histo_piece (id_piece, prix_unitaire, change_type, changed_by, changed_at)
        VALUES (NEW.id_piece, NEW.prix_unitaire, 'INSERT', current_user, COALESCE(NEW.date_changement_prix, CURRENT_DATE));
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO histo_piece (id_piece, prix_unitaire, change_type, changed_by, changed_at)
        VALUES (NEW.id_piece, NEW.prix_unitaire, 'UPDATE', current_user, COALESCE(NEW.date_changement_prix, CURRENT_DATE));
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO histo_piece (id_piece, prix_unitaire, change_type, changed_by, changed_at)
        VALUES (OLD.id_piece, OLD.prix_unitaire, 'DELETE', current_user, COALESCE(OLD.date_changement_prix, CURRENT_DATE));
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_piece_history
AFTER INSERT OR UPDATE OR DELETE ON piece
FOR EACH ROW
EXECUTE FUNCTION trg_piece_history();