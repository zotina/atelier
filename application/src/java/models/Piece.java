package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Piece{
	String id_piece;
	String numero_serie;
	double prix_unitaire;
	String reference;
	String description;
	Categorie categorie ;
	Marque marque ;

	public Piece(){
	}

	public Piece(String id_piece, String numero_serie, double prix_unitaire, String reference, String description, Categorie categorie, Marque marque) {
		this.id_piece = id_piece;
		this.numero_serie = numero_serie;
		this.prix_unitaire = prix_unitaire;
		this.reference = reference;
		this.description = description;
		this.categorie = categorie;
		this.marque = marque;
	}

	public String getId_piece() {
		return this.id_piece;
	}

	public String getNumero_serie() {
		return this.numero_serie;
	}

	public double getPrix_unitaire() {
		return this.prix_unitaire;
	}

	public String getReference() {
		return this.reference;
	}

	public String getDescription() {
		return this.description;
	}

	public Categorie getCategorie() {
		return this.categorie;
	}

	public Marque getMarque() {
		return this.marque;
	}

	public void setId_piece(String newId_piece) {
		this.id_piece = newId_piece;
	}

	public void setNumero_serie(String newNumero_serie) {
		this.numero_serie = newNumero_serie;
	}

	public void setPrix_unitaire(double newPrix_unitaire) {
		this.prix_unitaire = newPrix_unitaire;
	}

	public void setReference(String newReference) {
		this.reference = newReference;
	}

	public void setDescription(String newDescription) {
		this.description = newDescription;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public void setMarque(Marque marque) {
		this.marque = marque;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO piece (numero_serie,prix_unitaire,reference,description,id_categorie,id_marque) VALUES (?,?,?,?,?,?) RETURNING id_piece";
            statement = connection.prepareStatement(query);
            statement.setString(1, getNumero_serie());
            statement.setDouble(2, getPrix_unitaire());
            statement.setString(3, getReference());
            statement.setString(4, getDescription());
            statement.setString(5, this.categorie.getId_categorie());
            statement.setString(6, this.marque.getId_marque());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_piece = resultSet.getString("id_piece");
            }
            System.out.println("Données Piece insérées avec succès");
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
            String query = "UPDATE piece SET numero_serie = ?, prix_unitaire = ?, reference = ?, description = ?, id_categorie = ?, id_marque = ? WHERE id_piece = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, getNumero_serie());
            statement.setDouble(2, getPrix_unitaire());
            statement.setString(3, getReference());
            statement.setString(4, getDescription());
            statement.setString(5, this.categorie.getId_categorie());
            statement.setString(6, this.marque.getId_marque());
            statement.setString(7, getId_piece());
            statement.executeUpdate();	
            System.out.println("Données Piece mises à jour avec succès");
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

	public static void delete(String id_piece,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM piece WHERE id_piece = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_piece);
            statement.executeUpdate();
            System.out.println("Données Piece supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Piece avec ID id_piece: " + e.getMessage());
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

	public static List<Piece> getAll(Connection connection) throws Exception {
		List<Piece> liste = new ArrayList<Piece>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM piece";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Piece instance = new Piece();
				instance.setId_piece(resultSet.getString("id_piece"));
				instance.setNumero_serie(resultSet.getString("numero_serie"));
				instance.setPrix_unitaire(resultSet.getDouble("prix_unitaire"));
				instance.setReference(resultSet.getString("reference"));
				instance.setDescription(resultSet.getString("description"));
				Categorie categorie = Categorie.getById(resultSet.getString("id_categorie"),connection);
				instance.setCategorie(categorie);
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
	public static Piece getById(String id,Connection connection) throws Exception {
		Piece instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM piece WHERE id_piece = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Piece();
				instance.setId_piece(resultSet.getString("id_piece"));
				instance.setNumero_serie(resultSet.getString("numero_serie"));
				instance.setPrix_unitaire(resultSet.getDouble("prix_unitaire"));
				instance.setReference(resultSet.getString("reference"));
				instance.setDescription(resultSet.getString("description"));
				Categorie categorie = Categorie.getById(resultSet.getString("id_categorie"),connection);
				instance.setCategorie(categorie);
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
