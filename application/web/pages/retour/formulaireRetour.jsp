<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Retour> retourList = null;
	List<Appareil> appareilList = null;
	Retour retour = null;
	
	try {
		retourList = (List<Retour>) request.getAttribute("retourList");
		appareilList = (List<Appareil>) request.getAttribute("appareilList");
		retour = (Retour) request.getAttribute("retour");
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
				<form action="FormActionRetourServlet" method="post">
					<input type="hidden" class="form-control" id="id_retour" name="id_retour" value="<%= (retour != null && retour.getId_retour() != null) ? retour.getId_retour() : "" %>">
					<input type="hidden" class="form-control" id="prix_total_main_doeuvre" name="prix_total_main_doeuvre" value="0.0">
					<input type="hidden" class="form-control" id="prix_total_piece" name="prix_total_piece" value="0.0">
					<div class="form-group">
						<label for="date_retour">Date_retour</label>
						<input type="date" class="form-control" id="date_retour" name="date_retour" value="<%= (retour != null && retour.getDate_retour() != null) ? retour.getDate_retour() : "" %>">
					</div>
					<div class="form-group">
						<label for="id_appareil">Appareil</label>
						<select class="form-control" id="id_appareil" name="id_appareil">
							<% for (Appareil appareil : appareilList) { %>
							<% boolean isSelected = (appareil.getId_appareil() == (retour != null ? retour.getAppareil().getId_appareil() : 0)); %>
							<option value="<%= appareil.getId_appareil() %>" <%= isSelected ? "selected" : "" %>><%= appareil.getOwnerLib() %></option>
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
