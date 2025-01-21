<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Appareil> appareilList = null;
	List<Typa> typaList = null;
	List<Client> clientList = null;
	List<Model> modelList = null;
	Appareil appareil = null;
	
	try {
		appareilList = (List<Appareil>) request.getAttribute("appareilList");
		typaList = (List<Typa>) request.getAttribute("typaList");
		clientList = (List<Client>) request.getAttribute("clientList");
		modelList = (List<Model>) request.getAttribute("modelList");
		appareil = (Appareil) request.getAttribute("appareil");
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
				<form action="FormActionAppareilServlet" method="post">
					<input type="hidden" class="form-control" id="id_appareil" name="id_appareil" value="<%= (appareil != null && appareil.getId_appareil() != null) ? appareil.getId_appareil() : "" %>">
					<div class="form-group">
						<label for="numero_serie">Numero_serie</label>
						<input type="text" class="form-control" id="numero_serie" name="numero_serie" value="<%= (appareil != null && appareil.getNumero_serie() != null) ? appareil.getNumero_serie() : "" %>">
					</div>
					<div class="form-group">
						<label for="est_repare">Est_repare</label>
						<input type="text" class="form-control" id="est_repare" name="est_repare" value="<%= (appareil != null && appareil.getEst_repare() != null) ? appareil.getEst_repare() : "" %>">
					</div>
					<div class="form-group">
						<label for="desc_probleme">Desc_probleme</label>
						<input type="text" class="form-control" id="desc_probleme" name="desc_probleme" value="<%= (appareil != null && appareil.getDesc_probleme() != null) ? appareil.getDesc_probleme() : "" %>">
					</div>
					<div class="form-group">
						<label for="date_deposition">Date_deposition</label>
						<input type="text" class="form-control" id="date_deposition" name="date_deposition" value="<%= (appareil != null && appareil.getDate_deposition() != null) ? appareil.getDate_deposition() : "" %>">
					</div>
					<div class="form-group">
						<label for="id_typa">Typa</label>
						<select class="form-control" id="id_typa" name="id_typa">
							<% for (Typa typa : typaList) { %>
							<% boolean isSelected = (typa.getId_typa() == (appareil != null ? appareil.getTypa().getId_typa() : 0)); %>
							<option value="<%= typa.getId_typa() %>" <%= isSelected ? "selected" : "" %>><%= typa.getNom() %></option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="id_client">Client</label>
						<select class="form-control" id="id_client" name="id_client">
							<% for (Client client : clientList) { %>
							<% boolean isSelected = (client.getId_client() == (appareil != null ? appareil.getClient().getId_client() : 0)); %>
							<option value="<%= client.getId_client() %>" <%= isSelected ? "selected" : "" %>><%= client.getEmail() %></option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="id_model">Model</label>
						<select class="form-control" id="id_model" name="id_model">
							<% for (Model model : modelList) { %>
							<% boolean isSelected = (model.getId_model() == (appareil != null ? appareil.getModel().getId_model() : 0)); %>
							<option value="<%= model.getId_model() %>" <%= isSelected ? "selected" : "" %>><%= model.getLibelle() %></option>
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
