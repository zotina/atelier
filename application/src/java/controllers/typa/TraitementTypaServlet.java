package controllers.typa;

import java.io.*;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import models.Typa;

import java.sql.Connection;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/TraitementTypaServlet")
public class TraitementTypaServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = new Connexion().connect_to_postgres();
			String action = request.getParameter("action");

			if (action != null) {
				switch (action) {
					case "form":
						try {
							loadForm(request, response, connection);
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
		// Récupérer tous les enregistrements de la table typa
		List<Typa> typaList = Typa.getAll(connection);
		request.setAttribute("typaList", typaList);

		// Rediriger vers la page principale
		forwardRequest(request, response, "pages/typa/typa.jsp");
	}

	private void loadForm(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		// Charger les informations nécessaires pour le formulaire
		// (Ici, nous utilisons simplement le formulaire sans données supplémentaires)
		forwardRequest(request, response, "pages/typa/formulaireTypa.jsp");
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
}
