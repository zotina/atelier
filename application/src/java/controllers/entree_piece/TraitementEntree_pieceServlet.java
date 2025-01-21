package controllers.entree_piece;
import java.io.*;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.Connection;
import java.sql.Date;

import models.Entree_piece;
import models.Fornisseur;
import models.Piece;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/TraitementEntree_pieceServlet")
public class TraitementEntree_pieceServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = new Connexion().connect_to_postgres();
			String action = request.getParameter("action");

			if (action != null) {
				switch (action) {
					case "form":
						try {
							loadformulaire(request, response,connection);
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

	private void Default(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		List<Piece> pieceList = Piece.getAll(connection);
		request.setAttribute("pieceList", pieceList);
		List<Fornisseur> fornisseurList = Fornisseur.getAll(connection);
		request.setAttribute("fornisseurList", fornisseurList);
		List<Entree_piece> entree_pieceList = Entree_piece.getAll(connection);
		request.setAttribute("entree_pieceList", entree_pieceList);
		forwardRequest(request, response, "pages/piece/entree_piece.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
	private void loadformulaire(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		List<Piece> pieceList = Piece.getAll(connection);
		request.setAttribute("pieceList", pieceList);
		List<Fornisseur> fornisseurList = Fornisseur.getAll(connection);
		request.setAttribute("fornisseurList", fornisseurList);
		forwardRequest(request, response, "pages/piece/formulaireEntree_piece.jsp");
	}

}
