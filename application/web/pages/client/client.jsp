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
<%@ include file="../../templates/header.jsp" %>
<main id="main" class="main">
	<section class="section profile">
		<div class="container">
			<div class="row">
				<div class="table-container">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Id_client</th>
							<th>Email</th>
							<th>Nom</th>
							<th>Telephone</th>
							<th>Addresse</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (clientList != null) { %>
							<% for (Client clientvalue : clientList) { %>
								<tr>
								<td><%= clientvalue.getId_client() %></td>
								<td><%= clientvalue.getEmail() %></td>
								<td><%= clientvalue.getNom() %></td>
								<td><%= clientvalue.getTelephone() %></td>
								<td><%= clientvalue.getAddresse() %></td>
								<td><a href="FormActionClientServlet?action=handleUpdate&id_client=<%=clientvalue.getId_client() %>"><i class="far fa-edit"></i></a><a href="FormActionClientServlet?action=delete&id_client=<%=clientvalue.getId_client() %>"><i class="far fa-trash-alt"></i></a></td>
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
