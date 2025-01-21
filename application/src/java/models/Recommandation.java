package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Recommandation{
	String id_recommandation;
	Date date_recommandation;
	Piece piece ;

	public Recommandation(){
	}

	public Recommandation(String id_recommandation, Date date_recommandation, Piece piece) {
		this.id_recommandation = id_recommandation;
		this.date_recommandation = date_recommandation;
		this.piece = piece;
	}

	public String getId_recommandation() {
		return this.id_recommandation;
	}

	public Date getDate_recommandation() {
		return this.date_recommandation;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public void setId_recommandation(String newId_recommandation) {
		this.id_recommandation = newId_recommandation;
	}

	public void setDate_recommandation(Date newDate_recommandation) {
		this.date_recommandation = newDate_recommandation;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO recommandation (date_recommandation,id_piece) VALUES (?,?) RETURNING id_recommandation";
            statement = connection.prepareStatement(query);
            statement.setDate(1, getDate_recommandation());
            statement.setString(2, this.piece.getId_piece());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_recommandation = resultSet.getString("id_recommandation");
            }
            System.out.println("Données Recommandation insérées avec succès");
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
            String query = "UPDATE recommandation SET date_recommandation = ?, id_piece = ? WHERE id_recommandation = ?";
            statement = connection.prepareStatement(query);
            statement.setDate(1, getDate_recommandation());
            statement.setString(2, this.piece.getId_piece());
            statement.setString(3, getId_recommandation());
            statement.executeUpdate();
            System.out.println("Données Recommandation mises à jour avec succès");
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

	public static void delete(String id_recommandation,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM recommandation WHERE id_recommandation = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_recommandation);
            statement.executeUpdate();
            System.out.println("Données Recommandation supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Recommandation avec ID id_recommandation: " + e.getMessage());
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

	public static List<Recommandation> getAll(Connection connection) throws Exception {
		List<Recommandation> liste = new ArrayList<Recommandation>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "WITH LastRecommandations AS ("
			+ " SELECT DISTINCT ON (id_piece, TO_CHAR(date_recommandation, 'YYYY-MM')) *"
			+ " FROM recommandation"
			+ " WHERE EXTRACT(YEAR FROM date_recommandation) = 2024"
			+ " ORDER BY id_piece, TO_CHAR(date_recommandation, 'YYYY-MM'), date_recommandation DESC"
			+ ")"
			+ " SELECT * FROM LastRecommandations"
			+ " ORDER BY date_recommandation DESC";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Recommandation instance = new Recommandation();
				instance.setId_recommandation(resultSet.getString("id_recommandation"));
				instance.setDate_recommandation(resultSet.getDate("date_recommandation"));
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
	public static Recommandation getById(String id,Connection connection) throws Exception {
		Recommandation instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM recommandation WHERE id_recommandation = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Recommandation();
				instance.setId_recommandation(resultSet.getString("id_recommandation"));
				instance.setDate_recommandation(resultSet.getDate("date_recommandation"));
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
	public static List<Recommandation> filterByMonth(String monthYear, Connection connection) throws Exception {
		List<Recommandation> liste = new ArrayList<Recommandation>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			// Vérifie le format de la date (YYYY-MM)
			if (!monthYear.matches("\\d{4}-\\d{2}")) {
				throw new Exception("Format de date invalide. Format attendu : YYYY-MM");
			}
	
			String query = "WITH LastRecommandations AS (" +
                      "  SELECT DISTINCT ON (id_piece) *" +
                      "  FROM recommandation" +
                      "  WHERE TO_CHAR(date_recommandation, 'YYYY-MM') = ?" +
                      "  ORDER BY id_piece, date_recommandation DESC" +
                      ")" +
                      "SELECT * FROM LastRecommandations";
			
			statement = connection.prepareStatement(query);
			statement.setString(1, monthYear);
			
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Recommandation instance = new Recommandation();
				instance.setId_recommandation(resultSet.getString("id_recommandation"));
				instance.setDate_recommandation(resultSet.getDate("date_recommandation"));
				Piece piece = Piece.getById(resultSet.getString("id_piece"), connection);
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
}
