package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Appareil_employe{
	String id_appareil_employe;
	Appareil appareil ;
	Employe employe ;

	public Appareil_employe(){
	}

	public Appareil_employe(String id_appareil_employe, Appareil appareil, Employe employe) {
		this.id_appareil_employe = id_appareil_employe;
		this.appareil = appareil;
		this.employe = employe;
	}

	public String getLibelle(){
		return employe.getNom() +"-"+ appareil.getLibelle();
	}

	public String getId_appareil_employe() {
		return this.id_appareil_employe;
	}

	public Appareil getAppareil() {
		return this.appareil;
	}

	public Employe getEmploye() {
		return this.employe;
	}

	public void setId_appareil_employe(String newId_appareil_employe) {
		this.id_appareil_employe = newId_appareil_employe;
	}

	public void setAppareil(Appareil appareil) {
		this.appareil = appareil;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO appareil_employe (id_appareil,id_employe) VALUES (?,?) RETURNING id_appareil_employe";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.appareil.getId_appareil());
            statement.setString(2, this.employe.getId_employe());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_appareil_employe = resultSet.getString("id_appareil_employe");
            }
            System.out.println("Données Appareil_employe insérées avec succès");
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
            String query = "UPDATE appareil_employe SET id_appareil = ?, id_employe = ? WHERE id_appareil_employe = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.appareil.getId_appareil());
            statement.setString(2, this.employe.getId_employe());
            statement.setString(3, getId_appareil_employe());
            statement.executeUpdate();
            System.out.println("Données Appareil_employe mises à jour avec succès");
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

	public static void delete(String id_appareil_employe,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM appareil_employe WHERE id_appareil_employe = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_appareil_employe);
            statement.executeUpdate();
            System.out.println("Données Appareil_employe supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Appareil_employe avec ID id_appareil_employe: " + e.getMessage());
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

	public static List<Appareil_employe> getAll(Connection connection) throws Exception {
		List<Appareil_employe> liste = new ArrayList<Appareil_employe>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM appareil_employe";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Appareil_employe instance = new Appareil_employe();
				instance.setId_appareil_employe(resultSet.getString("id_appareil_employe"));
				Appareil appareil = Appareil.getById(resultSet.getString("id_appareil"),connection);
				instance.setAppareil(appareil);
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
	public static Appareil_employe getById(String id,Connection connection) throws Exception {
		Appareil_employe instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM appareil_employe WHERE id_appareil_employe = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Appareil_employe();
				instance.setId_appareil_employe(resultSet.getString("id_appareil_employe"));
				Appareil appareil = Appareil.getById(resultSet.getString("id_appareil"),connection);
				instance.setAppareil(appareil);
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
