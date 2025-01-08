package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Role{
	String id_role;
	String libelle;
	double salaire_base;

	public Role(){
	}

	public Role(String id_role, String libelle, double salaire_base) {
		this.id_role = id_role;
		this.libelle = libelle;
		this.salaire_base = salaire_base;
	}

	public String getId_role() {
		return this.id_role;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public double getSalaire_base() {
		return this.salaire_base;
	}

	public void setId_role(String newId_role) {
		this.id_role = newId_role;
	}

	public void setLibelle(String newLibelle) {
		this.libelle = newLibelle;
	}

	public void setSalaire_base(double newSalaire_base) {
		this.salaire_base = newSalaire_base;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO role (libelle,salaire_base) VALUES (?,?) RETURNING id_role";
            statement = connection.prepareStatement(query);
            statement.setString(1, getLibelle());
            statement.setDouble(2, getSalaire_base());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_role = resultSet.getString("id_role");
            }
            System.out.println("Données Role insérées avec succès");
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
            String query = "UPDATE role SET libelle = ?, salaire_base = ? WHERE id_role = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, getLibelle());
            statement.setDouble(2, getSalaire_base());
            statement.setString(3, getId_role());
            statement.executeUpdate();
            System.out.println("Données Role mises à jour avec succès");
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

	public static void delete(String id_role,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM role WHERE id_role = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_role);
            statement.executeUpdate();
            System.out.println("Données Role supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Role avec ID id_role: " + e.getMessage());
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

	public static List<Role> getAll(Connection connection) throws Exception {
		List<Role> liste = new ArrayList<Role>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM role";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Role instance = new Role();
				instance.setId_role(resultSet.getString("id_role"));
				instance.setLibelle(resultSet.getString("libelle"));
				instance.setSalaire_base(resultSet.getDouble("salaire_base"));
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
	public static Role getById(String id,Connection connection) throws Exception {
		Role instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM role WHERE id_role = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Role();
				instance.setId_role(resultSet.getString("id_role"));
				instance.setLibelle(resultSet.getString("libelle"));
				instance.setSalaire_base(resultSet.getDouble("salaire_base"));
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
