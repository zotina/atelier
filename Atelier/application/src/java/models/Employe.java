package models;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Employe {
	String id_employe;
	String nom;
	String telephone;
	String email;
	String salaire_personnalise;
	Date date_embauche;
	String addresse;
	boolean disponible;
	Role role;

	public static List<Employe> search(Connection connection, String nom, String email, String telephone,
			String salaireMin, String salaireMax, String dateDebut, String dateFin, String roleId) throws Exception {
		List<Employe> liste = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		System.out.println("Search");
		try {
			// Start building the query
			StringBuilder query = new StringBuilder("SELECT * FROM employe WHERE 1=1");

			// Add conditions based on the input parameters
			if (nom != null && !nom.isEmpty()) {
				query.append(" AND LOWER(TRIM(nom)) LIKE LOWER(TRIM(?))");
			}
			if (email != null && !email.isEmpty()) {
				query.append(" AND LOWER(TRIM(email)) LIKE LOWER(TRIM(?))");
			}
			if (telephone != null && !telephone.isEmpty()) {
				query.append(" AND telephone LIKE ?");
			}
			if (salaireMin != null && !salaireMin.isEmpty()) {
				query.append(" AND salaire_personnalise >= ?");
			}
			if (salaireMax != null && !salaireMax.isEmpty()) {
				query.append(" AND salaire_personnalise <= ?");
			}
			if (dateDebut != null && !dateDebut.isEmpty()) {
				query.append(" AND date_embauche >= ?");
			}
			if (dateFin != null && !dateFin.isEmpty()) {
				query.append(" AND date_embauche <= ?");
			}
			if (roleId != null && !roleId.isEmpty()) {
				query.append(" AND id_role = ?");
			}
			System.out.println(query);

			// Prepare the statement with the constructed query
			statement = connection.prepareStatement(query.toString());

			int index = 1;

			// Set parameters for the query
			if (nom != null && !nom.isEmpty()) {
				statement.setString(index++, "%" + nom.trim() + "%");
			}
			if (email != null && !email.isEmpty()) {
				statement.setString(index++, "%" + email.trim() + "%");
			}
			if (telephone != null && !telephone.isEmpty()) {
				statement.setString(index++, "%" + telephone + "%");
			}
			if (salaireMin != null && !salaireMin.isEmpty()) {
				statement.setDouble(index++, Double.parseDouble(salaireMin));
			}
			if (salaireMax != null && !salaireMax.isEmpty()) {
				statement.setDouble(index++, Double.parseDouble(salaireMax));
			}
			if (dateDebut != null && !dateDebut.isEmpty()) {
				statement.setDate(index++, Date.valueOf(dateDebut));
			}
			if (dateFin != null && !dateFin.isEmpty()) {
				statement.setDate(index++, Date.valueOf(dateFin));
			}
			if (roleId != null && !roleId.isEmpty()) {
				statement.setString(index++, roleId);
			}
			System.out.println(query);
			// Execute the query
			resultSet = statement.executeQuery();

			// Process the results
			while (resultSet.next()) {
				Employe instance = new Employe();
				instance.setId_employe(resultSet.getString("id_employe"));
				instance.setNom(resultSet.getString("nom"));
				instance.setTelephone(resultSet.getString("telephone"));
				instance.setEmail(resultSet.getString("email"));
				instance.setSalaire_personnalise(resultSet.getString("salaire_personnalise"));
				instance.setDate_embauche(resultSet.getDate("date_embauche"));
				instance.setAddresse(resultSet.getString("addresse"));
				Role role = Role.getById(resultSet.getString("id_role"), connection);
				instance.setRole(role);
				liste.add(instance);
			}

			return liste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// Close resources
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

	public Employe() {
	}

	public Employe(String id_employe, String nom, String telephone, String email, String salaire_personnalise,
			Date date_embauche, String addresse, Role role) {
		this.id_employe = id_employe;
		this.nom = nom;
		this.telephone = telephone;
		this.email = email;
		this.salaire_personnalise = salaire_personnalise;
		this.date_embauche = date_embauche;
		this.addresse = addresse;
		this.role = role;
	}

	public String getId_employe() {
		return this.id_employe;
	}

	public String getNom() {
		return this.nom;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public String getEmail() {
		return this.email;
	}

	public String getSalaire_personnalise() {
		return this.salaire_personnalise;
	}

	public Date getDate_embauche() {
		return this.date_embauche;
	}

	public String getAddresse() {
		return this.addresse;
	}

	public boolean getDisponible() {
		return this.disponible;
	}

	public Role getRole() {
		return this.role;
	}

	public void setId_employe(String newId_employe) {
		this.id_employe = newId_employe;
	}

	public void setNom(String newNom) {
		this.nom = newNom;
	}

	public void setTelephone(String newTelephone) {
		this.telephone = newTelephone;
	}

	public void setEmail(String newEmail) {
		this.email = newEmail;
	}

	public void setSalaire_personnalise(String newSalaire_personnalise) {
		this.salaire_personnalise = newSalaire_personnalise;
	}

	public void setDate_embauche(Date newDate_embauche) {
		this.date_embauche = newDate_embauche;
	}

	public void setAddresse(String newAddresse) {
		this.addresse = newAddresse;
	}

	public void setDisponible(boolean newDisponible) {
		this.disponible = newDisponible;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void insert(Connection connection) throws Exception {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "INSERT INTO employe (nom,telephone,email,salaire_personnalise,date_embauche,addresse,id_role) VALUES (?,?,?,?,?,?,?) RETURNING id_employe";
			statement = connection.prepareStatement(query);
			statement.setString(1, getNom());
			statement.setString(2, getTelephone());
			statement.setString(3, getEmail());
			statement.setString(4, getSalaire_personnalise());
			statement.setDate(5, getDate_embauche());
			statement.setString(6, getAddresse());
			statement.setString(7, this.role.getId_role());
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				this.id_employe = resultSet.getString("id_employe");
			}
			System.out.println("Données Employe insérées avec succès");
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
			String query = "UPDATE employe SET nom = ?, telephone = ?, email = ?, salaire_personnalise = ?, date_embauche = ?, addresse = ?, id_role = ? WHERE id_employe = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, getNom());
			statement.setString(2, getTelephone());
			statement.setString(3, getEmail());
			statement.setString(4, getSalaire_personnalise());
			statement.setDate(5, getDate_embauche());
			statement.setString(6, getAddresse());
			statement.setString(7, this.role.getId_role());
			statement.setString(8, getId_employe());
			statement.executeUpdate();
			System.out.println("Données Employe mises à jour avec succès");
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

	public static void delete(String id_employe, Connection connection) throws Exception {
		PreparedStatement statement = null;
		try {
			String query = "DELETE FROM employe WHERE id_employe = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id_employe);
			statement.executeUpdate();
			System.out.println("Données Employe supprimées avec succès");
		} catch (Exception e) {
			throw new Exception("Erreur lors de la suppression de Employe avec ID id_employe: " + e.getMessage());
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

	public static List<Employe> getAll(Connection connection) throws Exception {
		List<Employe> liste = new ArrayList<Employe>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM employe";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Employe instance = new Employe();
				instance.setId_employe(resultSet.getString("id_employe"));
				instance.setNom(resultSet.getString("nom"));
				instance.setTelephone(resultSet.getString("telephone"));
				instance.setEmail(resultSet.getString("email"));
				instance.setSalaire_personnalise(resultSet.getString("salaire_personnalise"));
				instance.setDate_embauche(resultSet.getDate("date_embauche"));
				instance.setAddresse(resultSet.getString("addresse"));
				Role role = Role.getById(resultSet.getString("id_role"), connection);
				instance.setRole(role);
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

	public static Employe getById(String id, Connection connection) throws Exception {
		Employe instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM employe WHERE id_employe = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Employe();
				instance.setId_employe(resultSet.getString("id_employe"));
				instance.setNom(resultSet.getString("nom"));
				instance.setTelephone(resultSet.getString("telephone"));
				instance.setEmail(resultSet.getString("email"));
				instance.setSalaire_personnalise(resultSet.getString("salaire_personnalise"));
				instance.setDate_embauche(resultSet.getDate("date_embauche"));
				instance.setAddresse(resultSet.getString("addresse"));
				Role role = Role.getById(resultSet.getString("id_role"), connection);
				instance.setRole(role);
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
