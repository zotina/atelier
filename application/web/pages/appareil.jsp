<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Appareil> appareilList = null;
	List<Client> clientList = null;
	List<Model> modelList = null;
	Appareil appareil = null;
	
	try {
		appareilList = (List<Appareil>) request.getAttribute("appareilList");
		clientList = (List<Client>) request.getAttribute("clientList");
		modelList = (List<Model>) request.getAttribute("modelList");
		appareil = (Appareil) request.getAttribute("appareil");
	} catch (Exception e) {
		// Log the error or handle it appropriately
		e.printStackTrace();
	}
%>
<%@ include file="../templates/header.jsp" %>
<main id="main" class="main">
	<section class="section profile">
		<div class="container">
			<div class="row">
				<div class="table-container">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Id ppareil</th>
							<th>Numero_serie</th>
							<th>Desc_probleme</th>
							<th>Date_deposition</th>
							<th>Details Client</th>
							<th>Details Employer</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (appareilList != null) { %>
							<% for (Appareil appareilvalue : appareilList) { %>	
								<tr>
								<td><%= appareilvalue.getId_appareil() %></td>
								<td><%= appareilvalue.getNumero_serie() %></td>
								<td><%= appareilvalue.getDesc_probleme() %></td>
								<td><%= appareilvalue.getDate_deposition() %></td>
								<td><a href="FormActionAppareilServlet?action=detailsClient&id=<%= appareilvalue.getId_appareil() %>">voir details</a></td>
								<td><a href="FormActionAppareilServlet?action=detailsEmployer&id=<%= appareilvalue.getId_appareil() %>">voir details</a></td>
								<td><a href="FormActionAppareilServlet?action=handleUpdate&id_appareil=<%=appareilvalue.getId_appareil() %>"><i class="far fa-edit"></i></a>
								<a href="FormActionAppareilServlet?action=delete&id_appareil=<%=appareilvalue.getId_appareil() %>"><i class="far fa-trash-alt"></i></a></td>
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
<%@ include file="../templates/footer.jsp" %>
