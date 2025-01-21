package controllers.recommandation;
import java.io.*;
import java.util.List;
import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.Date;

import models.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/FormActionRecommandationServlet")
public class FormActionRecommandationServlet extends HttpServlet {

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
			String id_recommandation = request.getParameter("id_recommandation");
			Date date_recommandation = Date.valueOf(request.getParameter("date_recommandation"));
			String id_piece = request.getParameter("id_piece");
			Piece id_pieceObj = Piece.getById(id_piece,connection);
			Recommandation recommandation = new Recommandation(id_recommandation, date_recommandation, id_pieceObj);
			recommandation.insert(connection);
			forwardRequest(request, response, "TraitementRecommandationServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_recommandation = request.getParameter("id_recommandation");
			Date date_recommandation = Date.valueOf(request.getParameter("date_recommandation"));
			String id_piece = request.getParameter("id_piece");
			Piece id_pieceObj = Piece.getById(id_piece,connection);
			Recommandation recommandation = new Recommandation(id_recommandation, date_recommandation, id_pieceObj);
			recommandation.update(connection);
			forwardRequest(request, response, "TraitementRecommandationServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_recommandation = request.getParameter("id_recommandation");
			Recommandation.delete(id_recommandation,connection);
			forwardRequest(request, response, "TraitementRecommandationServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_recommandation = request.getParameter("id_recommandation");
		Recommandation recommandation = Recommandation.getById(id_recommandation,connection);
		if (recommandation != null) {
			request.setAttribute("action", "handleUpdate");
			List<Piece> pieceList = Piece.getAll(connection);
			request.setAttribute("pieceList", pieceList);
			request.setAttribute("recommandation", recommandation);
			forwardRequest(request, response, "pages/recommandation/formulaireRecommandation.jsp");
		} else {
			out.println("Recommandation non trouvé.");
		}
	}
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
