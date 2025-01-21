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
		<div class="container">
			<div class="row">
				<div class="table-container">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Id_reparation_reel</th>
							<th>reparation</th>
							<th>piece</th>
							<th>Quantite</th>
							<th>Date_sortie</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (reparation_reelList != null) { %>
							<% for (Reparation_reel reparation_reelvalue : reparation_reelList) { %>
								<tr>
								<td><%= reparation_reelvalue.getId_reparation_reel() %></td>
								<td><%= reparation_reelvalue.getReparation().getDiagnostic() %></td>
								<td><%= reparation_reelvalue.getPiece().getNumero_serie() %></td>
								<td><%= reparation_reelvalue.getQuantite() %></td>
								<td><%= reparation_reelvalue.getDate_sortie() %></td>
								<td><a href="FormActionReparation_reelServlet?action=handleUpdate&id_reparation_reel=<%=reparation_reelvalue.getId_reparation_reel() %>"><i class="far fa-edit"></i></a></td>
								<td><a href="FormActionReparation_reelServlet?action=delete&id_reparation_reel=<%=reparation_reelvalue.getId_reparation_reel() %>"><i class="far fa-trash-alt"></i></a></td>
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
