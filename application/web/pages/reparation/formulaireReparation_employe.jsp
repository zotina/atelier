<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Reparation_employe> reparation_employeList = null;
	List<Employe> employeList = null;
	List<Reparation> reparationList = null;
	Reparation_employe reparation_employe = null;
	
	try {
		reparation_employeList = (List<Reparation_employe>) request.getAttribute("reparation_employeList");
		employeList = (List<Employe>) request.getAttribute("employeList");
		reparationList = (List<Reparation>) request.getAttribute("reparationList");
		reparation_employe = (Reparation_employe) request.getAttribute("reparation_employe");
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
				<form action="FormActionReparation_employeServlet" method="post">
						<input type="hidden" class="form-control" id="id_reparation_employe" name="id_reparation_employe" value="<%= (reparation_employe != null && reparation_employe.getId_reparation_employe() != null) ? reparation_employe.getId_reparation_employe() : "" %>">
					<div class="form-group">
						<label for="id_reparation">Reparation</label>
						<select class="form-control" id="id_reparation" name="id_reparation">
							<% for (Reparation reparation : reparationList) { %>
							<% boolean isSelected = (reparation.getId_reparation() == (reparation_employe != null ? reparation_employe.getReparation().getId_reparation() : 0)); %>
							<option value="<%= reparation.getId_reparation() %>" <%= isSelected ? "selected" : "" %>><%= reparation.getId_reparation()  %></option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="id_employe">Employe</label>
						<select class="form-control" id="id_employe" name="id_employe">
							<% for (Employe employe : employeList) { %>
							<% boolean isSelected = (employe.getId_employe() == (reparation_employe != null ? reparation_employe.getEmploye().getId_employe() : 0)); %>
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
<%@ include file="../../templates/footer.jsp" %>
