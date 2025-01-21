<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ include file="../../templates/header.jsp" %>
<%
    // Récupération de la liste des employés passée via la requête
    List<models.Employe> employes = (List<models.Employe>) request.getAttribute("employers");
%>
<main id="main" class="main">
    <div class="container">
        <h2>Détails des Employés</h2>
        <div class="row">
            <% if (employes != null && !employes.isEmpty()) { %>
                <% for (models.Employe employe : employes) { %>
                    <div class="col-md-6 mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Informations générales</h5>
                                <p><strong>ID Employé :</strong> <%= employe.getId_employe() != null ? employe.getId_employe() : "Non spécifié" %></p>
                                <p><strong>Nom :</strong> <%= employe.getNom() != null ? employe.getNom() : "Non spécifié" %></p>
                                <h5 class="card-title mt-4">Informations personnelles</h5>
                                <p><strong>Téléphone :</strong> <%= employe.getTelephone() != null ? employe.getTelephone() : "Non spécifié" %></p>
                                <p><strong>Email :</strong> <%= employe.getEmail() != null ? employe.getEmail() : "Non spécifié" %></p>
                                <p><strong>Adresse :</strong> <%= employe.getAddresse() != null ? employe.getAddresse() : "Non spécifiée" %></p>
                            </div>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <p>Aucun employé attribué pour cet appareil.</p>
            <% } %>
        </div>
        <div class="mt-4">
            <a href="TraitementAppareilServlet" class="btn btn-secondary">Retour à la liste des appareils</a>
        </div>
        <div class="mt-4">
            <a href="TraitementEmployeServlet" class="btn btn-secondary">Retour à la liste des employer</a>
        </div>
    </div>
</main>
<%@ include file="../../templates/footer.jsp" %>
