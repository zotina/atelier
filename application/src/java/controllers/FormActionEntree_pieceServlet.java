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

@WebServlet("/FormActionEntree_pieceServlet")
public class FormActionEntree_pieceServlet extends HttpServlet {

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
			String id_entree_piece = request.getParameter("id_entree_piece");
			Date date_entree = Date.valueOf(request.getParameter("date_entree"));
			int quantite = Integer.parseInt(request.getParameter("quantite"));
			double prix_unitaire = Double.parseDouble(request.getParameter("prix_unitaire"));
			String id_fornisseur = request.getParameter("id_fornisseur");
			Fornisseur id_fornisseurObj = Fornisseur.getById(id_fornisseur,connection);
			String id_piece = request.getParameter("id_piece");
			Piece id_pieceObj = Piece.getById(id_piece,connection);
			Entree_piece entree_piece = new Entree_piece(id_entree_piece, date_entree, quantite, prix_unitaire, id_fornisseurObj, id_pieceObj);
			entree_piece.insert(connection);
			forwardRequest(request, response, "TraitementEntree_pieceServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_entree_piece = request.getParameter("id_entree_piece");
			Date date_entree = Date.valueOf(request.getParameter("date_entree"));
			int quantite = Integer.parseInt(request.getParameter("quantite"));
			double prix_unitaire = Double.parseDouble(request.getParameter("prix_unitaire"));
			String id_fornisseur = request.getParameter("id_fornisseur");
			Fornisseur id_fornisseurObj = Fornisseur.getById(id_fornisseur,connection);
			String id_piece = request.getParameter("id_piece");
			Piece id_pieceObj = Piece.getById(id_piece,connection);
			Entree_piece entree_piece = new Entree_piece(id_entree_piece, date_entree, quantite, prix_unitaire, id_fornisseurObj, id_pieceObj);
			entree_piece.update(connection);
			forwardRequest(request, response, "TraitementEntree_pieceServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_entree_piece = request.getParameter("id_entree_piece");
			Entree_piece.delete(id_entree_piece,connection);
			forwardRequest(request, response, "TraitementEntree_pieceServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_entree_piece = request.getParameter("id_entree_piece");
		Entree_piece entree_piece = Entree_piece.getById(id_entree_piece,connection);
		if (entree_piece != null) {
			request.setAttribute("action", "handleUpdate");
			List<Fornisseur> fornisseurList = Fornisseur.getAll(connection);
			request.setAttribute("fornisseurList", fornisseurList);
			List<Piece> pieceList = Piece.getAll(connection);
			request.setAttribute("pieceList", pieceList);
			request.setAttribute("entree_piece", entree_piece);
			forwardRequest(request, response, "pages/formulaireEntree_piece.jsp");
		} else {
			out.println("Entree_piece non trouvé.");
		}
	}
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
