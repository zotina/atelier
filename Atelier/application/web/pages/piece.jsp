<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Piece> pieceList = null;
	List<Categorie> categorieList = null;
	List<Marque> marqueList = null;
	Piece piece = null;
	
	try {
		pieceList = (List<Piece>) request.getAttribute("pieceList");
		categorieList = (List<Categorie>) request.getAttribute("categorieList");
		marqueList = (List<Marque>) request.getAttribute("marqueList");
		piece = (Piece) request.getAttribute("piece");
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
							<th>Numero_serie</th>
							<th>Prix_unitaire</th>
							<th>Reference</th>
							<th>Description</th>
							<th>Id_categorie</th>
							<th>Id_marque</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (pieceList != null) { %>
							<% for (Piece piecevalue : pieceList) { %>
								<tr>
								<td><%= piecevalue.getNumero_serie() %></td>
								<td><%= piecevalue.getPrix_unitaire() %></td>
								<td><%= piecevalue.getReference() %></td>
								<td><%= piecevalue.getDescription() %></td>
								<td><%= piecevalue.getCategorie().getNom() %></td>
								<td><%= piecevalue.getMarque().getNom() %></td>
								<td><a href="FormActionPieceServlet?action=handleUpdate&id_piece=<%=piecevalue.getId_piece() %>"><i class="far fa-edit"></i></a></td>
								<td><a href="FormActionPieceServlet?action=delete&id_piece=<%=piecevalue.getId_piece() %>"><i class="far fa-trash-alt"></i></a></td>
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
