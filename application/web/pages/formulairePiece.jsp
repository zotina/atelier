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
		<div class="container d-flex align-items-center justify-content-center" style="width: 80vh;">
			<div class="row w-100">
				<form action="FormActionPieceServlet" method="post">
					<input type="hidden" class="form-control" id="id_piece" name="id_piece" value="<%= (piece != null && piece.getId_piece() != null) ? piece.getId_piece() : "" %>">
					<div class="form-group">
						<label for="numero_serie">Numero_serie</label>
						<input type="text" class="form-control" id="numero_serie" name="numero_serie" value="<%= (piece != null && piece.getNumero_serie() != null) ? piece.getNumero_serie() : "" %>">
					</div>
					<div class="form-group">
						<label for="prix_unitaire">Prix_unitaire</label>
						<input type="number" class="form-control" id="prix_unitaire" name="prix_unitaire" value="<%= (piece != null) ? piece.getPrix_unitaire() : 0.0 %>">
					</div>
					<div class="form-group">
						<label for="reference">Reference</label>
						<input type="text" class="form-control" id="reference" name="reference" value="<%= (piece != null && piece.getReference() != null) ? piece.getReference() : "" %>">
					</div>
					<div class="form-group">
						<label for="description">Description</label>
						<input type="text" class="form-control" id="description" name="description" value="<%= (piece != null && piece.getDescription() != null) ? piece.getDescription() : "" %>">
					</div>
					<div class="form-group">
						<label for="id_categorie">Categorie</label>
						<select class="form-control" id="id_categorie" name="id_categorie">
							<% for (Categorie categorie : categorieList) { %>
							<% boolean isSelected = (categorie.getId_categorie() == (piece != null ? piece.getCategorie().getId_categorie() : 0)); %>
							<option value="<%= categorie.getId_categorie() %>" <%= isSelected ? "selected" : "" %>><%= categorie.getNom() %></option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="id_marque">Marque</label>
						<select class="form-control" id="id_marque" name="id_marque">
							<% for (Marque marque : marqueList) { %>
							<% boolean isSelected = (marque.getId_marque() == (piece != null ? piece.getMarque().getId_marque() : 0)); %>
							<option value="<%= marque.getId_marque() %>" <%= isSelected ? "selected" : "" %>><%= marque.getNom() %></option>
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
<%@ include file="../templates/footer.jsp" %>
