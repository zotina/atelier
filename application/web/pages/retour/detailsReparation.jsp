<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ include file="../../templates/header.jsp" %>
<%
    List<models.Reparation> reparations = (List<models.Reparation>) request.getAttribute("reparations");
%>

<main id="main" class="main">
    <div class="container">
        <h2>Détails des Réparations</h2>
        <div class="row">
            <% if (reparations != null && !reparations.isEmpty()) { %>
                <% for (models.Reparation reparation : reparations) { %>
                    <div class="col-md-6 mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Informations de réparation</h5>
                                <p><strong>ID Réparation :</strong> 
                                    <%= reparation.getId_reparation() != null ? reparation.getId_reparation() : "Non spécifié" %>
                                </p>
                                <p><strong>Date de début :</strong> 
                                    <%= reparation.getDate_debut() != null ? reparation.getDate_debut() : "Non spécifié" %>
                                </p>
                                <p><strong>Prix :</strong> 
                                    <%= reparation.getPrix() + " Ar" %>
                                </p>
                                <p><strong>Catégorie :</strong> 
                                    <%= reparation.getCategorie() != null ? reparation.getCategorie().getNom() : "Non spécifié" %>
                                </p>
                            </div>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <p>Aucune réparation trouvée pour cet appareil.</p>
            <% } %>
        </div>
        <div class="mt-4">
            <a href="TraitementRetourServlet" class="btn btn-secondary">Retour à la liste des retours</a>
        </div>
    </div>
</main>

<%@ include file="../../templates/footer.jsp" %>