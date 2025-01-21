package controllers.reparation_employe;

import java.io.*;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.Connection;
import java.sql.Date;

import models.Employe;
import models.Reparation;
import models.Reparation_employe;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/TraitementReparation_employeServlet")
public class TraitementReparation_employeServlet extends HttpServlet {

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
		List<Employe> employeList = Employe.getAll(connection);
		request.setAttribute("employeList", employeList);
		List<Reparation> reparationList = Reparation.getAll(connection);
		request.setAttribute("reparationList", reparationList);
		List<Reparation_employe> reparation_employeList = Reparation_employe.getAll(connection);
		request.setAttribute("reparation_employeList", reparation_employeList);
		forwardRequest(request, response, "pages/reparation/reparation_employe.jsp");
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
		List<Employe> employeList = Employe.getAll(connection);
		request.setAttribute("employeList", employeList);
		List<Reparation> reparationList = Reparation.getAll(connection);
		request.setAttribute("reparationList", reparationList);
		forwardRequest(request, response, "pages/reparation/formulaireReparation_employe.jsp");
	}

}
