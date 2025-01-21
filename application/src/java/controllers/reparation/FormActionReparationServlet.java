package controllers.reparation;
import java.io.*;
import java.util.List;
import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.Date;

import models.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/FormActionReparationServlet")
public class FormActionReparationServlet extends HttpServlet {

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
			String id_reparation = request.getParameter("id_reparation");
			String date_debut = request.getParameter("date_debut");
			double prix = Double.parseDouble(request.getParameter("prix"));
			String id_etat = request.getParameter("id_etat");
			Etat_reparation id_etatObj = Etat_reparation.getById(id_etat,connection);
			String id_categorie = request.getParameter("id_categorie");
			Categorie id_categorieObj = Categorie.getById(id_categorie,connection);
			String id_appareil = request.getParameter("id_appareil");
			Appareil id_appareilObj = Appareil.getById(id_appareil,connection);
			Reparation reparation = new Reparation(id_reparation, date_debut, prix, id_etatObj, id_categorieObj, id_appareilObj);
			reparation.insert(connection);
			forwardRequest(request, response, "TraitementReparationServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_reparation = request.getParameter("id_reparation");
			String date_debut = request.getParameter("date_debut");
			double prix = Double.parseDouble(request.getParameter("prix"));
			String id_etat = request.getParameter("id_etat");
			Etat_reparation id_etatObj = Etat_reparation.getById(id_etat,connection);
			String id_categorie = request.getParameter("id_categorie");
			Categorie id_categorieObj = Categorie.getById(id_categorie,connection);
			String id_appareil = request.getParameter("id_appareil");
			Appareil id_appareilObj = Appareil.getById(id_appareil,connection);
			Reparation reparation = new Reparation(id_reparation, date_debut, prix, id_etatObj, id_categorieObj, id_appareilObj);
			reparation.update(connection);
			forwardRequest(request, response, "TraitementReparationServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_reparation = request.getParameter("id_reparation");
			Reparation.delete(id_reparation,connection);
			forwardRequest(request, response, "TraitementReparationServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_reparation = request.getParameter("id_reparation");
		Reparation reparation = Reparation.getById(id_reparation,connection);
		if (reparation != null) {
			request.setAttribute("action", "handleUpdate");
			List<Etat_reparation> etat_reparationList = Etat_reparation.getAll(connection);
			request.setAttribute("etat_reparationList", etat_reparationList);
			List<Categorie> categorieList = Categorie.getAll(connection);
			request.setAttribute("categorieList", categorieList);
			List<Appareil> appareilList = Appareil.getAll(connection);
			request.setAttribute("appareilList", appareilList);
			request.setAttribute("reparation", reparation);
			forwardRequest(request, response, "pages/reparation/formulaireReparation.jsp");
		} else {
			out.println("Reparation non trouvé.");
		}
	}
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
