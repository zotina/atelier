package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Reparation_employe{
	String id_reparation_employe;
	Reparation reparation ;
	Employe employe ;

	public Reparation_employe(){
	}

	public Reparation_employe(String id_reparation_employe, Reparation reparation, Employe employe) {
		this.id_reparation_employe = id_reparation_employe;
		this.reparation = reparation;
		this.employe = employe;
	}

	public String getId_reparation_employe() {
		return this.id_reparation_employe;
	}

	public Reparation getReparation() {
		return this.reparation;
	}

	public Employe getEmploye() {
		return this.employe;
	}

	public void setId_reparation_employe(String newId_reparation_employe) {
		this.id_reparation_employe = newId_reparation_employe;
	}

	public void setReparation(Reparation reparation) {
		this.reparation = reparation;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO reparation_employe (id_reparation,id_employe) VALUES (?,?) RETURNING id_reparation_employe";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.reparation.getId_reparation());
            statement.setString(2, this.employe.getId_employe());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_reparation_employe = resultSet.getString("id_reparation_employe");
            }
            System.out.println("Données Reparation_employe insérées avec succès");
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
            String query = "UPDATE reparation_employe SET id_reparation = ?, id_employe = ? WHERE id_reparation_employe = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.reparation.getId_reparation());
            statement.setString(2, this.employe.getId_employe());
            statement.setString(3, getId_reparation_employe());
            statement.executeUpdate();
            System.out.println("Données Reparation_employe mises à jour avec succès");
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

	public static void delete(String id_reparation_employe,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM reparation_employe WHERE id_reparation_employe = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_reparation_employe);
            statement.executeUpdate();
            System.out.println("Données Reparation_employe supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Reparation_employe avec ID id_reparation_employe: " + e.getMessage());
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

	public static List<Reparation_employe> getAll(Connection connection) throws Exception {
		List<Reparation_employe> liste = new ArrayList<Reparation_employe>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reparation_employe";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Reparation_employe instance = new Reparation_employe();
				instance.setId_reparation_employe(resultSet.getString("id_reparation_employe"));
				Reparation reparation = Reparation.getById(resultSet.getString("id_reparation"),connection);
				instance.setReparation(reparation);
				Employe employe = Employe.getById(resultSet.getString("id_employe"),connection);
				instance.setEmploye(employe);
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
	public static Reparation_employe getById(String id,Connection connection) throws Exception {
		Reparation_employe instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reparation_employe WHERE id_reparation_employe = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Reparation_employe();
				instance.setId_reparation_employe(resultSet.getString("id_reparation_employe"));
				Reparation reparation = Reparation.getById(resultSet.getString("id_reparation"),connection);
				instance.setReparation(reparation);
				Employe employe = Employe.getById(resultSet.getString("id_employe"),connection);
				instance.setEmploye(employe);
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
