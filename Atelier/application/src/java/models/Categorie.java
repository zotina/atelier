package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Categorie{
	String id_categorie;
	String nom;

	public Categorie(){
	}

	public Categorie(String id_categorie, String nom) {
		this.id_categorie = id_categorie;
		this.nom = nom;
	}

	public String getId_categorie() {
		return this.id_categorie;
	}

	public String getNom() {
		return this.nom;
	}

	public void setId_categorie(String newId_categorie) {
		this.id_categorie = newId_categorie;
	}

	public void setNom(String newNom) {
		this.nom = newNom;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO categorie (nom) VALUES (?) RETURNING id_categorie";
            statement = connection.prepareStatement(query);
            statement.setString(1, getNom());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_categorie = resultSet.getString("id_categorie");
            }
            System.out.println("Données Categorie insérées avec succès");
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
            String query = "UPDATE categorie SET nom = ? WHERE id_categorie = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, getNom());
            statement.setString(2, getId_categorie());
            statement.executeUpdate();
            System.out.println("Données Categorie mises à jour avec succès");
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

	public static void delete(String id_categorie,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM categorie WHERE id_categorie = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_categorie);
            statement.executeUpdate();
            System.out.println("Données Categorie supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Categorie avec ID id_categorie: " + e.getMessage());
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

	public static List<Categorie> getAll(Connection connection) throws Exception {
		List<Categorie> liste = new ArrayList<Categorie>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM categorie";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Categorie instance = new Categorie();
				instance.setId_categorie(resultSet.getString("id_categorie"));
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
	public static Categorie getById(String id,Connection connection) throws Exception {
		Categorie instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM categorie WHERE id_categorie = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Categorie();
				instance.setId_categorie(resultSet.getString("id_categorie"));
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
