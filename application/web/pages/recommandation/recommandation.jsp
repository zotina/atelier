<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Recommandation> recommandationList = null;
	List<Piece> pieceList = null;
	Recommandation recommandation = null;
	
	try {
		recommandationList = (List<Recommandation>) request.getAttribute("recommandationList");
		pieceList = (List<Piece>) request.getAttribute("pieceList");
		recommandation = (Recommandation) request.getAttribute("recommandation");
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
				<form action="TraitementRecommandationServlet" method="get" class="form-inline">
					<input type="hidden" name="action" value="search">
					<div class="form-group">
						<label for="month">Mois :</label>
						<input type="month" name="month" id="month" class="form-control">
					</div>
					<button type="submit" class="btn btn-primary">Rechercher</button>
				</form>
			</div>
			<div class="row">
				<div class="table-container">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Id_recommandation</th>
							<th>Date_recommandation</th>
							<th>piece</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (recommandationList != null) { %>
							<% for (Recommandation recommandationvalue : recommandationList) { %>
								<tr>
								<td><%= recommandationvalue.getId_recommandation() %></td>
								<td><%= recommandationvalue.getDate_recommandation() %></td>
								<td><%= recommandationvalue.getPiece().getLibelle()%>
								</td>
								<td><a href="FormActionRecommandationServlet?action=handleUpdate&id_recommandation=<%=recommandationvalue.getId_recommandation() %>"><i class="far fa-edit"></i></a></td>
								<td><a href="FormActionRecommandationServlet?action=delete&id_recommandation=<%=recommandationvalue.getId_recommandation() %>"><i class="far fa-trash-alt"></i></a></td>
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
