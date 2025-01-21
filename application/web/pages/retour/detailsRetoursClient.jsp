<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ include file="../../templates/header.jsp" %>
<%
    List<models.Retour> retours = (List<models.Retour>) request.getAttribute("retours");
%>

<main id="main" class="main">
    <div class="container">
        <h2>Détails des Retours Client</h2>
        <div class="row">
            <% if (retours != null && !retours.isEmpty()) { %>
                <% for (models.Retour retour : retours) { %>
                    <div class="col-md-6 mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Informations du retour</h5>
                                <p><strong>ID Retour :</strong>
                                    <%= retour.getId_retour() != null ? retour.getId_retour() : "Non spécifié" %>
                                </p>
                                <p><strong>Date de retour :</strong>
                                    <%= retour.getDate_retour() != null ? retour.getDate_retour() : "Non spécifié" %>
                                </p>
                                <p><strong>Prix total main d'œuvre :</strong>
                                    <%= retour.getPrix_total_main_doeuvre() + " Ar" %>
                                </p>
                                <p><strong>Prix total pièces :</strong>
                                    <%= retour.getPrix_total_piece() + " Ar" %>
                                </p>
                                <% if (retour.getAppareil() != null) { %>
                                    <div class="mt-3">
                                        <h6>Informations de l'appareil</h6>
                                        <p><strong>Appareil :</strong>
                                            <%= retour.getAppareil().getLibelle() != null ? retour.getAppareil().getLibelle() : "Non spécifié" %>
                                        </p>    
                                        <p><strong>Numéro de série :</strong>
                                            <%= retour.getAppareil().getNumero_serie() != null ? retour.getAppareil().getNumero_serie() : "Non spécifié" %>
                                        </p>
                                        <p><strong>Description du problème :</strong>
                                            <%= retour.getAppareil().getDesc_probleme() != null ? retour.getAppareil().getDesc_probleme() : "Non spécifié" %>
                                        </p>
                                    </div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <p>Aucun retour trouvé pour ce client.</p>
            <% } %>
        </div>
        <div class="mt-4">
            <a href="TraitementClientServlet?action=client_retour" class="btn btn-secondary">Retour à la liste des client retour</a>
        </div>
    </div>
</main>

<%@ include file="../../templates/footer.jsp" %>