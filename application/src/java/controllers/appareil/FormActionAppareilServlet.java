package controllers.appareil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.Connection;
import java.sql.Date;

import models.Appareil;
import models.Client;
import models.Model;
import models.Typa;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/FormActionAppareilServlet")
public class FormActionAppareilServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = new Connexion().connect_to_postgres();

			String action = request.getParameter("action");

			if (action != null) {
				switch (action) {
					case "detailsEmployer":
						try {
							detailsEmployer(request, response, connection);
							// Envoyez une réponse ou redirigez vers une page de succès
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
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
			String id_appareil = request.getParameter("id_appareil");
			String numero_serie = request.getParameter("numero_serie");
			String est_repare = request.getParameter("est_repare");
			String desc_probleme = request.getParameter("desc_probleme");
			String date_deposition = request.getParameter("date_deposition");
			String id_typa = request.getParameter("id_typa");
			Typa id_typaObj = Typa.getById(id_typa,connection);
			String id_client = request.getParameter("id_client");
			Client id_clientObj = Client.getById(id_client,connection);
			String id_model = request.getParameter("id_model");
			Model id_modelObj = Model.getById(id_model,connection);
			Appareil appareil = new Appareil(id_appareil, numero_serie, est_repare, desc_probleme, date_deposition, id_typaObj, id_clientObj, id_modelObj);
			appareil.insert(connection);
			forwardRequest(request, response, "TraitementAppareilServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_appareil = request.getParameter("id_appareil");
			String numero_serie = request.getParameter("numero_serie");
			String est_repare = request.getParameter("est_repare");
			String desc_probleme = request.getParameter("desc_probleme");
			String date_deposition = request.getParameter("date_deposition");
			String id_typa = request.getParameter("id_typa");
			Typa id_typaObj = Typa.getById(id_typa,connection);
			String id_client = request.getParameter("id_client");
			Client id_clientObj = Client.getById(id_client,connection);
			String id_model = request.getParameter("id_model");
			Model id_modelObj = Model.getById(id_model,connection);
			Appareil appareil = new Appareil(id_appareil, numero_serie, est_repare, desc_probleme, date_deposition, id_typaObj, id_clientObj, id_modelObj);
			appareil.update(connection);
			forwardRequest(request, response, "TraitementAppareilServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_appareil = request.getParameter("id_appareil");
			Appareil.delete(id_appareil,connection);
			forwardRequest(request, response, "TraitementAppareilServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_appareil = request.getParameter("id_appareil");
		Appareil appareil = Appareil.getById(id_appareil,connection);
		if (appareil != null) {
			request.setAttribute("action", "handleUpdate");
			List<Typa> typaList = Typa.getAll(connection);
			request.setAttribute("typaList", typaList);
			List<Client> clientList = Client.getAll(connection);
			request.setAttribute("clientList", clientList);
			List<Model> modelList = Model.getAll(connection);
			request.setAttribute("modelList", modelList);
			request.setAttribute("appareil", appareil);
			forwardRequest(request, response, "pages/appareil/formulaireAppareil.jsp");
		} else {
			out.println("Appareil non trouvé.");
		}
	}

	private void detailsEmployer(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		try {
			String id_appareil = request.getParameter("id");
			Appareil appareil = Appareil.getById(id_appareil, connection);
			System.out.println(appareil);
			request.setAttribute("employers", appareil.getEmployesAttribues(connection));
			forwardRequest(request, response, "pages/appareil/detailsEmployer.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
