<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Reparation_reel> reparation_reelList = null;
	List<Piece> pieceList = null;
	List<Reparation> reparationList = null;
	Reparation_reel reparation_reel = null;
	
	try {
		reparation_reelList = (List<Reparation_reel>) request.getAttribute("reparation_reelList");
		pieceList = (List<Piece>) request.getAttribute("pieceList");
		reparationList = (List<Reparation>) request.getAttribute("reparationList");
		reparation_reel = (Reparation_reel) request.getAttribute("reparation_reel");
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
				<form action="FormActionReparation_reelServlet" method="post">
					<input type="hidden" class="form-control" id="id_reparation_reel" name="id_reparation_reel" value="<%= (reparation_reel != null && reparation_reel.getId_reparation_reel() != null) ? reparation_reel.getId_reparation_reel() : "" %>">
					<div class="form-group">
						<label for="id_reparation">Reparation</label>
						<select class="form-control" id="id_reparation" name="id_reparation">
							<% for (Reparation reparation : reparationList) { %>
							<% boolean isSelected = (reparation.getId_reparation() == (reparation_reel != null ? reparation_reel.getReparation().getId_reparation() : 0)); %>
							<option value="<%= reparation.getId_reparation() %>" <%= isSelected ? "selected" : "" %>><%= reparation.getId_reparation() %></option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="id_piece">Piece</label>
						<select class="form-control" id="id_piece" name="id_piece">
							<% for (Piece piece : pieceList) { %>
							<% boolean isSelected = (piece.getId_piece() == (reparation_reel != null ? reparation_reel.getPiece().getId_piece() : 0)); %>
							<option value="<%= piece.getId_piece() %>" <%= isSelected ? "selected" : "" %>><%= piece.getNumero_serie() %></option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label for="quantite">Quantite</label>
						<input type="number" class="form-control" id="quantite" name="quantite" value="<%= (reparation_reel != null) ? reparation_reel.getQuantite() : 0 %>">
					</div>
					<div class="form-group">
						<label for="date_sortie">Date_sortie</label>
						<input type="date" class="form-control" id="date_sortie" name="date_sortie" value="<%= (reparation_reel != null && reparation_reel.getDate_sortie() != null) ? reparation_reel.getDate_sortie() : "" %>">
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
