package controllers.reparation_reel;
import java.io.*;
import java.util.List;
import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.Date;

import models.Piece;
import models.Reparation;
import models.Reparation_reel;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/FormActionReparation_reelServlet")
public class FormActionReparation_reelServlet extends HttpServlet {

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
			String id_reparation_reel = request.getParameter("id_reparation_reel");
			String id_reparation = request.getParameter("id_reparation");
			Reparation id_reparationObj = Reparation.getById(id_reparation,connection);
			String id_piece = request.getParameter("id_piece");
			Piece id_pieceObj = Piece.getById(id_piece,connection);
			int quantite = Integer.parseInt(request.getParameter("quantite"));
			Date date_sortie = Date.valueOf(request.getParameter("date_sortie"));
			Reparation_reel reparation_reel = new Reparation_reel(id_reparation_reel, id_reparationObj, id_pieceObj, quantite, date_sortie);
			reparation_reel.insert(connection);
			forwardRequest(request, response, "TraitementReparation_reelServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_reparation_reel = request.getParameter("id_reparation_reel");
			String id_reparation = request.getParameter("id_reparation");
			Reparation id_reparationObj = Reparation.getById(id_reparation,connection);
			String id_piece = request.getParameter("id_piece");
			Piece id_pieceObj = Piece.getById(id_piece,connection);
			int quantite = Integer.parseInt(request.getParameter("quantite"));
			Date date_sortie = Date.valueOf(request.getParameter("date_sortie"));
			Reparation_reel reparation_reel = new Reparation_reel(id_reparation_reel, id_reparationObj, id_pieceObj, quantite, date_sortie);
			reparation_reel.update(connection);
			forwardRequest(request, response, "TraitementReparation_reelServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_reparation_reel = request.getParameter("id_reparation_reel");
			Reparation_reel.delete(id_reparation_reel,connection);
			forwardRequest(request, response, "TraitementReparation_reelServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_reparation_reel = request.getParameter("id_reparation_reel");
		Reparation_reel reparation_reel = Reparation_reel.getById(id_reparation_reel,connection);
		if (reparation_reel != null) {
			request.setAttribute("action", "handleUpdate");
			List<Reparation> reparationList = Reparation.getAll(connection);
			request.setAttribute("reparationList", reparationList);
			List<Piece> pieceList = Piece.getAll(connection);
			request.setAttribute("pieceList", pieceList);
			request.setAttribute("reparation_reel", reparation_reel);
			forwardRequest(request, response, "pages/reparation/formulaireReparation_reel.jsp");
		} else {
			out.println("Reparation_reel non trouvé.");
		}
	}
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
