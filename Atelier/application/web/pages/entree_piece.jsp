<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Entree_piece> entree_pieceList = null;
	List<Piece> pieceList = null;
	List<Fornisseur> fornisseurList = null;
	Entree_piece entree_piece = null;
	
	try {
		entree_pieceList = (List<Entree_piece>) request.getAttribute("entree_pieceList");
		pieceList = (List<Piece>) request.getAttribute("pieceList");
		fornisseurList = (List<Fornisseur>) request.getAttribute("fornisseurList");
		entree_piece = (Entree_piece) request.getAttribute("entree_piece");
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
							<th>Id_entree_piece</th>
							<th>Date_entree</th>
							<th>Quantite</th>
							<th>Prix_unitaire</th>
							<th>Id_fornisseur</th>
							<th>Id_piece</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (entree_pieceList != null) { %>
							<% for (Entree_piece entree_piecevalue : entree_pieceList) { %>
								<tr>
								<td><%= entree_piecevalue.getId_entree_piece() %></td>
								<td><%= entree_piecevalue.getDate_entree() %></td>
								<td><%= entree_piecevalue.getQuantite() %></td>
								<td><%= entree_piecevalue.getPrix_unitaire() %></td>
								<td><%= entree_piecevalue.getFornisseur().getNom() %></td>
								<td><%= entree_piecevalue.getPiece().getNumero_serie() %></td>
								<td><a href="FormActionEntree_pieceServlet?action=handleUpdate&id_entree_piece=<%=entree_piecevalue.getId_entree_piece() %>"><i class="far fa-edit"></i></a></td>
								<td><a href="FormActionEntree_pieceServlet?action=delete&id_entree_piece=<%=entree_piecevalue.getId_entree_piece() %>"><i class="far fa-trash-alt"></i></a></td>
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
