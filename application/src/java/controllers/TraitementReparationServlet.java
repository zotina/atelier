package controllers;

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

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
						}
						break;
					case "form":
						try {
							loadformulaire(request, response, connection);
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

	private void Default(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		List<Etat_reparation> etat_reparationList = Etat_reparation.getAll(connection);
		request.setAttribute("etat_reparationList", etat_reparationList);
		List<Appareil_employe> appareil_employeList = Appareil_employe.getAll(connection);
		request.setAttribute("appareil_employeList", appareil_employeList);
		List<Reparation> reparationList = Reparation.getAll(connection);
		request.setAttribute("reparationList", reparationList);
		List<Categorie> categorieList = Categorie.getAll(connection);
		request.setAttribute("categorieList", categorieList);
		forwardRequest(request, response, "pages/reparation.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	private void loadformulaire(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		List<Etat_reparation> etat_reparationList = Etat_reparation.getAll(connection);
		request.setAttribute("etat_reparationList", etat_reparationList);
		List<Appareil_employe> appareil_employeList = Appareil_employe.getAll(connection);
		request.setAttribute("appareil_employeList", appareil_employeList);
		forwardRequest(request, response, "pages/formulaireReparation.jsp");
	}
	private void search(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		try {
			String categoire = request.getParameter("categorie");

			// Call the search method with all parameters
			List<Reparation> repationList = Reparation.fitreBycategorie(connection,categoire);
			request.setAttribute("reparationList", repationList);
			List<Etat_reparation> etat_reparationList = Etat_reparation.getAll(connection);
			request.setAttribute("etat_reparationList", etat_reparationList);
			List<Appareil_employe> appareil_employeList = Appareil_employe.getAll(connection);
			request.setAttribute("appareil_employeList", appareil_employeList);
			List<Categorie> categorieList = Categorie.getAll(connection);
			request.setAttribute("categorieList", categorieList);
			// Redirect to the results page
			forwardRequest(request, response, "pages/reparation.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
