<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Reparation> reparationList = null;
	List<Appareil> appareilList = null;
	List<Categorie> categorieList = null;
	List<Etat_reparation> etat_reparationList = null;
	Reparation reparation = null;
	
	try {
		reparationList = (List<Reparation>) request.getAttribute("reparationList");
		appareilList = (List<Appareil>) request.getAttribute("appareilList");
		categorieList = (List<Categorie>) request.getAttribute("categorieList");
		etat_reparationList = (List<Etat_reparation>) request.getAttribute("etat_reparationList");
		reparation = (Reparation) request.getAttribute("reparation");
	} catch (Exception e) {
		// Log the error or handle it appropriately
		e.printStackTrace();
	}
%>
<%@ include file="../../templates/header.jsp" %>
<main id="main" class="main">
	<section class="section profile">
		<div class="container">
			<div class="search-container">
				<form action="TraitementReparationServlet" method="get" class="form-inline">
					<input type="hidden" name="action" value="search">
					<div class="form-group">
						<label for="diagnostic">Diagnostic :</label>
						<select name="diagnostic" id="diagnostic" class="form-control">
							<option value="">Tous</option>
							<option value="true">Oui</option>
							<option value="false">Non</option>
						</select>
					</div>
					<div class="form-group">
						<label for="date_debut_min">Date début min :</label>
						<input type="date" name="date_debut_min" id="date_debut_min" class="form-control">
					</div>
					<div class="form-group">
						<label for="date_debut_max">Date début max :</label>
						<input type="date" name="date_debut_max" id="date_debut_max" class="form-control">
					</div>
					<div class="form-group">
						<label for="id_etat">État :</label>
						<select name="id_etat" id="id_etat" class="form-control">
							<option value="">Tous</option>
							<% if (etat_reparationList != null) { %>
								<% for (Etat_reparation etat : etat_reparationList) { %>
									<option value="<%= etat.getId_etat() %>"><%= etat.getLibelle() %></option>
								<% } %>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="id_categorie">Catégorie :</label>
						<select name="id_categorie" id="id_categorie" class="form-control">
							<option value="">Tous</option>
							<% if (categorieList != null) { %>
								<% for (Categorie categorie : categorieList) { %>
									<option value="<%= categorie.getId_categorie() %>"><%= categorie.getNom() %></option>
								<% } %>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="id_appareil">Appareil :</label>
						<select name="id_appareil" id="id_appareil" class="form-control">
							<option value="">Tous</option>
							<% if (appareilList != null) { %>
								<% for (Appareil appareil : appareilList) { %>
									<option value="<%= appareil.getId_appareil() %>"><%= appareil.getLibelle() %></option>
								<% } %>
							<% } %>
						</select>
					</div>
					<button type="submit" class="btn btn-primary">Rechercher</button>
				</form>
			</div>
			
			<div class="row">
				<div class="table-container">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Id_reparation</th>
							<th>Date_debut</th>
							<th>Prix</th>
							<th>etat</th>
							<th>categorie</th>
							<th>appareil</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (reparationList != null) { %>
							<% for (Reparation reparationvalue : reparationList) { %>
								<tr>
								<td><%= reparationvalue.getId_reparation() %></td>
								<td><%= reparationvalue.getDate_debut() %></td>
								<td><%= reparationvalue.getPrix() %></td>
								<td><%= reparationvalue.getEtat_reparation().getLibelle() %></td>
								<td><%= reparationvalue.getCategorie().getNom() %></td>
								<td><%= reparationvalue.getAppareil().getLibelle() %></td>
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
<%@ include file="../../templates/footer.jsp" %>
