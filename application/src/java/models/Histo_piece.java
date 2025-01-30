package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Histo_piece{
	String id_histo;
	Piece piece ;
	double prix_unitaire;
	String change_type;
	Date changed_at;
	String changed_by;

	public Histo_piece(){
	}

	public Histo_piece(String id_histo, Piece piece, double prix_unitaire, String change_type, Date changed_at, String changed_by) {
		this.id_histo = id_histo;
		this.piece = piece;
		this.prix_unitaire = prix_unitaire;
		this.change_type = change_type;
		this.changed_at = changed_at;
		this.changed_by = changed_by;
	}

	public String getId_histo() {
		return this.id_histo;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public double getPrix_unitaire() {
		return this.prix_unitaire;
	}

	public String getChange_type() {
		return this.change_type;
	}

	public Date getChanged_at() {
		return this.changed_at;
	}

	public String getChanged_by() {
		return this.changed_by;
	}

	public void setId_histo(String newId_histo) {
		this.id_histo = newId_histo;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public void setPrix_unitaire(double newPrix_unitaire) {
		this.prix_unitaire = newPrix_unitaire;
	}

	public void setChange_type(String newChange_type) {
		this.change_type = newChange_type;
	}

	public void setChanged_at(Date newChanged_at) {
		this.changed_at = newChanged_at;
	}

	public void setChanged_by(String newChanged_by) {
		this.changed_by = newChanged_by;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO histo_piece (id_piece,prix_unitaire,change_type,changed_at,changed_by) VALUES (?,?,?,?,?) RETURNING id_histo";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.piece.getId_piece());
            statement.setDouble(2, getPrix_unitaire());
            statement.setString(3, getChange_type());
            statement.setDate(4, getChanged_at());
            statement.setString(5, getChanged_by());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_histo = resultSet.getString("id_histo");
            }
            System.out.println("Données Histo_piece insérées avec succès");
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
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public void update(Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "UPDATE histo_piece SET id_piece = ?, prix_unitaire = ?, change_type = ?, changed_at = ?, changed_by = ? WHERE id_histo = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.piece.getId_piece());
            statement.setDouble(2, getPrix_unitaire());
            statement.setString(3, getChange_type());
            statement.setDate(4, getChanged_at());
            statement.setString(5, getChanged_by());
            statement.setString(6, getId_histo());
            statement.executeUpdate();
            System.out.println("Données Histo_piece mises à jour avec succès");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public static void delete(String id_histo,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM histo_piece WHERE id_histo = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_histo);
            statement.executeUpdate();
            System.out.println("Données Histo_piece supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Histo_piece avec ID id_histo: " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public static List<Histo_piece> getAll(Connection connection) throws Exception {
		List<Histo_piece> liste = new ArrayList<Histo_piece>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM histo_piece";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Histo_piece instance = new Histo_piece();
				instance.setId_histo(resultSet.getString("id_histo"));
				Piece piece = Piece.getById(resultSet.getString("id_piece"),connection);
				instance.setPiece(piece);
				instance.setPrix_unitaire(resultSet.getDouble("prix_unitaire"));
				instance.setChange_type(resultSet.getString("change_type"));
				instance.setChanged_at(resultSet.getDate("changed_at"));
				instance.setChanged_by(resultSet.getString("changed_by"));
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
	public static Histo_piece getById(String id,Connection connection) throws Exception {
		Histo_piece instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM histo_piece WHERE id_histo = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Histo_piece();
				instance.setId_histo(resultSet.getString("id_histo"));
				Piece piece = Piece.getById(resultSet.getString("id_piece"),connection);
				instance.setPiece(piece);
				instance.setPrix_unitaire(resultSet.getDouble("prix_unitaire"));
				instance.setChange_type(resultSet.getString("change_type"));
				instance.setChanged_at(resultSet.getDate("changed_at"));
				instance.setChanged_by(resultSet.getString("changed_by"));
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
