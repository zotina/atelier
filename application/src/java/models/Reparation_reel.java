package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Reparation_reel{
	String id_reparation_reel;
	Reparation reparation ;
	Piece piece ;
	int quantite;
	Date date_sortie;

	public Reparation_reel(){
	}

	public Reparation_reel(String id_reparation_reel, Reparation reparation, Piece piece, int quantite, Date date_sortie) {
		this.id_reparation_reel = id_reparation_reel;
		this.reparation = reparation;
		this.piece = piece;
		this.quantite = quantite;
		this.date_sortie = date_sortie;
	}

	public String getId_reparation_reel() {
		return this.id_reparation_reel;
	}

	public Reparation getReparation() {
		return this.reparation;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public int getQuantite() {
		return this.quantite;
	}

	public Date getDate_sortie() {
		return this.date_sortie;
	}

	public void setId_reparation_reel(String newId_reparation_reel) {
		this.id_reparation_reel = newId_reparation_reel;
	}

	public void setReparation(Reparation reparation) {
		this.reparation = reparation;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public void setQuantite(int newQuantite) {
		this.quantite = newQuantite;
	}

	public void setDate_sortie(Date newDate_sortie) {
		this.date_sortie = newDate_sortie;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO reparation_reel (id_reparation,id_piece,quantite,date_sortie) VALUES (?,?,?,?) RETURNING id_reparation_reel";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.reparation.getId_reparation());
            statement.setString(2, this.piece.getId_piece());
            statement.setInt(3, getQuantite());
            statement.setDate(4, getDate_sortie());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_reparation_reel = resultSet.getString("id_reparation_reel");
            }
            System.out.println("Données Reparation_reel insérées avec succès");
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
            String query = "UPDATE reparation_reel SET id_reparation = ?, id_piece = ?, quantite = ?, date_sortie = ? WHERE id_reparation_reel = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.reparation.getId_reparation());
            statement.setString(2, this.piece.getId_piece());
            statement.setInt(3, getQuantite());
            statement.setDate(4, getDate_sortie());
            statement.setString(5, getId_reparation_reel());
            statement.executeUpdate();
            System.out.println("Données Reparation_reel mises à jour avec succès");
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

	public static void delete(String id_reparation_reel,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM reparation_reel WHERE id_reparation_reel = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_reparation_reel);
            statement.executeUpdate();
            System.out.println("Données Reparation_reel supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Reparation_reel avec ID id_reparation_reel: " + e.getMessage());
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

	public static List<Reparation_reel> getAll(Connection connection) throws Exception {
		List<Reparation_reel> liste = new ArrayList<Reparation_reel>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reparation_reel";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Reparation_reel instance = new Reparation_reel();
				instance.setId_reparation_reel(resultSet.getString("id_reparation_reel"));
				Reparation reparation = Reparation.getById(resultSet.getString("id_reparation"),connection);
				instance.setReparation(reparation);
				Piece piece = Piece.getById(resultSet.getString("id_piece"),connection);
				instance.setPiece(piece);
				instance.setQuantite(resultSet.getInt("quantite"));
				instance.setDate_sortie(resultSet.getDate("date_sortie"));
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

	public static Reparation_reel getById(String id,Connection connection) throws Exception {
		Reparation_reel instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reparation_reel WHERE id_reparation_reel = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Reparation_reel();
				instance.setId_reparation_reel(resultSet.getString("id_reparation_reel"));
				Reparation reparation = Reparation.getById(resultSet.getString("id_reparation"),connection);
				instance.setReparation(reparation);
				Piece piece = Piece.getById(resultSet.getString("id_piece"),connection);
				instance.setPiece(piece);
				instance.setQuantite(resultSet.getInt("quantite"));
				instance.setDate_sortie(resultSet.getDate("date_sortie"));
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
