package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Marque{
	String id_marque;
	String nom;

	public Marque(){
	}

	public Marque(String id_marque, String nom) {
		this.id_marque = id_marque;
		this.nom = nom;
	}

	public String getId_marque() {
		return this.id_marque;
	}

	public String getNom() {
		return this.nom;
	}

	public void setId_marque(String newId_marque) {
		this.id_marque = newId_marque;
	}

	public void setNom(String newNom) {
		this.nom = newNom;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO marque (nom) VALUES (?) RETURNING id_marque";
            statement = connection.prepareStatement(query);
            statement.setString(1, getNom());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_marque = resultSet.getString("id_marque");
            }
            System.out.println("Données Marque insérées avec succès");
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
            String query = "UPDATE marque SET nom = ? WHERE id_marque = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, getNom());
            statement.setString(2, getId_marque());
            statement.executeUpdate();
            System.out.println("Données Marque mises à jour avec succès");
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

	public static void delete(String id_marque,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM marque WHERE id_marque = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_marque);
            statement.executeUpdate();
            System.out.println("Données Marque supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Marque avec ID id_marque: " + e.getMessage());
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

	public static List<Marque> getAll(Connection connection) throws Exception {
		List<Marque> liste = new ArrayList<Marque>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM marque";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Marque instance = new Marque();
				instance.setId_marque(resultSet.getString("id_marque"));
				instance.setNom(resultSet.getString("nom"));
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
	public static Marque getById(String id,Connection connection) throws Exception {
		Marque instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM marque WHERE id_marque = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Marque();
				instance.setId_marque(resultSet.getString("id_marque"));
				instance.setNom(resultSet.getString("nom"));
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
