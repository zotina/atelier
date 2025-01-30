<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
    List<Retour> retourList = null;
    List<Appareil> appareilList = null;
    List<Typa> typeList = null; // Liste des types d'appareils
    List<Categorie> categorieList = null; // Liste des catégories
    Retour retour = null;

    try {
        retourList = (List<Retour>) request.getAttribute("retourList");
        appareilList = (List<Appareil>) request.getAttribute("appareilList");
        typeList = (List<Typa>) request.getAttribute("typeList"); // Récupérer les types d'appareils
        categorieList = (List<Categorie>) request.getAttribute("categorieList"); // Récupérer les catégories d'appareils
        retour = (Retour) request.getAttribute("retour");
    } catch (Exception e) {
        // Log the error or handle it appropriately
        e.printStackTrace();
    }
%>

<%@ include file="../../templates/header.jsp" %>

<main id="main" class="main">
    <section class="section profile">
        <div class="container">
            <!-- Formulaire de recherche -->
            <form method="get" action="TraitementRetourServlet">
                <input type="hidden" name="action" value="searchByTypeAndCategorie">
                
                <label for="type">Type d'appareil :</label>
                <select id="type" name="type">
                    <option value="">Sélectionnez un type</option>
                    <% if (typeList != null) { %>
                        <% for (Typa type : typeList) { %>
                            <option value="<%= type.getId_typa() %>" <%= (request.getParameter("type") != null && request.getParameter("type").equals(String.valueOf(type.getId_typa()))) ? "selected" : "" %>><%= type.getNom() %></option>
                        <% } %>
                    <% } %>
                </select>
                
                <label for="categorie">Catégorie :</label>
                <select id="categorie" name="categorie">
                    <option value="">Sélectionnez une catégorie</option>
                    <% if (categorieList != null) { %>
                        <% for (Categorie categorie : categorieList) { %>
                            <option value="<%= categorie.getId_categorie() %>" <%= (request.getParameter("categorie") != null && request.getParameter("categorie").equals(String.valueOf(categorie.getId_categorie()))) ? "selected" : "" %>><%= categorie.getNom() %></option>
                        <% } %>
                    <% } %>
                </select>

                <div class="form-group">
                    <label for="date">Date</label>
                    <input type="date" class="form-control" id="date" name="date" >
                </div>
                
                <button type="submit">Rechercher</button>
            </form>

            <!-- Table des retours -->
            <div class="row">
                <div class="table-container">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Id_retour</th>
                                <th>Appareil</th>
                                <th>Type</th>
                                <th>Réparations faites</th>
                                <th>Prix Total Main d'Oeuvre</th>
                                <th>Prix Total Pièces</th>
                                <th>Date Retour</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (retourList != null) { %>
                                <% for (Retour retourvalue : retourList) { %>
                                    <tr>
                                        <td><%= retourvalue.getId_retour() %></td>
                                        <td><%= retourvalue.getAppareil().getLibelle() %></td>
                                        <td><%= retourvalue.getAppareil().getTypa().getNom() %></td>
                                        <td><a href="FormActionRetourServlet?action=detailsReparations&id=<%= retourvalue.getAppareil().getId_appareil() %>">Les réparations</a></td>
                                        <td><%= retourvalue.getPrix_total_main_doeuvre() %></td>
                                        <td><%= retourvalue.getPrix_total_piece() %></td>
                                        <td><%= retourvalue.getDate_retour() %></td>
                                        <td>
                                            <a href="FormActionRetourServlet?action=handleUpdate&id_retour=<%= retourvalue.getId_retour() %>"><i class="far fa-edit"></i></a>
                                            <a href="FormActionRetourServlet?action=delete&id_retour=<%= retourvalue.getId_retour() %>"><i class="far fa-trash-alt"></i></a>
                                        </td>
                                    </tr>
                                <% } %>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</main>

<%@ include file="../../templates/footer.jsp" %>
