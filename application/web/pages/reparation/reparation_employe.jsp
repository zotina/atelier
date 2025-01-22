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
		<div class="container">
			<div class="row">
				<div class="table-container">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Id_reparation_employe</th>
							<th>reparation</th>
							<th>employe</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (reparation_employeList != null) { %>
							<% for (Reparation_employe reparation_employevalue : reparation_employeList) { %>
								<tr>
								<td><%= reparation_employevalue.getId_reparation_employe() %></td>
								<td><%= reparation_employevalue.getReparation().getId_reparation() %></td>
								<td><%= reparation_employevalue.getEmploye().getNom() %></td>
								<td><a href="FormActionReparation_employeServlet?action=handleUpdate&id_reparation_employe=<%=reparation_employevalue.getId_reparation_employe() %>"><i class="far fa-edit"></i></a></td>
								<td><a href="FormActionReparation_employeServlet?action=delete&id_reparation_employe=<%=reparation_employevalue.getId_reparation_employe() %>"><i class="far fa-trash-alt"></i></a></td>
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
