-- Création des séquences
CREATE SEQUENCE IF NOT EXISTS seq_reparation_employe START 1;
CREATE SEQUENCE IF NOT EXISTS seq_reparation START 1;
CREATE SEQUENCE IF NOT EXISTS seq_appareil START 1;
CREATE SEQUENCE IF NOT EXISTS seq_employe START 1;
CREATE SEQUENCE IF NOT EXISTS seq_entree_piece START 1;
CREATE SEQUENCE IF NOT EXISTS seq_client START 1;
CREATE SEQUENCE IF NOT EXISTS seq_marque START 1;
CREATE SEQUENCE IF NOT EXISTS seq_model START 1;
CREATE SEQUENCE IF NOT EXISTS seq_role START 1;
CREATE SEQUENCE IF NOT EXISTS seq_categorie START 1;
CREATE SEQUENCE IF NOT EXISTS seq_fornisseur START 1;
CREATE SEQUENCE IF NOT EXISTS seq_etat_reparation START 1;
CREATE SEQUENCE IF NOT EXISTS seq_piece START 1;
CREATE SEQUENCE IF NOT EXISTS seq_facture_client START 1;
CREATE SEQUENCE IF NOT EXISTS seq_ligne_facture_client START 1;
CREATE SEQUENCE IF NOT EXISTS seq_typa START 1;
CREATE SEQUENCE IF NOT EXISTS seq_reparation_reel START 1;
CREATE SEQUENCE IF NOT EXISTS seq_retour START 1;
CREATE SEQUENCE IF NOT EXISTS seq_recommandation START 1;


CREATE OR REPLACE FUNCTION generate_id_seq_recommandation()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_recommandation := 'REC-' || LPAD(NEXTVAL('seq_recommandation')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_entree_piece()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_entree_piece := 'ENT-' || LPAD(NEXTVAL('seq_entree_piece')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_reparation_employe()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_reparation_employe := 'RE-' || LPAD(NEXTVAL('seq_reparation_employe')::TEXT, 6, '0');
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_retour()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_retour := 'RET-' || LPAD(NEXTVAL('seq_retour')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_typa()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_typa := 'TYPA-' || LPAD(NEXTVAL('seq_typa')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_appareil()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_appareil := 'APP-' || LPAD(NEXTVAL('seq_appareil')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_categorie()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_categorie := 'CAT-' || LPAD(NEXTVAL('seq_categorie')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_client()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_client := 'CLT-' || LPAD(NEXTVAL('seq_client')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_employe()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_employe := 'EMP-' || LPAD(NEXTVAL('seq_employe')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_fornisseur()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_fornisseur := 'FOU-' || LPAD(NEXTVAL('seq_fornisseur')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_marque()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_marque := 'MAR-' || LPAD(NEXTVAL('seq_marque')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_model()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_model := 'MOD-' || LPAD(NEXTVAL('seq_model')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_piece()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_piece := 'PIE-' || LPAD(NEXTVAL('seq_piece')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_facture()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_facture_client := 'FAC-' || LPAD(NEXTVAL('seq_facture_client')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_ligne()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_ligne_facture_client := 'LFC-' || LPAD(NEXTVAL('seq_ligne_facture_client')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_etat()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_etat := 'ETR-' || LPAD(NEXTVAL('seq_etat_reparation')::TEXT, 6, '0'); 
   RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_id_reparation()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_reparation := 'REP-' || LPAD(NEXTVAL('seq_reparation')::TEXT, 6, '0'); 
   RETURN NEW;  
END;  
$$
 LANGUAGE plpgsql;  

CREATE OR REPLACE FUNCTION generate_id_reparation_reel()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_reparation_reel := 'RR-' || LPAD(NEXTVAL('seq_reparation_reel')::TEXT, 6, '0'); 
   RETURN NEW;  
END;  
$$
 LANGUAGE plpgsql;  

CREATE OR REPLACE FUNCTION generate_id_role()
RETURNS TRIGGER AS $$
BEGIN
   NEW.id_role := 'ROL-' || LPAD(NEXTVAL('seq_role')::TEXT, 6, '0'); 
   RETURN NEW;  
END;  
$$
 LANGUAGE plpgsql;  


CREATE TRIGGER trig_recommandation_id
BEFORE INSERT ON recommandation  
FOR EACH ROW  
EXECUTE FUNCTION generate_id_seq_recommandation();  

CREATE TRIGGER trig_entree_piece_id  
BEFORE INSERT ON entree_piece  
FOR EACH ROW  
EXECUTE FUNCTION generate_id_entree_piece();  

CREATE TRIGGER trig_reparation_employe_id
BEFORE INSERT ON reparation_employe
FOR EACH ROW
EXECUTE FUNCTION generate_id_reparation_employe();

CREATE TRIGGER trig_typa_id
BEFORE INSERT ON typa
FOR EACH ROW
EXECUTE FUNCTION generate_id_typa();


CREATE TRIGGER trig_appareil_id
BEFORE INSERT ON appareil
FOR EACH ROW
EXECUTE FUNCTION generate_id_appareil();

CREATE TRIGGER trig_categorie_id
BEFORE INSERT ON categorie
FOR EACH ROW
EXECUTE FUNCTION generate_id_categorie();

CREATE TRIGGER trig_client_id
BEFORE INSERT ON client
FOR EACH ROW
EXECUTE FUNCTION generate_id_client();

CREATE TRIGGER trig_employe_id
BEFORE INSERT ON employe
FOR EACH ROW
EXECUTE FUNCTION generate_id_employe();

CREATE TRIGGER trig_fornisseur_id
BEFORE INSERT ON fornisseur
FOR EACH ROW
EXECUTE FUNCTION generate_id_fornisseur();

CREATE TRIGGER trig_marque_id
BEFORE INSERT ON marque
FOR EACH ROW
EXECUTE FUNCTION generate_id_marque();

CREATE TRIGGER trig_model_id
BEFORE INSERT ON model
FOR EACH ROW
EXECUTE FUNCTION generate_id_model();

CREATE TRIGGER trig_piece_id
BEFORE INSERT ON piece
FOR EACH ROW
EXECUTE FUNCTION generate_id_piece();

CREATE TRIGGER trig_facture_client_id
BEFORE INSERT ON facture_client
FOR EACH ROW
EXECUTE FUNCTION generate_id_facture();

CREATE TRIGGER trig_ligne_facture_client_id
BEFORE INSERT ON ligne_facture_client
FOR EACH ROW
EXECUTE FUNCTION generate_id_ligne();

CREATE TRIGGER trig_etat_reparation_id  
BEFORE INSERT ON etat_reparation  
FOR EACH ROW  
EXECUTE FUNCTION generate_id_etat();  

CREATE TRIGGER trig_retour_id  
BEFORE INSERT ON retour  
FOR EACH ROW  
EXECUTE FUNCTION generate_id_retour();  

CREATE TRIGGER trig_reparation_id  
BEFORE INSERT ON reparation  
FOR EACH ROW  
EXECUTE FUNCTION generate_id_reparation();  

CREATE TRIGGER trig_reparation_reel_id  
BEFORE INSERT ON reparation_reel  
FOR EACH ROW  
EXECUTE FUNCTION generate_id_reparation_reel();  

CREATE TRIGGER trig_role_id  
BEFORE INSERT ON role  
FOR EACH ROW  
EXECUTE FUNCTION generate_id_role();
