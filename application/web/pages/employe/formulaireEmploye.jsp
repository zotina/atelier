<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Employe> employeList = null;
	List<Role> roleList = null;
	Employe employe = null;
	
	try {
		employeList = (List<Employe>) request.getAttribute("employeList");
		roleList = (List<Role>) request.getAttribute("roleList");
		employe = (Employe) request.getAttribute("employe");
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
				<form action="FormActionEmployeServlet" method="post">
					<input type="hidden" class="form-control" id="id_employe" name="id_employe" value="<%= (employe != null && employe.getId_employe() != null) ? employe.getId_employe() : "" %>">
					<div class="form-group">
						<label for="nom">Nom</label>
						<input type="text" class="form-control" id="nom" name="nom" value="<%= (employe != null && employe.getNom() != null) ? employe.getNom() : "" %>">
					</div>
					<div class="form-group">
						<label for="telephone">Telephone</label>
						<input type="text" class="form-control" id="telephone" name="telephone" value="<%= (employe != null && employe.getTelephone() != null) ? employe.getTelephone() : "" %>">
					</div>
					<div class="form-group">
						<label for="email">Email</label>
						<input type="text" class="form-control" id="email" name="email" value="<%= (employe != null && employe.getEmail() != null) ? employe.getEmail() : "" %>">
					</div>
					<div class="form-group">
						<label for="salaire_personnalise">Salaire_personnalise</label>
						<input type="text" class="form-control" id="salaire_personnalise" name="salaire_personnalise" value="<%= (employe != null && employe.getSalaire_personnalise() != null) ? employe.getSalaire_personnalise() : "" %>">
					</div>
					<div class="form-group">
						<label for="date_embauche">Date_embauche</label>
						<input type="date" class="form-control" id="date_embauche" name="date_embauche" value="<%= (employe != null && employe.getDate_embauche() != null) ? employe.getDate_embauche() : "" %>">
					</div>
					<div class="form-group">
						<label for="addresse">Addresse</label>
						<input type="text" class="form-control" id="addresse" name="addresse" value="<%= (employe != null && employe.getAddresse() != null) ? employe.getAddresse() : "" %>">
					</div>
					<div class="form-group">
						<label for="id_role">Role</label>
						<select class="form-control" id="id_role" name="id_role">
							<% for (Role role : roleList) { %>
							<% boolean isSelected = (role.getId_role() == (employe != null ? employe.getRole().getId_role() : 0)); %>
							<option value="<%= role.getId_role() %>" <%= isSelected ? "selected" : "" %>><%= role.getLibelle() %></option>
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
