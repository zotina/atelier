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

@WebServlet("/FormActionPieceServlet")
public class FormActionPieceServlet extends HttpServlet {

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
			String id_piece = request.getParameter("id_piece");
			String numero_serie = request.getParameter("numero_serie");
			double prix_unitaire = Double.parseDouble(request.getParameter("prix_unitaire"));
			String reference = request.getParameter("reference");
			String description = request.getParameter("description");
			String id_categorie = request.getParameter("id_categorie");
			Categorie id_categorieObj = Categorie.getById(id_categorie,connection);
			String id_marque = request.getParameter("id_marque");
			Marque id_marqueObj = Marque.getById(id_marque,connection);
			Piece piece = new Piece(id_piece, numero_serie, prix_unitaire, reference, description, id_categorieObj, id_marqueObj);
			piece.insert(connection);
			forwardRequest(request, response, "TraitementPieceServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_piece = request.getParameter("id_piece");
			String numero_serie = request.getParameter("numero_serie");
			double prix_unitaire = Double.parseDouble(request.getParameter("prix_unitaire"));
			String reference = request.getParameter("reference");
			String description = request.getParameter("description");
			String id_categorie = request.getParameter("id_categorie");
			Categorie id_categorieObj = Categorie.getById(id_categorie,connection);
			String id_marque = request.getParameter("id_marque");
			Marque id_marqueObj = Marque.getById(id_marque,connection);
			Piece piece = new Piece(id_piece, numero_serie, prix_unitaire, reference, description, id_categorieObj, id_marqueObj);
			piece.update(connection);
			forwardRequest(request, response, "TraitementPieceServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		try {
			String id_piece = request.getParameter("id_piece");
			Piece.delete(id_piece,connection);
			forwardRequest(request, response, "TraitementPieceServlet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		PrintWriter out = response.getWriter();
		String id_piece = request.getParameter("id_piece");
		Piece piece = Piece.getById(id_piece,connection);
		if (piece != null) {
			request.setAttribute("action", "handleUpdate");
			List<Categorie> categorieList = Categorie.getAll(connection);
			request.setAttribute("categorieList", categorieList);
			List<Marque> marqueList = Marque.getAll(connection);
			request.setAttribute("marqueList", marqueList);
			request.setAttribute("piece", piece);
			forwardRequest(request, response, "pages/formulairePiece.jsp");
		} else {
			out.println("Piece non trouvé.");
		}
	}
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
}
