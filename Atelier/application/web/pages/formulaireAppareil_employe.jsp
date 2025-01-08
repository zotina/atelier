<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Appareil_employe> appareil_employeList = null;
	List<Appareil> appareilList = null;
	List<Employe> employeList = null;
	Appareil_employe appareil_employe = null;
	
	try {
		appareil_employeList = (List<Appareil_employe>) request.getAttribute("appareil_employeList");
		appareilList = (List<Appareil>) request.getAttribute("appareilList");
		employeList = (List<Employe>) request.getAttribute("employeList");
		appareil_employe = (Appareil_employe) request.getAttribute("appareil_employe");
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
				<form action="FormActionAppareil_employeServlet" method="post">
					<div class="form-group">
						<label for="id_appareil">Appareil</label>
						<select class="form-control" id="id_appareil" name="id_appareil">
							<% for (Appareil appareil : appareilList) { %>
							<% boolean isSelected = (appareil.getId_appareil() == (appareil_employe != null ? appareil_employe.getAppareil().getId_appareil() : 0)); %>
							<option value="<%= appareil.getId_appareil() %>" <%= isSelected ? "selected" : "" %>><%= appareil.getNumero_serie() %></option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="id_employe">Employe</label>
						<select class="form-control" id="id_employe" name="id_employe">
							<% for (Employe employe : employeList) { %>
							<% boolean isSelected = (employe.getId_employe() == (appareil_employe != null ? appareil_employe.getEmploye().getId_employe() : 0)); %>
							<option value="<%= employe.getId_employe() %>" <%= isSelected ? "selected" : "" %>><%= employe.getNom() %></option>
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
