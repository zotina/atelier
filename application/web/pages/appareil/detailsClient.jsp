<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../templates/header.jsp" %>
<%
    // Retrieve the client object passed through the request
    models.Client client = (models.Client) request.getAttribute("client");

    // Extract client details using getter methods
    String idClient = client != null ? client.getId_client() : null;
    String nom = client != null ? client.getNom() : null;
    String telephone = client != null ? String.valueOf(client.getTelephone()) : null;
    String email = client != null ? client.getEmail() : null;
    String addresse = client != null ? client.getAddresse() : null;
%>
<main id="main" class="main">
    <div class="container">
        <h2>Détails du Client</h2>
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Informations générales</h5>
                <p><strong>ID Client :</strong> <%= idClient != null ? idClient : "Non spécifié" %></p>
                <p><strong>Nom :</strong> <%= nom != null ? nom : "Non spécifié" %></p>
                <h5 class="card-title mt-4">Informations personnelles</h5>
                <p><strong>Téléphone :</strong> <%= telephone != null ? telephone : "Non spécifié" %></p>
                <p><strong>Email :</strong> <%= email != null ? email : "Non spécifié" %></p>
                <p><strong>Adresse :</strong> <%= addresse != null ? addresse : "Non spécifiée" %></p>
            </div>
        </div>
        <div class="mt-4">
            <a href="TraitementAppareilServlet" class="btn btn-secondary">Retour à la liste des Appareil</a>
        </div>
    </div>
</main>
<%@ include file="../../templates/footer.jsp" %>
