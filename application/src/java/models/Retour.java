package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Retour{
	String id_retour;
	double prix_total_main_doeuvre;
	double prix_total_piece;
	Date date_retour;
	Appareil appareil ;

    public static List<Retour> getByTypeAndCategory(Connection connection, String id_type, String categorie) throws Exception {
        List<Retour> retours = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT DISTINCT * FROM v_getretours WHERE 1=1 ");
    
        if (id_type != null && !id_type.isEmpty()) {
            queryBuilder.append("AND id_typa = ? ");
        }
        if (categorie != null && !categorie.isEmpty()) {
            queryBuilder.append("AND id_categorie = ? ");
        }
    
        String query = queryBuilder.toString();
        System.out.println("Query: " + query);
    
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int parameterIndex = 1;
    
            if (id_type != null && !id_type.isEmpty()) {
                statement.setString(parameterIndex++, id_type);
            }
            if (categorie != null && !categorie.isEmpty()) {
                statement.setString(parameterIndex++, categorie);
            }
    
            System.out.println("Prepared statement: " + statement);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Retour instance = new Retour();
                    instance.setId_retour(resultSet.getString("id_retour"));
                    instance.setPrix_total_main_doeuvre(resultSet.getDouble("prix_total_main_doeuvre"));
                    instance.setPrix_total_piece(resultSet.getDouble("prix_total_piece"));
                    instance.setDate_retour(resultSet.getString("date_retour"));
                    Appareil appareil = Appareil.getById(resultSet.getString("id_appareil"),connection);
                    instance.setAppareil(appareil);
                    retours.add(instance);
                }
            }
        }
        return retours;
    }
    
    

	public Retour(){
	}

	public Retour(String id_retour, double prix_total_main_doeuvre, double prix_total_piece, String date_retour, Appareil appareil) {
		this.setId_retour(id_retour);
		this.setPrix_total_main_doeuvre(prix_total_main_doeuvre);
		this.setPrix_total_piece(prix_total_piece);
        this.setDate_retour(date_retour);
		this.setAppareil(appareil);;
	}

	public String getId_retour() {
		return this.id_retour;
	}

	public double getPrix_total_main_doeuvre() {
		return this.prix_total_main_doeuvre;
	}

	public double getPrix_total_piece() {
		return this.prix_total_piece;
	}

	public Date getDate_retour() {
		return this.date_retour;
	}

	public Appareil getAppareil() {
		return this.appareil;
	}

	public void setId_retour(String newId_retour) {
		this.id_retour = newId_retour;
	}

	public void setPrix_total_main_doeuvre(double newPrix_total_main_doeuvre) {
		this.prix_total_main_doeuvre = newPrix_total_main_doeuvre;
	}

	public void setPrix_total_piece(double newPrix_total_piece) {
		this.prix_total_piece = newPrix_total_piece;
	}

	public void setDate_retour(String newDate_retour) {
		this.date_retour = Date.valueOf(newDate_retour);
	}

	public void setAppareil(Appareil appareil) {
		this.appareil = appareil;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO retour (prix_total_main_doeuvre,prix_total_piece,date_retour,id_appareil) VALUES (?,?,?,?) RETURNING id_retour";
            statement = connection.prepareStatement(query);
            statement.setDouble(1, getPrix_total_main_doeuvre());
            statement.setDouble(2, getPrix_total_piece());
            statement.setDate(3, getDate_retour());
            statement.setString(4, this.appareil.getId_appareil());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_retour = resultSet.getString("id_retour");
            }
            System.out.println("Données Retour insérées avec succès");
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
            String query = "UPDATE retour SET prix_total_main_doeuvre = ?, prix_total_piece = ?, date_retour = ?, id_appareil = ? WHERE id_retour = ?";
            statement = connection.prepareStatement(query);
            statement.setDouble(1, getPrix_total_main_doeuvre());
            statement.setDouble(2, getPrix_total_piece());
            statement.setDate(3, getDate_retour());
            statement.setString(4, this.appareil.getId_appareil());
            statement.setString(5, getId_retour());
            statement.executeUpdate();
            System.out.println("Données Retour mises à jour avec succès");
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

	public static void delete(String id_retour,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM retour WHERE id_retour = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_retour);
            statement.executeUpdate();
            System.out.println("Données Retour supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Retour avec ID id_retour: " + e.getMessage());
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

	public static List<Retour> getAll(Connection connection) throws Exception {
		List<Retour> liste = new ArrayList<Retour>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM retour";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Retour instance = new Retour();
				instance.setId_retour(resultSet.getString("id_retour"));
				instance.setPrix_total_main_doeuvre(resultSet.getDouble("prix_total_main_doeuvre"));
				instance.setPrix_total_piece(resultSet.getDouble("prix_total_piece"));
				instance.setDate_retour(resultSet.getString("date_retour"));
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
    
    public static List<Retour> getByClient(Connection connection, String id_client) throws Exception {
        List<Retour> retours = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        
        // Base query joining retour with appareil to filter by client
        queryBuilder.append("SELECT DISTINCT r.* FROM retour r ")
                   .append("JOIN appareil a ON r.id_appareil = a.id_appareil ")
                   .append("WHERE a.id_client = ?");
    
        String query = queryBuilder.toString();
        System.out.println("Query: " + query);
    
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id_client);
            System.out.println("Prepared statement: " + statement);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Retour instance = new Retour();
                    instance.setId_retour(resultSet.getString("id_retour"));
                    instance.setPrix_total_main_doeuvre(resultSet.getDouble("prix_total_main_doeuvre"));
                    instance.setPrix_total_piece(resultSet.getDouble("prix_total_piece"));
                    instance.setDate_retour(resultSet.getString("date_retour"));
                    Appareil appareil = Appareil.getById(resultSet.getString("id_appareil"), connection);
                    instance.setAppareil(appareil);
                    retours.add(instance);
                }
            }
        }
        return retours;
    }
    
	public static Retour getById(String id,Connection connection) throws Exception {
		Retour instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM retour WHERE id_retour = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Retour();
				instance.setId_retour(resultSet.getString("id_retour"));
				instance.setPrix_total_main_doeuvre(resultSet.getDouble("prix_total_main_doeuvre"));
				instance.setPrix_total_piece(resultSet.getDouble("prix_total_piece"));
				instance.setDate_retour(resultSet.getString("date_retour"));
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
