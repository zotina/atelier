package controllers.retour;

import java.io.*;
import java.sql.Connection;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import models.Appareil;
import models.Reparation;
import models.Retour;

@WebServlet("/FormActionRetourServlet")
public class FormActionRetourServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = new Connexion().connect_to_postgres();

			String action = request.getParameter("action");

			if (action != null) {
				switch (action) {	
					case "detailsReparations":
						try {
							getReparationbyApp(request, response, connection);
							// Envoyez une réponse ou redirigez vers une page de succès
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "detailsRetoursClient":
						try {
							getRetoursClient(request, response, connection);
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
			String id_retour = request.getParameter("id_retour");
			double prix_total_main_doeuvre = Double.parseDouble(request.getParameter("prix_total_main_doeuvre"));
			double prix_total_piece = Double.parseDouble(request.getParameter("prix_total_piece"));
			String date_retour = request.getParameter("date_retour");
			String id_appareil = request.getParameter("id_appareil");
			Appareil id_appareilObj = Appareil.getById(id_appareil,connection);
			Retour retour = new Retour(id_retour, prix_total_main_doeuvre, prix_total_piece, date_retour, id_appareilObj);
			retour.insert(connection);
			forwardRequest(request, response, "TraitementRetourServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_retour = request.getParameter("id_retour");
			double prix_total_main_doeuvre = Double.parseDouble(request.getParameter("prix_total_main_doeuvre"));
			double prix_total_piece = Double.parseDouble(request.getParameter("prix_total_piece"));
			String date_retour = request.getParameter("date_retour");
			String id_appareil = request.getParameter("id_appareil");
			Appareil id_appareilObj = Appareil.getById(id_appareil,connection);
			Retour retour = new Retour(id_retour, prix_total_main_doeuvre, prix_total_piece, date_retour, id_appareilObj);
			retour.update(connection);
			forwardRequest(request, response, "TraitementRetourServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_retour = request.getParameter("id_retour");
			Retour.delete(id_retour,connection);
			forwardRequest(request, response, "TraitementRetourServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_retour = request.getParameter("id_retour");
		Retour retour = Retour.getById(id_retour,connection);
		if (retour != null) {
			request.setAttribute("action", "handleUpdate");
			List<Appareil> appareilList = Appareil.getAll(connection);
			request.setAttribute("appareilList", appareilList);
			request.setAttribute("retour", retour);
			forwardRequest(request, response, "pages/retour/formulaireRetour.jsp");
		} else {
			out.println("Retour non trouvé.");
		}
	}

	private void getReparationbyApp(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		try {
			String id_appareil = request.getParameter("id");
			List<Reparation> reparations = Reparation.getAllByAppareil(connection, id_appareil);
			request.setAttribute("reparations", reparations);
			forwardRequest(request, response, "pages/retour/detailsReparation.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getRetoursClient(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		try {
			String id_client = request.getParameter("id_client");
			List<Retour> retours = Retour.getByClient(connection, id_client);
			request.setAttribute("retours", retours);
			forwardRequest(request, response, "pages/retour/detailsRetoursClient.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
