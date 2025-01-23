package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Genre{
	String id_genre;
	String libelle;

	public Genre(){
	}

	public Genre(String id_genre, String libelle) {
		this.id_genre = id_genre;
		this.libelle = libelle;
	}

	public String getId_genre() {
		return this.id_genre;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setId_genre(String newId_genre) {
		this.id_genre = newId_genre;
	}

	public void setLibelle(String newLibelle) {
		this.libelle = newLibelle;
	}

	public static List<Genre> getAll(Connection connection) throws Exception {
		List<Genre> liste = new ArrayList<Genre>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM genre";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Genre instance = new Genre();
				instance.setId_genre(resultSet.getString("id_genre"));
				instance.setLibelle(resultSet.getString("libelle"));
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
	public static Genre getById(String id,Connection connection) throws Exception {
		Genre instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM genre WHERE id_genre = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Genre();
				instance.setId_genre(resultSet.getString("id_genre"));
				instance.setLibelle(resultSet.getString("libelle"));
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
