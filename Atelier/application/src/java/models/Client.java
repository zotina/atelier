package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Client{
	String id_client;
	String email;
	String nom;
	int telephone;
	String addresse;

	public Client(){
	}

	public Client(String id_client, String email, String nom, int telephone, String addresse) {
		this.id_client = id_client;
		this.email = email;
		this.nom = nom;
		this.telephone = telephone;
		this.addresse = addresse;
	}

	public String getId_client() {
		return this.id_client;
	}

	public String getEmail() {
		return this.email;
	}

	public String getNom() {
		return this.nom;
	}

	public int getTelephone() {
		return this.telephone;
	}

	public String getAddresse() {
		return this.addresse;
	}

	public void setId_client(String newId_client) {
		this.id_client = newId_client;
	}

	public void setEmail(String newEmail) {
		this.email = newEmail;
	}

	public void setNom(String newNom) {
		this.nom = newNom;
	}

	public void setTelephone(int newTelephone) {
		this.telephone = newTelephone;
	}

	public void setAddresse(String newAddresse) {
		this.addresse = newAddresse;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO client (email,nom,telephone,addresse) VALUES (?,?,?,?) RETURNING id_client";
            statement = connection.prepareStatement(query);
            statement.setString(1, getEmail());
            statement.setString(2, getNom());
            statement.setInt(3, getTelephone());
            statement.setString(4, getAddresse());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_client = resultSet.getString("id_client");
            }
            System.out.println("Données Client insérées avec succès");
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
            String query = "UPDATE client SET email = ?, nom = ?, telephone = ?, addresse = ? WHERE id_client = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, getEmail());
            statement.setString(2, getNom());
            statement.setInt(3, getTelephone());
            statement.setString(4, getAddresse());
            statement.setString(5, getId_client());
            statement.executeUpdate();
            System.out.println("Données Client mises à jour avec succès");
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

	public static void delete(String id_client,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM client WHERE id_client = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_client);
            statement.executeUpdate();
            System.out.println("Données Client supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Client avec ID id_client: " + e.getMessage());
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

	public static List<Client> getAll(Connection connection) throws Exception {
		List<Client> liste = new ArrayList<Client>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM client";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Client instance = new Client();
				instance.setId_client(resultSet.getString("id_client"));
				instance.setEmail(resultSet.getString("email"));
				instance.setNom(resultSet.getString("nom"));
				instance.setTelephone(resultSet.getInt("telephone"));
				instance.setAddresse(resultSet.getString("addresse"));
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
	public static Client getById(String id,Connection connection) throws Exception {
		Client instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM client WHERE id_client = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Client();
				instance.setId_client(resultSet.getString("id_client"));
				instance.setEmail(resultSet.getString("email"));
				instance.setNom(resultSet.getString("nom"));
				instance.setTelephone(resultSet.getInt("telephone"));
				instance.setAddresse(resultSet.getString("addresse"));
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
