package controllers.typa;

import java.io.*;
import java.sql.Connection;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import models.Typa;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/FormActionTypaServlet")
public class FormActionTypaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = new Connexion().connect_to_postgres();

            String action = request.getParameter("action");

            if (action != null) {
                switch (action) {
                    case "insert":
                        try {
                            insert(request, response, connection);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "update":
                        try {
                            update(request, response, connection);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "delete":
                        try {
                            delete(request, response, connection);
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void insert(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws Exception {
        try {
            String id_typa = request.getParameter("id_typa");
            String libelle = request.getParameter("libelle");
            Typa typa = new Typa(id_typa, libelle);
            typa.insert(connection);
            forwardRequest(request, response, "TraitementTypaServlet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws Exception {
        try {
            String id_typa = request.getParameter("id_typa");
            String libelle = request.getParameter("libelle");
            Typa typa = new Typa(id_typa, libelle);
            typa.update(connection);
            forwardRequest(request, response, "TraitementTypaServlet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws Exception {
        try {
            String id_typa = request.getParameter("id_typa");
            Typa.delete(id_typa, connection);
            forwardRequest(request, response, "TraitementTypaServlet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Connection connection)
            throws Exception {
        PrintWriter out = response.getWriter();
        String id_typa = request.getParameter("id_typa");
        Typa typa = Typa.getById(id_typa, connection);
        if (typa != null) {
            request.setAttribute("action", "handleUpdate");
            request.setAttribute("typa", typa);
            forwardRequest(request, response, "pages/typa/formulaireTypa.jsp");
        } else {
            out.println("Typa non trouvé.");
        }
    }

    private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
}
