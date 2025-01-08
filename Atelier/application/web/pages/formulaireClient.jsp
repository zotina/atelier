<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Client> clientList = null;
	Client client = null;
	
	try {
		clientList = (List<Client>) request.getAttribute("clientList");
		client = (Client) request.getAttribute("client");
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
				<form action="FormActionClientServlet" method="post">
					<div class="form-group">
						<input type="hidden" class="form-control" id="id_client" name="id_client" value="<%= (client != null && client.getId_client() != null) ? client.getId_client() : "" %>">
						<label for="email">Email</label>
						<input type="text" class="form-control" id="email" name="email" value="<%= (client != null && client.getEmail() != null) ? client.getEmail() : "" %>">
					</div>
					<div class="form-group">
						<label for="nom">Nom</label>
						<input type="text" class="form-control" id="nom" name="nom" value="<%= (client != null && client.getNom() != null) ? client.getNom() : "" %>">
					</div>
					<div class="form-group">
						<label for="telephone">Telephone</label>
						<input type="number" class="form-control" id="telephone" name="telephone" value="<%= (client != null) ? client.getTelephone() : 0 %>">
					</div>
					
					<div class="form-group">
						<label for="addresse">Addresse</label>
						<input type="text" class="form-control" id="addresse" name="addresse" value="<%= (client != null && client.getAddresse() != null) ? client.getAddresse() : "" %>">
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
