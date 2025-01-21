package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Entree_piece{
	String id_entree_piece;
	Date date_entree;
	int quantite;
	double prix_unitaire;
	Fornisseur fornisseur ;
	Piece piece ;

	public Entree_piece(){
	}

	public Entree_piece(String id_entree_piece, String date_entree, int quantite, double prix_unitaire, Fornisseur fornisseur, Piece piece) {
		this.setId_entree_piece(id_entree_piece);
		this.setDate_entree(date_entree);
		this.setQuantite(quantite);
		this.setPrix_unitaire(prix_unitaire);
		this.setFornisseur(fornisseur);
		this.setPiece(piece);
	}

	public String getId_entree_piece() {
		return this.id_entree_piece;
	}

	public Date getDate_entree() {
		return this.date_entree;
	}

	public int getQuantite() {
		return this.quantite;
	}

	public double getPrix_unitaire() {
		return this.prix_unitaire;
	}

	public Fornisseur getFornisseur() {
		return this.fornisseur;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public void setId_entree_piece(String newId_entree_piece) {
		this.id_entree_piece = newId_entree_piece;
	}

	public void setDate_entree(String newDate_entree) {
		this.date_entree = Date.valueOf(newDate_entree);
	}

	public void setQuantite(int newQuantite) {
		this.quantite = newQuantite;
	}

	public void setPrix_unitaire(double newPrix_unitaire) {
		this.prix_unitaire = newPrix_unitaire;
	}

	public void setFornisseur(Fornisseur fornisseur) {
		this.fornisseur = fornisseur;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO entree_piece (date_entree,quantite,prix_unitaire,id_fornisseur,id_piece) VALUES (?,?,?,?,?) RETURNING id_entree_piece";
            statement = connection.prepareStatement(query);
            statement.setDate(1, getDate_entree());
            statement.setInt(2, getQuantite());
            statement.setDouble(3, getPrix_unitaire());
            statement.setString(4, this.fornisseur.getId_fornisseur());
            statement.setString(5, this.piece.getId_piece());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_entree_piece = resultSet.getString("id_entree_piece");
            }
            System.out.println("Données Entree_piece insérées avec succès");
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
            String query = "UPDATE entree_piece SET date_entree = ?, quantite = ?, prix_unitaire = ?, id_fornisseur = ?, id_piece = ? WHERE id_entree_piece = ?";
            statement = connection.prepareStatement(query);
            statement.setDate(1, getDate_entree());
            statement.setInt(2, getQuantite());
            statement.setDouble(3, getPrix_unitaire());
            statement.setString(4, this.fornisseur.getId_fornisseur());
            statement.setString(5, this.piece.getId_piece());
            statement.setString(6, getId_entree_piece());
            statement.executeUpdate();
            System.out.println("Données Entree_piece mises à jour avec succès");
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

	public static void delete(String id_entree_piece,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM entree_piece WHERE id_entree_piece = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_entree_piece);
            statement.executeUpdate();
            System.out.println("Données Entree_piece supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Entree_piece avec ID id_entree_piece: " + e.getMessage());
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

	public static List<Entree_piece> getAll(Connection connection) throws Exception {
		List<Entree_piece> liste = new ArrayList<Entree_piece>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM entree_piece";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Entree_piece instance = new Entree_piece();
				instance.setId_entree_piece(resultSet.getString("id_entree_piece"));
				instance.setDate_entree(resultSet.getString("date_entree"));
				instance.setQuantite(resultSet.getInt("quantite"));
				instance.setPrix_unitaire(resultSet.getDouble("prix_unitaire"));
				Fornisseur fornisseur = Fornisseur.getById(resultSet.getString("id_fornisseur"),connection);
				instance.setFornisseur(fornisseur);
				Piece piece = Piece.getById(resultSet.getString("id_piece"),connection);
				instance.setPiece(piece);
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
	public static Entree_piece getById(String id,Connection connection) throws Exception {
		Entree_piece instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM entree_piece WHERE id_entree_piece = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Entree_piece();
				instance.setId_entree_piece(resultSet.getString("id_entree_piece"));
				instance.setDate_entree(resultSet.getString("date_entree"));
				instance.setQuantite(resultSet.getInt("quantite"));
				instance.setPrix_unitaire(resultSet.getDouble("prix_unitaire"));
				Fornisseur fornisseur = Fornisseur.getById(resultSet.getString("id_fornisseur"),connection);
				instance.setFornisseur(fornisseur);
				Piece piece = Piece.getById(resultSet.getString("id_piece"),connection);
				instance.setPiece(piece);
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
