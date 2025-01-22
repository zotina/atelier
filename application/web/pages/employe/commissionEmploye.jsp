<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
    List<Commission> commissionList = null;
    List<Employe> employeList = null;

    try {
        commissionList = (List<Commission>) request.getAttribute("commissions");
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<%@ include file="../../templates/header.jsp" %>

<main id="main" class="main">
    <section class="section profile">
        <div class="container">
            <!-- Formulaire de recherche -->
            <form method="get" action="TraitementEmployeServlet">
                <input type="hidden" name="action" value="commission">
                
                <label for="dateDebutMin">Date début :</label>
                <input type="date" id="dateMin" name="dateMin" >
                
                <label for="dateDebutMax">Date fin :</label>
                <input type="date" id="dateMax" name="dateMax">
                
                <button type="submit">Rechercher</button>
            </form>

            <!-- Table des commissions -->
            <div class="row">
                <div class="table-container">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Employe</th>
                                <th>ID Reparation</th>
                                <th>Appareil</th>
                                <th>Date Reparation</th>
                                <th>Prix Reparation</th>
                                <th>Commission </th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (commissionList != null) { %>
                                <% 
                                double totalCommission = 0;
                                for (Commission commission : commissionList) { 
                                    totalCommission += commission.getCommission();
                                %>
                                    <tr>
                                        <td><a href="FormActionEmployeServlet?action=detailsEmployer&id=<%= commission.getEmploye().getId_employe() %>"><%= commission.getEmploye().getId_employe() %></a> </td>
                                        <td><%= commission.getReparation().getId_reparation() %></td>
                                        <td><%= commission.getReparation().getAppareil().getLibelle() %></td>
                                        <td><%= commission.getReparation().getDate_debut() %></td>
                                        <td><%= String.format("%,.2f", commission.getReparation().getPrix()) %> €</td>
                                        <td><%= String.format("%,.2f", commission.getCommission()) %> €</td>
                                    </tr>
                                <% } %>
                                <!-- Ligne pour le total -->
                                <tr class="table-info">
                                    <td colspan="4"><strong>Total des commissions</strong></td>
                                    <td><strong><%= String.format("%,.2f", totalCommission) %> €</strong></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</main>

<%@ include file="../../templates/footer.jsp" %>