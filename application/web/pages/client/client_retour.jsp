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
            <div class="search-container">
				<form action="TraitementClientServlet" method="get" class="form-inline">
					<input type="hidden" name="action" value="search">
					<div class="form-group">
						<label for="month">Date :</label>
						<input type="date" name="date" id="date" class="form-control">
					</div>
					<button type="submit" class="btn btn-primary">Rechercher</button>
				</form>
			</div>
			<div class="row">
				<div class="table-container">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Id_client</th>
							<th>Nom</th>
							<th>Details client</th>
                            <th>Details Retours</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (clientList != null) { %>
							<% for (Client clientvalue : clientList) { %>
								<tr>
								<td><%= clientvalue.getId_client() %></td>
								<td><%= clientvalue.getNom() %></td>
								<td><a href="FormActionClientServlet?action=detailsClient&id_client=<%=clientvalue.getId_client()%>">voir client</a></td>
								<td><a href="FormActionRetourServlet?action=detailsRetoursClient&id_client=<%=clientvalue.getId_client()%>">voir les retours</a></td>
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
