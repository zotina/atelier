package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Etat_reparation{
	String id_etat;
	String libelle;

	public Etat_reparation(){
	}

	public Etat_reparation(String id_etat, String libelle) {
		this.id_etat = id_etat;
		this.libelle = libelle;
	}

	public String getId_etat() {
		return this.id_etat;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setId_etat(String newId_etat) {
		this.id_etat = newId_etat;
	}

	public void setLibelle(String newLibelle) {
		this.libelle = newLibelle;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO etat_reparation (libelle) VALUES (?) RETURNING id_etat";
            statement = connection.prepareStatement(query);
            statement.setString(1, getLibelle());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_etat = resultSet.getString("id_etat");
            }
            System.out.println("Données Etat_reparation insérées avec succès");
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
            String query = "UPDATE etat_reparation SET libelle = ? WHERE id_etat = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, getLibelle());
            statement.setString(2, getId_etat());
            statement.executeUpdate();
            System.out.println("Données Etat_reparation mises à jour avec succès");
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

	public static void delete(String id_etat,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM etat_reparation WHERE id_etat = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_etat);
            statement.executeUpdate();
            System.out.println("Données Etat_reparation supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Etat_reparation avec ID id_etat: " + e.getMessage());
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

	public static List<Etat_reparation> getAll(Connection connection) throws Exception {
		List<Etat_reparation> liste = new ArrayList<Etat_reparation>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM etat_reparation";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Etat_reparation instance = new Etat_reparation();
				instance.setId_etat(resultSet.getString("id_etat"));
				instance.setLibelle(resultSet.getString("libelle"));
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
	public static Etat_reparation getById(String id,Connection connection) throws Exception {
		Etat_reparation instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM etat_reparation WHERE id_etat = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Etat_reparation();
				instance.setId_etat(resultSet.getString("id_etat"));
				instance.setLibelle(resultSet.getString("libelle"));
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
