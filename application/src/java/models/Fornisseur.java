package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Fornisseur{
	String id_fornisseur;
	String nom;
	String email;
	String addresse;

	public Fornisseur(){
	}

	public Fornisseur(String id_fornisseur, String nom, String email, String addresse) {
		this.id_fornisseur = id_fornisseur;
		this.nom = nom;
		this.email = email;
		this.addresse = addresse;
	}

	public String getId_fornisseur() {
		return this.id_fornisseur;
	}

	public String getNom() {
		return this.nom;
	}

	public String getEmail() {
		return this.email;
	}

	public String getAddresse() {
		return this.addresse;
	}

	public void setId_fornisseur(String newId_fornisseur) {
		this.id_fornisseur = newId_fornisseur;
	}

	public void setNom(String newNom) {
		this.nom = newNom;
	}

	public void setEmail(String newEmail) {
		this.email = newEmail;
	}

	public void setAddresse(String newAddresse) {
		this.addresse = newAddresse;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO fornisseur (nom,email,addresse) VALUES (?,?,?) RETURNING id_fornisseur";
            statement = connection.prepareStatement(query);
            statement.setString(1, getNom());
            statement.setString(2, getEmail());
            statement.setString(3, getAddresse());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_fornisseur = resultSet.getString("id_fornisseur");
            }
            System.out.println("Données Fornisseur insérées avec succès");
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
            String query = "UPDATE fornisseur SET nom = ?, email = ?, addresse = ? WHERE id_fornisseur = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, getNom());
            statement.setString(2, getEmail());
            statement.setString(3, getAddresse());
            statement.setString(4, getId_fornisseur());
            statement.executeUpdate();
            System.out.println("Données Fornisseur mises à jour avec succès");
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

	public static void delete(String id_fornisseur,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM fornisseur WHERE id_fornisseur = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_fornisseur);
            statement.executeUpdate();
            System.out.println("Données Fornisseur supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Fornisseur avec ID id_fornisseur: " + e.getMessage());
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

	public static List<Fornisseur> getAll(Connection connection) throws Exception {
		List<Fornisseur> liste = new ArrayList<Fornisseur>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM fornisseur";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Fornisseur instance = new Fornisseur();
				instance.setId_fornisseur(resultSet.getString("id_fornisseur"));
				instance.setNom(resultSet.getString("nom"));
				instance.setEmail(resultSet.getString("email"));
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
	public static Fornisseur getById(String id,Connection connection) throws Exception {
		Fornisseur instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM fornisseur WHERE id_fornisseur = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Fornisseur();
				instance.setId_fornisseur(resultSet.getString("id_fornisseur"));
				instance.setNom(resultSet.getString("nom"));
				instance.setEmail(resultSet.getString("email"));
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
