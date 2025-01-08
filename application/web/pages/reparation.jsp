<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Reparation> reparationList = null;
	List<Etat_reparation> etat_reparationList = null;
	List<Appareil_employe> appareil_employeList = null;
	List<Categorie> categorieList = null;
	Reparation reparation = null;
	
	try {
		reparationList = (List<Reparation>) request.getAttribute("reparationList");
		etat_reparationList = (List<Etat_reparation>) request.getAttribute("etat_reparationList");
		appareil_employeList = (List<Appareil_employe>) request.getAttribute("appareil_employeList");
		categorieList = (List<Categorie>) request.getAttribute("categorieList");
		reparation = (Reparation) request.getAttribute("reparation");
	} catch (Exception e) {
		// Log the error or handle it appropriately
		e.printStackTrace();
	}
%>
<%@ include file="../templates/header.jsp" %>
<main id="main" class="main">
	<form action="TraitementReparationServlet" method="get">
		<input type="hidden" name="action" id="nom" value="search" >
		<div class="col-md-4">
            <label for="categorie">categorie:</label>
            <select name="categorie" id="categorie" class="form-control">
                <option value="">categorie</option>
                <% if (categorieList != null) { %>
					<% for (Categorie categorievalue : categorieList) { %>
						<option value="<%= categorievalue.getId_categorie() %>"><%= categorievalue.getNom() %></option>
					<% } %>
				<% } %>
            </select>
        </div>
		<div class="row mt-4">
			<div class="col-md-12 text-center">
				<button type="submit" class="btn btn-primary">Rechercher</button>
			</div>
		</div>
	</form>

	<section class="section profile">
		<div class="container">
			<div class="row">
				<div class="table-container">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>reparation</th>
							<th>Diagnostic</th>
							<th>Date_debut</th>
							<th>Prix</th>
							<th>Appareil</th>
							<th>Employe</th>
							<th>Etat</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (reparationList != null) { %>
							<% for (Reparation reparationvalue : reparationList) { %>
								<tr>
								<td><%= reparationvalue.getId_reparation() %></td>
								<td><%= reparationvalue.getDiagnostic() %></td>
								<td><%= reparationvalue.getDate_debut() %></td>
								<td><%= reparationvalue.getPrix() %></td>
								<td><%= reparationvalue.getAppareil_employe().getAppareil().getLibelle() %></td>
								<td><%= reparationvalue.getAppareil_employe().getEmploye().getNom() %></td>
								<td><%= reparationvalue.getEtat_reparation().getLibelle() %></td>
								<td><a href="FormActionReparationServlet?action=handleUpdate&id_reparation=<%=reparationvalue.getId_reparation() %>"><i class="far fa-edit"></i></a><a href="FormActionReparationServlet?action=delete&id_reparation=<%=reparationvalue.getId_reparation() %>"><i class="far fa-trash-alt"></i></a></td>
								</tr>
							<% } %>
						<% } %>
						</tbody>
					</table>
				<!-- Contenu de la page ici -->
			</div>
		</div>
	</div>
	</section>
</main>
<%@ include file="../templates/footer.jsp" %>
