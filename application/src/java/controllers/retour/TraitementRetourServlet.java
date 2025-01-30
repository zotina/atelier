package controllers.retour;

import java.io.*;
import java.sql.Connection;
import java.util.List;

import database.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import models.Appareil;
import models.Retour;
import models.Typa;
import models.Categorie;

@WebServlet("/TraitementRetourServlet")
public class TraitementRetourServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = new Connexion().connect_to_postgres();
            String action = request.getParameter("action");

            if (action != null) {
                switch (action) {
                    case "form":
                        try {
                            loadformulaire(request, response, connection);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new ServletException(e);
                        }
                        break;
                    case "searchByTypeAndCategorie":
                        try {
                            searchByTypeAndCategorie(request, response, connection);
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

    private void Default(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        List<Appareil> appareilList = Appareil.getAll(connection);
        request.setAttribute("appareilList", appareilList);

        // Récupérer les types et catégories des appareils avec Typa et Categorie
        List<Typa> typeList = Typa.getAll(connection);  // Récupérer les types d'appareils
        List<Categorie> categorieList = Categorie.getAll(connection);  // Récupérer les catégories d'appareils
        request.setAttribute("typeList", typeList);
        request.setAttribute("categorieList", categorieList);

        List<Retour> retourList = Retour.getAll(connection);
        request.setAttribute("retourList", retourList);

        forwardRequest(request, response, "pages/retour/retour.jsp");
    }

    private void loadformulaire(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        List<Appareil> appareilList = Appareil.getAll(connection);
        request.setAttribute("appareilList", appareilList);

        // Récupérer les types et catégories des appareils avec Typa et Categorie
        List<Typa> typeList = Typa.getAll(connection);  // Récupérer les types d'appareils
        List<Categorie> categorieList = Categorie.getAll(connection);  // Récupérer les catégories d'appareils
        request.setAttribute("typeList", typeList);
        request.setAttribute("categorieList", categorieList);

        forwardRequest(request, response, "pages/retour/formulaireRetour.jsp");
    }

    private void searchByTypeAndCategorie(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        String type = request.getParameter("type");
        String categorie = request.getParameter("categorie");
        String date = request.getParameter("date");

        if (type == null || type.isEmpty() || categorie == null || categorie.isEmpty()) {
            request.setAttribute("error", "Veuillez fournir un type et une catégorie.");
            Default(request, response, connection);
            return;
        }

        List<Retour> retourList = Retour.getByTypeAndCategory(connection, type, categorie,date);
        request.setAttribute("retourList", retourList);
        request.setAttribute("type", type);
        request.setAttribute("categorie", categorie);

        // Récupérer les types et catégories des appareils avec Typa et Categorie
        List<Typa> typeList = Typa.getAll(connection);
        List<Categorie> categorieList = Categorie.getAll(connection);
        request.setAttribute("typeList", typeList);
        request.setAttribute("categorieList", categorieList);

        forwardRequest(request, response, "pages/retour/retour.jsp");
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
}
