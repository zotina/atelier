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
		<div class="container d-flex align-items-center justify-content-center" style="width: 80vh;">
			<div class="row w-100">
				<form action="FormActionRecommandationServlet" method="post">
						<input type="hidden" class="form-control" id="id_recommandation" name="id_recommandation" value="<%= (recommandation != null && recommandation.getId_recommandation() != null) ? recommandation.getId_recommandation() : "" %>">
					<div class="form-group">
						<label for="date_recommandation">Date_recommandation</label>
						<input type="date" class="form-control" id="date_recommandation" name="date_recommandation" value="<%= (recommandation != null && recommandation.getDate_recommandation() != null) ? recommandation.getDate_recommandation() : "" %>">
					</div>
					<div class="form-group">
						<label for="id_piece">Piece</label>
						<select class="form-control" id="id_piece" name="id_piece">
							<% for (Piece piece : pieceList) { %>
							<% boolean isSelected = (piece.getId_piece() == (recommandation != null ? recommandation.getPiece().getId_piece() : 0)); %>
							<option value="<%= piece.getId_piece() %>" <%= isSelected ? "selected" : "" %>><%= piece.getLibelle() %></option>
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
