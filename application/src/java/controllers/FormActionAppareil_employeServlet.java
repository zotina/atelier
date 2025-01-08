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


@WebServlet("/FormActionAppareil_employeServlet")
public class FormActionAppareil_employeServlet extends HttpServlet {

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
			String id_appareil_employe = request.getParameter("id_appareil_employe");
			String id_appareil = request.getParameter("id_appareil");
			Appareil id_appareilObj = Appareil.getById(id_appareil,connection);
			String id_employe = request.getParameter("id_employe");
			Employe id_employeObj = Employe.getById(id_employe,connection);
			Appareil_employe appareil_employe = new Appareil_employe(id_appareil_employe, id_appareilObj, id_employeObj);
			appareil_employe.insert(connection);
			forwardRequest(request, response, "TraitementAppareil_employeServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_appareil_employe = request.getParameter("id_appareil_employe");
			String id_appareil = request.getParameter("id_appareil");
			Appareil id_appareilObj = Appareil.getById(id_appareil,connection);
			String id_employe = request.getParameter("id_employe");
			Employe id_employeObj = Employe.getById(id_employe,connection);
			Appareil_employe appareil_employe = new Appareil_employe(id_appareil_employe, id_appareilObj, id_employeObj);
			appareil_employe.update(connection);
			forwardRequest(request, response, "TraitementAppareil_employeServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_appareil_employe = request.getParameter("id_appareil_employe");
			Appareil_employe.delete(id_appareil_employe,connection);
			forwardRequest(request, response, "TraitementAppareil_employeServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_appareil_employe = request.getParameter("id_appareil_employe");
		Appareil_employe appareil_employe = Appareil_employe.getById(id_appareil_employe,connection);
		if (appareil_employe != null) {
			request.setAttribute("action", "handleUpdate");
			List<Appareil> appareilList = Appareil.getAll(connection);
			request.setAttribute("appareilList", appareilList);
			List<Employe> employeList = Employe.getAll(connection);
			request.setAttribute("employeList", employeList);
			request.setAttribute("appareil_employe", appareil_employe);
			forwardRequest(request, response, "pages/formulaireAppareil_employe.jsp");
		} else {
			out.println("Appareil_employe non trouvé.");
		}
	}
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
