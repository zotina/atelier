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
<%@ include file="../../templates/header.jsp" %>
<main id="main" class="main">
	<section class="section profile">
		<div class="container d-flex align-items-center justify-content-center" style="width: 80vh;">
			<div class="row w-100">
				<form action="FormActionEntree_pieceServlet" method="post">
					<input type="hidden" class="form-control" id="id_entree_piece" name="id_entree_piece" value="<%= (entree_piece != null && entree_piece.getId_entree_piece() != null) ? entree_piece.getId_entree_piece() : "" %>">
					<div class="form-group">
						<label for="id_piece">Piece</label>
						<select class="form-control" id="id_piece" name="id_piece">
							<% for (Piece piece : pieceList) { %>
							<% boolean isSelected = (piece.getId_piece() == (entree_piece != null ? entree_piece.getPiece().getId_piece() : 0)); %>
							<option value="<%= piece.getId_piece() %>" <%= isSelected ? "selected" : "" %>><%= piece.getNumero_serie() %></option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="date_entree">Date_entree</label>
						<input type="date" class="form-control" id="date_entree" name="date_entree" value="<%= (entree_piece != null && entree_piece.getDate_entree() != null) ? entree_piece.getDate_entree() : "" %>">
					</div>
					<div class="form-group">
						<label for="quantite">Quantite</label>
						<input type="number" class="form-control" id="quantite" name="quantite" value="<%= (entree_piece != null) ? entree_piece.getQuantite() : 0 %>">
					</div>
					<div class="form-group">
						<label for="prix_unitaire">Prix_unitaire</label>
						<input type="number" class="form-control" id="prix_unitaire" name="prix_unitaire" value="<%= (entree_piece != null) ? entree_piece.getPrix_unitaire() : 0.0 %>">
					</div>
					<div class="form-group">
						<label for="id_fornisseur">Fornisseur</label>
						<select class="form-control" id="id_fornisseur" name="id_fornisseur">
							<% for (Fornisseur fornisseur : fornisseurList) { %>
							<% boolean isSelected = (fornisseur.getId_fornisseur() == (entree_piece != null ? entree_piece.getFornisseur().getId_fornisseur() : 0)); %>
							<option value="<%= fornisseur.getId_fornisseur() %>" <%= isSelected ? "selected" : "" %>><%= fornisseur.getNom() %></option>
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
