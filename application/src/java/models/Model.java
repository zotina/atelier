package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Model{
	String id_model;
	String libelle;
	Marque marque ;

	public Model(){
	}

	public Model(String id_model, String libelle, Marque marque) {
		this.id_model = id_model;
		this.libelle = libelle;
		this.marque = marque;
	}

	public String getId_model() {
		return this.id_model;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public Marque getMarque() {
		return this.marque;
	}

	public void setId_model(String newId_model) {
		this.id_model = newId_model;
	}

	public void setLibelle(String newLibelle) {
		this.libelle = newLibelle;
	}

	public void setMarque(Marque marque) {
		this.marque = marque;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO model (libelle,id_marque) VALUES (?,?) RETURNING id_model";
            statement = connection.prepareStatement(query);
            statement.setString(1, getLibelle());
            statement.setString(2, this.marque.getId_marque());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_model = resultSet.getString("id_model");
            }
            System.out.println("Données Model insérées avec succès");
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
            String query = "UPDATE model SET libelle = ?, id_marque = ? WHERE id_model = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, getLibelle());
            statement.setString(2, this.marque.getId_marque());
            statement.setString(3, getId_model());
            statement.executeUpdate();
            System.out.println("Données Model mises à jour avec succès");
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

	public static void delete(String id_model,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM model WHERE id_model = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_model);
            statement.executeUpdate();
            System.out.println("Données Model supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Model avec ID id_model: " + e.getMessage());
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

	public static List<Model> getAll(Connection connection) throws Exception {
		List<Model> liste = new ArrayList<Model>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM model";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Model instance = new Model();
				instance.setId_model(resultSet.getString("id_model"));
				instance.setLibelle(resultSet.getString("libelle"));
				Marque marque = Marque.getById(resultSet.getString("id_marque"),connection);
				instance.setMarque(marque);
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
	public static Model getById(String id,Connection connection) throws Exception {
		Model instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM model WHERE id_model = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Model();
				instance.setId_model(resultSet.getString("id_model"));
				instance.setLibelle(resultSet.getString("libelle"));
				Marque marque = Marque.getById(resultSet.getString("id_marque"),connection);
				instance.setMarque(marque);
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
