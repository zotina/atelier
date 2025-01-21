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

@WebServlet("/TraitementReparationServlet")
public class TraitementReparationServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = new Connexion().connect_to_postgres();
			String action = request.getParameter("action");

			if (action != null) {
				switch (action) {
					case "search":
						try {
							search(request, response, connection);
						} catch (Exception e) {
							e.printStackTrace();
							throw new ServletException(e);
						}
						break;

					case "form":
						try {
							loadformulaire(request, response,connection);
						} catch (Exception e) {
							e.printStackTrace();
							throw new ServletException(e);
						}
						break;
					default:
						try {
							Default(request, response, connection);
						} catch (Exception e) {
							e.printStackTrace();
							throw new ServletException(e);
						}
						break;
				}
			} else {
				try {
					Default(request, response, connection);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServletException(e);
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

	private void search(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
		String diagnostic = request.getParameter("diagnostic");
		String dateDebutMin = request.getParameter("date_debut_min");
		String dateDebutMax = request.getParameter("date_debut_max");
		String idEtat = request.getParameter("id_etat");
		String idCategorie = request.getParameter("id_categorie");
		String idAppareil = request.getParameter("id_appareil");
	
		List<Reparation> reparationList = Reparation.search(connection, diagnostic, dateDebutMin, dateDebutMax, idEtat, idCategorie, idAppareil);
		request.setAttribute("reparationList", reparationList);
		List<Appareil> appareilList = Appareil.getAll(connection);
		request.setAttribute("appareilList", appareilList);
		List<Categorie> categorieList = Categorie.getAll(connection);
		request.setAttribute("categorieList", categorieList);
		List<Etat_reparation> etat_reparationList = Etat_reparation.getAll(connection);
		request.setAttribute("etat_reparationList", etat_reparationList);
		forwardRequest(request, response, "pages/reparation/reparation.jsp");
	}
	

	private void Default(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		List<Appareil> appareilList = Appareil.getAll(connection);
		request.setAttribute("appareilList", appareilList);
		List<Categorie> categorieList = Categorie.getAll(connection);
		request.setAttribute("categorieList", categorieList);
		List<Etat_reparation> etat_reparationList = Etat_reparation.getAll(connection);
		request.setAttribute("etat_reparationList", etat_reparationList);
		List<Reparation> reparationList = Reparation.getAll(connection);
		request.setAttribute("reparationList", reparationList);
		forwardRequest(request, response, "pages/reparation/reparation.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
	private void loadformulaire(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		List<Appareil> appareilList = Appareil.getAll(connection);
		request.setAttribute("appareilList", appareilList);
		List<Categorie> categorieList = Categorie.getAll(connection);
		request.setAttribute("categorieList", categorieList);
		List<Etat_reparation> etat_reparationList = Etat_reparation.getAll(connection);
		request.setAttribute("etat_reparationList", etat_reparationList);
		forwardRequest(request, response, "pages/reparation/formulaireReparation.jsp");
	}

}
