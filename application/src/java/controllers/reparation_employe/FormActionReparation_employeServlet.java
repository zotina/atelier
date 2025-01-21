package controllers.reparation_employe;
import java.io.*;
import java.util.List;
import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.Date;

import models.Employe;
import models.Reparation;
import models.Reparation_employe;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/FormActionReparation_employeServlet")
public class FormActionReparation_employeServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = new Connexion().connect_to_postgres();

			String action = request.getParameter("action");

			if (action != null) {
				switch (action) {
					case "insert":
						try {
							insert(request, response, connection);
							// Envoyez une réponse ou redirigez vers une page de succès
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "update":
						try {
							update(request, response, connection);
							// Envoyez une réponse ou redirigez vers une page de succès
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "delete":
						try {
							delete(request, response, connection);
							// Envoyez une réponse ou redirigez vers une page de succès
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
						// Gérez les cas d'action non reconnus
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void insert(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_reparation_employe = request.getParameter("id_reparation_employe");
			String id_reparation = request.getParameter("id_reparation");
			Reparation id_reparationObj = Reparation.getById(id_reparation,connection);
			String id_employe = request.getParameter("id_employe");
			Employe id_employeObj = Employe.getById(id_employe,connection);
			Reparation_employe reparation_employe = new Reparation_employe(id_reparation_employe, id_reparationObj, id_employeObj);
			reparation_employe.insert(connection);
			forwardRequest(request, response, "TraitementReparation_employeServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_reparation_employe = request.getParameter("id_reparation_employe");
			String id_reparation = request.getParameter("id_reparation");
			Reparation id_reparationObj = Reparation.getById(id_reparation,connection);
			String id_employe = request.getParameter("id_employe");
			Employe id_employeObj = Employe.getById(id_employe,connection);
			Reparation_employe reparation_employe = new Reparation_employe(id_reparation_employe, id_reparationObj, id_employeObj);
			reparation_employe.update(connection);
			forwardRequest(request, response, "TraitementReparation_employeServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_reparation_employe = request.getParameter("id_reparation_employe");
			Reparation_employe.delete(id_reparation_employe,connection);
			forwardRequest(request, response, "TraitementReparation_employeServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_reparation_employe = request.getParameter("id_reparation_employe");
		Reparation_employe reparation_employe = Reparation_employe.getById(id_reparation_employe,connection);
		if (reparation_employe != null) {
			request.setAttribute("action", "handleUpdate");
			List<Reparation> reparationList = Reparation.getAll(connection);
			request.setAttribute("reparationList", reparationList);
			List<Employe> employeList = Employe.getAll(connection);
			request.setAttribute("employeList", employeList);
			request.setAttribute("reparation_employe", reparation_employe);
			forwardRequest(request, response, "pages/reparation/formulaireReparation_employe.jsp");
		} else {
			out.println("Reparation_employe non trouvé.");
		}
	}
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
