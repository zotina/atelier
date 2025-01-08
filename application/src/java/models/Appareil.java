package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Appareil {
	String id_appareil;
	String numero_serie;
	Object est_repare;
	String desc_probleme;
	LocalDateTime date_deposition;
	LocalDateTime date_recuperation;
	Object estrecuperer;
	Client client;
	Model model;

	public List<Employe> getEmployesAttribues(Connection connection) throws Exception {
		List<Employe> liste = new ArrayList<Employe>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM historique_interventions_appareils where id_appareil = ? ";
			statement = connection.prepareStatement(query);
			statement.setString(1, getId_appareil());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Employe instance = new Employe();
				instance.setId_employe(resultSet.getString("id_employe"));
				instance.setNom(resultSet.getString("nom"));
				instance.setTelephone(resultSet.getString("telephone"));
				instance.setEmail(resultSet.getString("email"));
				liste.add(instance);
			}
			return liste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
				}
		}
	}

	public Appareil() {
	}

	public Appareil(String id_appareil, String numero_serie, String desc_probleme, String date_deposition,
			Client client, Model model) {
		this.setId_appareil(id_appareil);
		this.setNumero_serie(numero_serie);
		this.setDesc_probleme(desc_probleme);
		this.setDate_deposition(date_deposition);
		this.setClient(client);
		this.setModel(model);
	}

	public String getLibelle() {
		return model.getMarque().getNom() + " " + model.getLibelle();
	}

	public String getId_appareil() {
		return this.id_appareil;
	}

	public String getNumero_serie() {
		return this.numero_serie;
	}

	public Object getEst_repare() {
		return this.est_repare;
	}

	public String getDesc_probleme() {
		return this.desc_probleme;
	}

	public LocalDateTime getDate_deposition() {
		return this.date_deposition;
	}

	public LocalDateTime getDate_recuperation() {
		return this.date_recuperation;
	}

	public Object getEstrecuperer() {
		return this.estrecuperer;
	}

	public Client getClient() {
		return this.client;
	}

	public Model getModel() {
		return this.model;
	}

	public void setId_appareil(String newId_appareil) {
		this.id_appareil = newId_appareil;
	}

	public void setNumero_serie(String newNumero_serie) {
		this.numero_serie = newNumero_serie;
	}

	public void setEst_repare(Object newEst_repare) {
		this.est_repare = newEst_repare;
	}

	public void setDesc_probleme(String newDesc_probleme) {
		this.desc_probleme = newDesc_probleme;
	}

	public void setDate_deposition(String newDate_deposition) {
		if (newDate_deposition != null && !newDate_deposition.isEmpty()) {
			try {
				DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				this.date_deposition = LocalDateTime.parse(newDate_deposition, formatter1);
			} catch (DateTimeParseException e) {
				try {
					DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
					this.date_deposition = LocalDateTime.parse(newDate_deposition, formatter2);
				} catch (DateTimeParseException e2) {
					throw new IllegalArgumentException(
							"Format de date invalide. Formats acceptés : 'yyyy-MM-dd HH:mm:ss' ou 'yyyy-MM-dd'T'HH:mm'");
				}
			}
		}
	}

	public void setDate_recuperation(String newDate_recuperation) {
		if (newDate_recuperation != null && newDate_recuperation != "") {
			try {
				DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				this.date_recuperation = LocalDateTime.parse(newDate_recuperation, formatter1);
			} catch (DateTimeParseException e) {
				try {
					DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
					this.date_recuperation = LocalDateTime.parse(newDate_recuperation, formatter2);
				} catch (DateTimeParseException e2) {
					throw new IllegalArgumentException(
							"Format de date invalide. Formats acceptés : 'yyyy-MM-dd HH:mm:ss' ou 'yyyy-MM-dd'T'HH:mm'");
				}
			}
		}
	}

	public void setEstrecuperer(Object newEstrecuperer) {
		this.estrecuperer = newEstrecuperer;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void insert(Connection connection) throws Exception {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "INSERT INTO appareil (numero_serie,desc_probleme,date_deposition,id_client,id_model) VALUES (?,?,?,?,?) RETURNING id_appareil";
			statement = connection.prepareStatement(query);
			statement.setString(1, getNumero_serie());
			statement.setString(2, getDesc_probleme());
			statement.setTimestamp(3, Timestamp.valueOf(getDate_deposition()));
			statement.setString(4, this.client.getId_client());
			statement.setString(5, this.model.getId_model());
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				this.id_appareil = resultSet.getString("id_appareil");
			}
			System.out.println("Données Appareil insérées avec succès");
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
			String query = "UPDATE appareil SET numero_serie = ?, desc_probleme = ?, date_deposition = ?, id_client = ?, id_model = ? WHERE id_appareil = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, getNumero_serie());
			statement.setString(2, getDesc_probleme());
			statement.setTimestamp(3, Timestamp.valueOf(getDate_deposition()));
			statement.setString(4, this.client.getId_client());
			statement.setString(5, this.model.getId_model());
			statement.setString(6, getId_appareil());
			statement.executeUpdate();
			System.out.println("Données Appareil mises à jour avec succès");
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

	public static void delete(String id_appareil, Connection connection) throws Exception {
		PreparedStatement statement = null;
		try {
			String query = "DELETE FROM appareil WHERE id_appareil = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id_appareil);
			statement.executeUpdate();
			System.out.println("Données Appareil supprimées avec succès");
		} catch (Exception e) {
			throw new Exception("Erreur lors de la suppression de Appareil avec ID id_appareil: " + e.getMessage());
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

	public static List<Appareil> getAll(Connection connection) throws Exception {
		List<Appareil> liste = new ArrayList<Appareil>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM appareil";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Appareil instance = new Appareil();
				instance.setId_appareil(resultSet.getString("id_appareil"));
				instance.setNumero_serie(resultSet.getString("numero_serie"));
				instance.setEst_repare(resultSet.getString("est_repare"));
				instance.setDesc_probleme(resultSet.getString("desc_probleme"));
				instance.setDate_deposition(resultSet.getString("date_deposition"));
				instance.setDate_recuperation(resultSet.getString("date_recuperation"));
				instance.setEstrecuperer(resultSet.getString("estrecuperer"));
				Client client = Client.getById(resultSet.getString("id_client"), connection);
				instance.setClient(client);
				Model model = Model.getById(resultSet.getString("id_model"), connection);
				instance.setModel(model);
				liste.add(instance);
			}
			return liste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
				}
		}
	}

	public static Appareil getById(String id, Connection connection) throws Exception {
		Appareil instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM appareil WHERE id_appareil = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Appareil();
				instance.setId_appareil(resultSet.getString("id_appareil"));
				instance.setNumero_serie(resultSet.getString("numero_serie"));
				instance.setEst_repare(resultSet.getString("est_repare"));
				instance.setDesc_probleme(resultSet.getString("desc_probleme"));
				instance.setDate_deposition(resultSet.getString("date_deposition"));
				instance.setDate_recuperation(resultSet.getString("date_recuperation"));
				instance.setEstrecuperer(resultSet.getString("estrecuperer"));
				Client client = Client.getById(resultSet.getString("id_client"), connection);
				instance.setClient(client);
				Model model = Model.getById(resultSet.getString("id_model"), connection);
				instance.setModel(model);
			}
			return instance;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
				}
		}
	}
}
