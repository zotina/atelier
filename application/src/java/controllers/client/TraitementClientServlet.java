package controllers.client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import models.Client;
import models.Recommandation;

import java.sql.Connection;
import java.sql.Date;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/TraitementClientServlet")
public class TraitementClientServlet extends HttpServlet {

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
					case "client_retour":
						try {
							loadClientRetour(request, response,connection);
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
		List<Client> clientList = Client.getAll(connection);
		request.setAttribute("clientList", clientList);
		forwardRequest(request, response, "pages/client/client.jsp");
	}

	private void loadClientRetour(HttpServletRequest request, HttpServletResponse response,Connection connection) throws Exception {
		List<Client> clientList = Client.getAllFilterByDate(connection,null);
		request.setAttribute("clientList", clientList);
		forwardRequest(request, response, "pages/client/client_retour.jsp");
	}

	private void search(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
		String date = null;
		if(request.getParameter("date")!=null && request.getParameter("date")!=""){
			date = request.getParameter("date");
		}
        List<Client> clientList = Client.getAllFilterByDate(connection,date);
		request.setAttribute("clientList", clientList);
		forwardRequest(request, response, "pages/client/client_retour.jsp");
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
		forwardRequest(request, response, "pages/client/formulaireClient.jsp");
	}

}
