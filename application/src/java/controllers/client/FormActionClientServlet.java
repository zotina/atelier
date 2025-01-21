package controllers.client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import models.Client;

import java.sql.Connection;
import java.sql.Date;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/FormActionClientServlet")
public class FormActionClientServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = new Connexion().connect_to_postgres();

			String action = request.getParameter("action");

			if (action != null) {
				switch (action) {
					case "detailsClient":
						try {
							getClient(request, response, connection);
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
			String id_client = request.getParameter("id_client");
			String email = request.getParameter("email");
			String nom = request.getParameter("nom");
			int telephone = Integer.parseInt(request.getParameter("telephone"));
			String addresse = request.getParameter("addresse");
			Client client = new Client(id_client, email, nom, telephone, addresse);
			client.insert(connection);
			forwardRequest(request, response, "TraitementClientServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_client = request.getParameter("id_client");
			String email = request.getParameter("email");
			String nom = request.getParameter("nom");
			int telephone = Integer.parseInt(request.getParameter("telephone"));
			String addresse = request.getParameter("addresse");
			Client client = new Client(id_client, email, nom, telephone, addresse);
			client.update(connection);
			forwardRequest(request, response, "TraitementClientServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_client = request.getParameter("id_client");
			Client.delete(id_client,connection);
			forwardRequest(request, response, "TraitementClientServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_client = request.getParameter("id_client");
		Client client = Client.getById(id_client,connection);
		if (client != null) {
			request.setAttribute("action", "handleUpdate");
			request.setAttribute("client", client);
			forwardRequest(request, response, "pages/client/formulaireClient.jsp");
		} else {
			out.println("Client non trouvé.");
		}
	}

	private void getClient(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_client = request.getParameter("id_client");
		Client client = Client.getById(id_client,connection);
		if (client != null) {
			request.setAttribute("client", client);
			forwardRequest(request, response, "pages/appareil/detailsClient.jsp");
		} else {
			out.println("Client non trouvé.");
		}
	}

	
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
