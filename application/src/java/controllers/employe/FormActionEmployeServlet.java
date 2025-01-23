package controllers.employe;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.Connection;
import java.sql.Date;

import models.Employe;
import models.Genre;
import models.Role;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/FormActionEmployeServlet")
public class FormActionEmployeServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = new Connexion().connect_to_postgres();

			String action = request.getParameter("action");

			if (action != null) {
				switch (action) {
					case "detailsEmployer":
						try {
							detailsEmployer(request, response, connection);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "insert":
						try {
							insert(request, response, connection);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "update":
						try {
							update(request, response, connection);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "delete":
						try {
							delete(request, response, connection);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "handleUpdate":
						try {
							handleUpdate(request, response, connection);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					default:
						try {
							forwardRequest(request, response, "TraitementEmployeServlet");
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

private void insert(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_employe = request.getParameter("id_employe");
			String nom = request.getParameter("nom");
			String telephone = request.getParameter("telephone");
			String email = request.getParameter("email");
			String salaire_personnalise = request.getParameter("salaire_personnalise");
			Date date_embauche = Date.valueOf(request.getParameter("date_embauche"));
			String addresse = request.getParameter("addresse");
			String id_role = request.getParameter("id_role");
			Role id_roleObj = Role.getById(id_role,connection);
			String id_genre = request.getParameter("id_genre");
			Genre id_genreObj = Genre.getById(id_genre,connection);
			Employe employe = new Employe(id_employe, nom, telephone, email, salaire_personnalise, date_embauche, addresse, id_roleObj, id_genreObj);
			employe.insert(connection);
			forwardRequest(request, response, "TraitementEmployeServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void detailsEmployer(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		try {
			// Récupérer l'ID de l'employé depuis la requête
			String id = request.getParameter("id");

			// Récupérer l'employé en fonction de son ID
			Employe employe = Employe.getById(id, connection);

			// Créer une liste d'employés
			List<Employe> employeList = new ArrayList<>();

			// Ajouter l'employé à la liste
			if (employe != null) {
				employeList.add(employe);
			}

			// Ajouter la liste à l'attribut de la requête
			request.setAttribute("employers", employeList);

			// Transférer la requête à la page JSP
			forwardRequest(request, response, "pages/appareil/detailsEmployer.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		try {
			String id_employe = request.getParameter("id_employe");
			String nom = request.getParameter("nom");
			String telephone = request.getParameter("telephone");
			String email = request.getParameter("email");
			String salaire_personnalise = request.getParameter("salaire_personnalise");
			Date date_embauche = Date.valueOf(request.getParameter("date_embauche"));
			String addresse = request.getParameter("addresse");
			String id_role = request.getParameter("id_role");
			Role id_roleObj = Role.getById(id_role, connection);
			Employe employe = new Employe(id_employe, nom, telephone, email, salaire_personnalise, date_embauche,
					addresse, id_roleObj);
			employe.update(connection);
			forwardRequest(request, response, "TraitementEmployeServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		try {
			String id_employe = request.getParameter("id_employe");
			Employe.delete(id_employe, connection);
			forwardRequest(request, response, "TraitementEmployeServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		PrintWriter out = response.getWriter();
		String id_employe = request.getParameter("id_employe");
		Employe employe = Employe.getById(id_employe, connection);
		if (employe != null) {
			request.setAttribute("action", "handleUpdate");
			List<Role> roleList = Role.getAll(connection);
			request.setAttribute("roleList", roleList);
			request.setAttribute("employe", employe);
			forwardRequest(request, response, "pages/employe/formulaireEmploye.jsp");
		} else {
			out.println("Employe non trouvé.");
		}
	}

	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
