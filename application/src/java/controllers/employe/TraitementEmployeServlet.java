package controllers.employe;

import java.io.*;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.Connection;
import java.sql.Date;

import models.Commission;
import models.Employe;
import models.Role;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/TraitementEmployeServlet")
public class TraitementEmployeServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
						}
						break;
					case "commission":
					try {
						getCommission(request, response, connection);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
					case "form":
						try {
							loadformulaire(request, response, connection);
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

	private void Default(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		List<Role> roleList = Role.getAll(connection);
		request.setAttribute("roleList", roleList);
		List<Employe> employeList = Employe.getAll(connection);
		request.setAttribute("employeList", employeList);
		forwardRequest(request, response, "pages/employe/employe.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	private void loadformulaire(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		List<Role> roleList = Role.getAll(connection);
		request.setAttribute("roleList", roleList);
		forwardRequest(request, response, "pages/employe/formulaireEmploye.jsp");
	}

	private void search(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		try {
			String nom = request.getParameter("nom");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			String salaireMin = request.getParameter("salaire_min");
			String salaireMax = request.getParameter("salaire_max");
			String dateDebutStr = request.getParameter("date_debut");
			String dateFinStr = request.getParameter("date_fin");
			String role = request.getParameter("role");

			// Parse the date fields
			Date dateDebut = null;
			Date dateFin = null;
			if (dateDebutStr != null && !dateDebutStr.isEmpty()) {
				dateDebut = Date.valueOf(dateDebutStr);
			}
			if (dateFinStr != null && !dateFinStr.isEmpty()) {
				dateFin = Date.valueOf(dateFinStr);
			}

			// Call the search method with all parameters
			List<Employe> employeList = Employe.search(connection, nom, email, telephone, salaireMin,
					salaireMax, dateDebutStr, dateFinStr, role);
			request.setAttribute("employeList", employeList);
			List<Role> roleList = Role.getAll(connection);
			request.setAttribute("roleList", roleList);

			// Redirect to the results page
			forwardRequest(request, response, "pages/employe/employe.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void getCommission(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws Exception {
		String dateMin = request.getParameter("dateMin");
		String dateMax = request.getParameter("dateMax");
		List<Commission> commissions = Commission.getCommissionFilterByPeriode(connection, dateMin, dateMax);
		request.setAttribute("commissions", commissions);
		forwardRequest(request, response, "pages/employe/commissionEmploye.jsp");
	}

}
