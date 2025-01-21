package models;

import java.util.List;
import java.sql.Connection;
import java.sql.Date;
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
	boolean diagnostic;
	LocalDateTime date_debut;
	double prix;
	Etat_reparation etat_reparation ;
	Categorie categorie;
	Appareil appareil;

	public static List<Reparation> search(Connection connection, String diagnostic, String dateDebutMin, String dateDebutMax,
                                      String idEtat, String idCategorie, String idAppareil) throws Exception {
		List<Reparation> liste = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			StringBuilder query = new StringBuilder("SELECT * FROM reparation WHERE 1=1");
			if (diagnostic != null && !diagnostic.isEmpty()) {
				query.append(" AND diagnostic = ?");
			}
			if (dateDebutMin != null && !dateDebutMin.isEmpty()) {
				query.append(" AND date_debut >= ?");
			}
			if (dateDebutMax != null && !dateDebutMax.isEmpty()) {
				query.append(" AND date_debut <= ?");
			}
			if (idEtat != null && !idEtat.isEmpty()) {
				query.append(" AND id_etat = ?");
			}
			if (idCategorie != null && !idCategorie.isEmpty()) {
				query.append(" AND id_categorie = ?");
			}
			if (idAppareil != null && !idAppareil.isEmpty()) {
				query.append(" AND id_appareil = ?");
			}

			statement = connection.prepareStatement(query.toString());
			int index = 1;

			if (diagnostic != null && !diagnostic.isEmpty()) {
				statement.setBoolean(index++, Boolean.parseBoolean(diagnostic));
			}
			if (dateDebutMin != null && !dateDebutMin.isEmpty()) {
				statement.setDate(index++, Date.valueOf(dateDebutMin));
			}
			if (dateDebutMax != null && !dateDebutMax.isEmpty()) {
				statement.setDate(index++, Date.valueOf(dateDebutMax));
			}
			if (idEtat != null && !idEtat.isEmpty()) {
				statement.setString(index++, idEtat);
			}
			if (idCategorie != null && !idCategorie.isEmpty()) {
				statement.setString(index++, idCategorie);
			}
			if (idAppareil != null && !idAppareil.isEmpty()) {
				statement.setString(index++, idAppareil);
			}

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Reparation instance = new Reparation();
				instance.setId_reparation(resultSet.getString("id_reparation"));
				instance.setDiagnostic(resultSet.getBoolean("diagnostic"));
				instance.setDate_debut(resultSet.getString("date_debut"));
				instance.setPrix(resultSet.getDouble("prix"));
				Etat_reparation etat = Etat_reparation.getById(resultSet.getString("id_etat"), connection);
				instance.setEtat_reparation(etat);
				Categorie categorie = Categorie.getById(resultSet.getString("id_categorie"), connection);
				instance.setCategorie(categorie);
				Appareil appareil = Appareil.getById(resultSet.getString("id_appareil"), connection);
				instance.setAppareil(appareil);
				liste.add(instance);
			}
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
		}
		return liste;
	}


	public Reparation(){
	}

	public Reparation(String id_reparation, String date_debut, double prix, Etat_reparation etat_reparation,Categorie categorie,Appareil appareil) {
		this.setId_reparation(id_reparation);
		this.setDate_debut(date_debut);
		this.setPrix(prix);
		this.setEtat_reparation(etat_reparation);
		this.setCategorie(categorie);
		this.setAppareil(appareil);
	}

	public Appareil getAppareil() {
		return appareil;
	}

	public void setAppareil(Appareil appareil) {
		this.appareil = appareil;
	}

	public String getId_reparation() {
		return this.id_reparation;
	}

	public boolean getDiagnostic() {
		return this.diagnostic;
	}

	public LocalDateTime getDate_debut() {
		return this.date_debut;
	}

	public double getPrix() {
		return this.prix;
	}

	public Etat_reparation getEtat_reparation() {
		return this.etat_reparation;
	}

	public Categorie getCategorie() {
		return this.categorie;
	}

	public void setId_reparation(String newId_reparation) {
		this.id_reparation = newId_reparation;
	}

	public void setDiagnostic(boolean newDiBgetBooleanic) {
		this.diagnostic = newDiBgetBooleanic;
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

	public void setEtat_reparation(Etat_reparation etat_reparation) {
		this.etat_reparation = etat_reparation;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO reparation (date_debut,prix,id_etat,id_categorie,id_appareil) VALUES (?,?,?,?,?) RETURNING id_reparation";
            statement = connection.prepareStatement(query);
            statement.setTimestamp(1, Timestamp.valueOf(getDate_debut()));
            statement.setDouble(2, getPrix());
            statement.setString(3, this.etat_reparation.getId_etat());
            statement.setString(4, this.categorie.getId_categorie());
            statement.setString(5, this.appareil.getId_appareil());
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
            String query = "UPDATE reparation SET date_debut = ?, prix = ?, id_etat = ?, id_categorie = ?, id_appareil = ? WHERE id_reparation = ?";
            statement = connection.prepareStatement(query);
            statement.setTimestamp(1, Timestamp.valueOf(getDate_debut()));
            statement.setDouble(2, getPrix());
            statement.setString(3, this.etat_reparation.getId_etat());
            statement.setString(4, this.categorie.getId_categorie());
            statement.setString(5, this.appareil.getId_appareil());
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
				instance.setDiagnostic(resultSet.getBoolean("diagnostic"));
				instance.setDate_debut(resultSet.getString("date_debut"));
				instance.setPrix(resultSet.getDouble("prix"));
				Etat_reparation etat_reparation = Etat_reparation.getById(resultSet.getString("id_etat"),connection);
				instance.setEtat_reparation(etat_reparation);
				Categorie categorie = Categorie.getById(resultSet.getString("id_categorie"),connection);
				instance.setCategorie(categorie);
				Appareil appareil = Appareil.getById(resultSet.getString("id_appareil"),connection);
				instance.setAppareil(appareil);
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

	public static List<Reparation> getAllByAppareil(Connection connection, String id_appareil) throws Exception {
		List<Reparation> liste = new ArrayList<Reparation>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reparation WHERE id_appareil = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id_appareil);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Reparation instance = new Reparation();
				instance.setId_reparation(resultSet.getString("id_reparation"));
				instance.setDiagnostic(resultSet.getBoolean("diagnostic"));
				instance.setDate_debut(resultSet.getString("date_debut")); 
				instance.setPrix(resultSet.getDouble("prix"));
				Etat_reparation etat_reparation = Etat_reparation.getById(resultSet.getString("id_etat"),connection);
				instance.setEtat_reparation(etat_reparation);
				Categorie categorie = Categorie.getById(resultSet.getString("id_categorie"),connection);
				instance.setCategorie(categorie);
				Appareil appareil = Appareil.getById(resultSet.getString("id_appareil"),connection);
				instance.setAppareil(appareil);
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
				instance.setDiagnostic(resultSet.getBoolean("diagnostic"));
				instance.setDate_debut(resultSet.getString("date_debut"));
				instance.setPrix(resultSet.getDouble("prix"));
				Etat_reparation etat_reparation = Etat_reparation.getById(resultSet.getString("id_etat"),connection);
				instance.setEtat_reparation(etat_reparation);
				Categorie categorie = Categorie.getById(resultSet.getString("id_categorie"),connection);
				instance.setCategorie(categorie);
				Appareil appareil = Appareil.getById(resultSet.getString("id_appareil"),connection);
				instance.setAppareil(appareil);
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
