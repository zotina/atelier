<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Reparation> reparationList = null;
	List<Etat_reparation> etat_reparationList = null;
	List<Appareil_employe> appareil_employeList = null;
	Reparation reparation = null;
	
	try {
		reparationList = (List<Reparation>) request.getAttribute("reparationList");
		etat_reparationList = (List<Etat_reparation>) request.getAttribute("etat_reparationList");
		appareil_employeList = (List<Appareil_employe>) request.getAttribute("appareil_employeList");
		reparation = (Reparation) request.getAttribute("reparation");
	} catch (Exception e) {
		// Log the error or handle it appropriately
		e.printStackTrace();
	}
%>
<%@ include file="../templates/header.jsp" %>
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
						<label for="id_appareil_employe">Appareil_employe</label>
						<select class="form-control" id="id_appareil_employe" name="id_appareil_employe">
							<% for (Appareil_employe appareil_employe : appareil_employeList) { %>
							<% boolean isSelected = (appareil_employe.getId_appareil_employe() == (reparation != null ? reparation.getAppareil_employe().getId_appareil_employe() : 0)); %>
							<option value="<%= appareil_employe.getId_appareil_employe() %>" <%= isSelected ? "selected" : "" %>><%= appareil_employe.getLibelle() %></option>
							<% } %>
						</select>
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
					<input type="hidden" name="action" value="<%= (request.getAttribute("action") == null) ? "insert" : ("handleUpdate".equals(request.getAttribute("action")) ? "update" : "insert") %>">
					<button type="submit" class="btn btn-primary"><%= (request.getAttribute("action") == null) ? "Insert" : ("handleUpdate".equals(request.getAttribute("action")) ? "Update" : "Insert") %></button>
				</form>
				<!-- Contenu de la page ici -->
			</div>
		</div>
	</section>
</main>
<%@ include file="../templates/footer.jsp" %>
