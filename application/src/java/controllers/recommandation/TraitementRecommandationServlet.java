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

@WebServlet("/TraitementRecommandationServlet")
public class TraitementRecommandationServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = new Connexion().connect_to_postgres();
			String action = request.getParameter("action");

			if (action != null) {
				switch (action) {
					case "search":
                        try {
                            search(request, response, connection);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new ServletException(e);
                        }
                        break;
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
		List<Recommandation> recommandationList = Recommandation.getAll(connection);
		request.setAttribute("recommandationList", recommandationList);
		forwardRequest(request, response, "pages/recommandation/recommandation.jsp");
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
		forwardRequest(request, response, "pages/recommandation/formulaireRecommandation.jsp");
	}

	private void search(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        String month = request.getParameter("month");
		System.out.println("mois valeur = "+month);
        List<Piece> pieceList = Piece.getAll(connection);
		request.setAttribute("pieceList", pieceList);

		List<Recommandation> recommandationList = Recommandation.filterByMonth(month,connection);
		request.setAttribute("recommandationList", recommandationList);
		forwardRequest(request, response, "pages/recommandation/recommandation.jsp");
    }

}
