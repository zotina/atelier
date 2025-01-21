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
		<div class="container d-flex align-items-center justify-content-center" style="width: 80vh;">
			<div class="row w-100">
				<form action="FormActionReparationServlet" method="post">
					<input type="hidden" class="form-control" id="id_reparation" name="id_reparation" value="<%= (reparation != null && reparation.getId_reparation() != null) ? reparation.getId_reparation() : "" %>">
					<div class="form-group">
						<label for="date_debut">Date_debut</label>
						<input type="datetime-local" class="form-control" id="date_debut" name="date_debut" value="<%= (reparation != null && reparation.getDate_debut() != null) ? reparation.getDate_debut() : "" %>">
					</div>
					<div class="form-group">
						<label for="prix">Prix</label>
						<input type="number" class="form-control" id="prix" name="prix" value="<%= (reparation != null) ? reparation.getPrix() : 0.0 %>">
					</div>
					<div class="form-group">
						<label for="id_etat">Etat_reparation</label>
						<select class="form-control" id="id_etat" name="id_etat">
							<% for (Etat_reparation etat_reparation : etat_reparationList) { %>
							<% boolean isSelected = (etat_reparation.getId_etat() == (reparation != null ? reparation.getEtat_reparation().getId_etat() : 0)); %>
							<option value="<%= etat_reparation.getId_etat() %>" <%= isSelected ? "selected" : "" %>><%= etat_reparation.getLibelle() %></option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="id_categorie">Categorie</label>
						<select class="form-control" id="id_categorie" name="id_categorie">
							<% for (Categorie categorie : categorieList) { %>
							<% boolean isSelected = (categorie.getId_categorie() == (reparation != null ? reparation.getCategorie().getId_categorie() : 0)); %>
							<option value="<%= categorie.getId_categorie() %>" <%= isSelected ? "selected" : "" %>><%= categorie.getNom() %></option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="id_appareil">Appareil</label>
						<select class="form-control" id="id_appareil" name="id_appareil">
							<% for (Appareil appareil : appareilList) { %>
							<% boolean isSelected = (appareil.getId_appareil() == (reparation != null ? reparation.getAppareil().getId_appareil() : 0)); %>
							<option value="<%= appareil.getId_appareil() %>" <%= isSelected ? "selected" : "" %>><%= appareil.getNumero_serie() %></option>
							<% } %>
						</select>
					</div>
					<input type="hidden" name="action" value="<%= (request.getAttribute("action") == null) ? "insert" : ("handleUpdate".equals(request.getAttribute("action")) ? "update" : "insert") %>">
					<button type="submit" class="btn btn-primary"><%= (request.getAttribute("action") == null) ? "Insert" : ("handleUpdate".equals(request.getAttribute("action")) ? "Update" : "Insert") %></button>
				</form>
				<!-- Contenu de la page ici -->
			</div>
		</div>
	</section>
</main>
<%@ include file="../../templates/footer.jsp" %>
	