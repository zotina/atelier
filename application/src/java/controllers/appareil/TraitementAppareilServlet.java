package controllers.appareil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.Connection;
import java.sql.Date;

import models.Appareil;
import models.Client;
import models.Model;
import models.Typa;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/TraitementAppareilServlet")
public class TraitementAppareilServlet extends HttpServlet {

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
		List<Typa> typaList = Typa.getAll(connection);
		request.setAttribute("typaList", typaList);
		List<Client> clientList = Client.getAll(connection);
		request.setAttribute("clientList", clientList);
		List<Model> modelList = Model.getAll(connection);
		request.setAttribute("modelList", modelList);
		List<Appareil> appareilList = Appareil.getAll(connection);
		request.setAttribute("appareilList", appareilList);
		forwardRequest(request, response, "pages/appareil/appareil.jsp");
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
		List<Typa> typaList = Typa.getAll(connection);
		request.setAttribute("typaList", typaList);
		List<Client> clientList = Client.getAll(connection);
		request.setAttribute("clientList", clientList);
		List<Model> modelList = Model.getAll(connection);
		request.setAttribute("modelList", modelList);
		forwardRequest(request, response, "pages/appareil/formulaireAppareil.jsp");
	}

}
