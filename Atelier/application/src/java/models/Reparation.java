package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Reparation{
	String id_reparation;
	Object diagnostic;
	LocalDateTime date_debut;
	double prix;
	int garentie;
	Appareil_employe appareil_employe ;
	Etat_reparation etat_reparation ;

	public Reparation(){
	}

	public Reparation(String id_reparation, String date_debut, double prix, Appareil_employe appareil_employe, Etat_reparation etat_reparation) {
		this.setId_reparation(id_reparation);
		this.setDate_debut(date_debut);
		this.setPrix(prix);
		this.setAppareil_employe(appareil_employe);
		this.setEtat_reparation(etat_reparation);
	}

	public String getId_reparation() {
		return this.id_reparation;
	}

	public Object getDiagnostic() {
		return this.diagnostic;
	}

	public LocalDateTime getDate_debut() {
		return this.date_debut;
	}

	public double getPrix() {
		return this.prix;
	}

	public int getGarentie() {
		return this.garentie;
	}

	public Appareil_employe getAppareil_employe() {
		return this.appareil_employe;
	}

	public Etat_reparation getEtat_reparation() {
		return this.etat_reparation;
	}

	public void setId_reparation(String newId_reparation) {
		this.id_reparation = newId_reparation;
	}

	public void setDiagnostic(Object newDiagnostic) {
		this.diagnostic = newDiagnostic;
	}

	public void setDate_debut(String newDate_debut) {
		if (newDate_debut != null && !newDate_debut.isEmpty()) {
			try {
				DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				this.date_debut = LocalDateTime.parse(newDate_debut, formatter1);
			} catch (DateTimeParseException e) {
				try {
					DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
					this.date_debut = LocalDateTime.parse(newDate_debut, formatter2);
				} catch (DateTimeParseException e2) {
					throw new IllegalArgumentException(
							"Format de date invalide. Formats acceptés : 'yyyy-MM-dd HH:mm:ss' ou 'yyyy-MM-dd'T'HH:mm'");
				}
			}
		}
	}

	public void setPrix(double newPrix) {
		this.prix = newPrix;
	}

	public void setGarentie(int newGarentie) {
		this.garentie = newGarentie;
	}

	public void setAppareil_employe(Appareil_employe appareil_employe) {
		this.appareil_employe = appareil_employe;
	}

	public void setEtat_reparation(Etat_reparation etat_reparation) {
		this.etat_reparation = etat_reparation;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO reparation (diagnostic,date_debut,prix,id_appareil_employe,id_etat) VALUES (?,?,?,?,?) RETURNING id_reparation";
            statement = connection.prepareStatement(query);
            statement.setObject(1, getDiagnostic());
			statement.setTimestamp(2, Timestamp.valueOf(getDate_debut()));
            statement.setDouble(3, getPrix());
            statement.setString(4, this.appareil_employe.getId_appareil_employe());
            statement.setString(5, this.etat_reparation.getId_etat());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_reparation = resultSet.getString("id_reparation");
            }
            System.out.println("Données Reparation insérées avec succès");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public void update(Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "UPDATE reparation SET diagnostic = ?, date_debut = ?, prix = ?, id_appareil_employe = ?, id_etat = ? WHERE id_reparation = ?";
            statement = connection.prepareStatement(query);
            statement.setObject(1, getDiagnostic());
			statement.setTimestamp(2, Timestamp.valueOf(getDate_debut()));
            statement.setDouble(3, getPrix());
            statement.setString(4, this.appareil_employe.getId_appareil_employe());
            statement.setString(5, this.etat_reparation.getId_etat());
            statement.setString(6, getId_reparation());
            statement.executeUpdate();
            System.out.println("Données Reparation mises à jour avec succès");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public static void delete(String id_reparation,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM reparation WHERE id_reparation = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_reparation);
            statement.executeUpdate();
            System.out.println("Données Reparation supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Reparation avec ID id_reparation: " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	public static List<Reparation> fitreBycategorie(Connection connection,String id_categorie) throws Exception {
		List<Reparation> liste = new ArrayList<Reparation>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reparation where id_categorie = ? ";
			statement = connection.prepareStatement(query);
			statement.setString(1, id_categorie);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Reparation instance = new Reparation();
				instance.setId_reparation(resultSet.getString("id_reparation"));
				instance.setDiagnostic(resultSet.getString("diagnostic"));
				instance.setDate_debut(resultSet.getString("date_debut"));
				instance.setPrix(resultSet.getDouble("prix"));
				Appareil_employe appareil_employe = Appareil_employe.getById(resultSet.getString("id_appareil_employe"),connection);
				instance.setAppareil_employe(appareil_employe);
				Etat_reparation etat_reparation = Etat_reparation.getById(resultSet.getString("id_etat"),connection);
				instance.setEtat_reparation(etat_reparation);
				liste.add(instance);
			}
			return liste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {}
			if (statement != null) try { statement.close(); } catch (SQLException e) {}
		}
	}

	public static List<Reparation> getAll(Connection connection) throws Exception {
		List<Reparation> liste = new ArrayList<Reparation>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reparation";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Reparation instance = new Reparation();
				instance.setId_reparation(resultSet.getString("id_reparation"));
				instance.setDiagnostic(resultSet.getString("diagnostic"));
				instance.setDate_debut(resultSet.getString("date_debut"));
				instance.setPrix(resultSet.getDouble("prix"));
				Appareil_employe appareil_employe = Appareil_employe.getById(resultSet.getString("id_appareil_employe"),connection);
				instance.setAppareil_employe(appareil_employe);
				Etat_reparation etat_reparation = Etat_reparation.getById(resultSet.getString("id_etat"),connection);
				instance.setEtat_reparation(etat_reparation);
				liste.add(instance);
			}
			return liste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {}
			if (statement != null) try { statement.close(); } catch (SQLException e) {}
		}
	}
	public static Reparation getById(String id,Connection connection) throws Exception {
		Reparation instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reparation WHERE id_reparation = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Reparation();
				instance.setId_reparation(resultSet.getString("id_reparation"));
				instance.setDiagnostic(resultSet.getString("diagnostic"));
				instance.setDate_debut(resultSet.getString("date_debut"));
				instance.setPrix(resultSet.getDouble("prix"));
				Appareil_employe appareil_employe = Appareil_employe.getById(resultSet.getString("id_appareil_employe"),connection);
				instance.setAppareil_employe(appareil_employe);
				Etat_reparation etat_reparation = Etat_reparation.getById(resultSet.getString("id_etat"),connection);
				instance.setEtat_reparation(etat_reparation);
			}
			return instance;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {}
			if (statement != null) try { statement.close(); } catch (SQLException e) {}
		}
	}
}
