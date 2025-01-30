package controllers.piece;
import java.io.*;
import java.util.List;
import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.Date;

import models.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/FormActionHisto_pieceServlet")
public class FormActionHisto_pieceServlet extends HttpServlet {

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
			String id_histo = request.getParameter("id_histo");
			String id_piece = request.getParameter("id_piece");
			Piece id_pieceObj = Piece.getById(id_piece,connection);
			double prix_unitaire = Double.parseDouble(request.getParameter("prix_unitaire"));
			String change_type = request.getParameter("change_type");
			Date changed_at = Date.valueOf(request.getParameter("changed_at"));
			String changed_by = request.getParameter("changed_by");
			Histo_piece histo_piece = new Histo_piece(id_histo, id_pieceObj, prix_unitaire, change_type, changed_at, changed_by);
			histo_piece.insert(connection);
			forwardRequest(request, response, "TraitementHisto_pieceServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_histo = request.getParameter("id_histo");
			String id_piece = request.getParameter("id_piece");
			Piece id_pieceObj = Piece.getById(id_piece,connection);
			double prix_unitaire = Double.parseDouble(request.getParameter("prix_unitaire"));
			String change_type = request.getParameter("change_type");
			Date changed_at = Date.valueOf(request.getParameter("changed_at"));
			String changed_by = request.getParameter("changed_by");
			Histo_piece histo_piece = new Histo_piece(id_histo, id_pieceObj, prix_unitaire, change_type, changed_at, changed_by);
			histo_piece.update(connection);
			forwardRequest(request, response, "TraitementHisto_pieceServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_histo = request.getParameter("id_histo");
			Histo_piece.delete(id_histo,connection);
			forwardRequest(request, response, "TraitementHisto_pieceServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_histo = request.getParameter("id_histo");
		Histo_piece histo_piece = Histo_piece.getById(id_histo,connection);
		if (histo_piece != null) {
			request.setAttribute("action", "handleUpdate");
			List<Piece> pieceList = Piece.getAll(connection);
			request.setAttribute("pieceList", pieceList);
			request.setAttribute("histo_piece", histo_piece);
			forwardRequest(request, response, "pages/piece/formulaireHisto_piece.jsp");
		} else {
			out.println("Histo_piece non trouvé.");
		}
	}
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
