<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Histo_piece> histo_pieceList = null;
	List<Piece> pieceList = null;
	Histo_piece histo_piece = null;
	
	try {
		histo_pieceList = (List<Histo_piece>) request.getAttribute("histo_pieceList");
		pieceList = (List<Piece>) request.getAttribute("pieceList");
		histo_piece = (Histo_piece) request.getAttribute("histo_piece");
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
							<th>Id_histo</th>
							<th>Id_piece</th>
							<th>Prix_unitaire</th>
							<th>Change_type</th>
							<th>Changed_at</th>
							<th>Changed_by</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (histo_pieceList != null) { %>
							<% for (Histo_piece histo_piecevalue : histo_pieceList) { %>
								<tr>
								<td><%= histo_piecevalue.getId_histo() %></td>
								<td><%= histo_piecevalue.getPiece().getDescription() %></td>
								<td><%= histo_piecevalue.getPrix_unitaire() %></td>
								<td><%= histo_piecevalue.getChange_type() %></td>
								<td><%= histo_piecevalue.getChanged_at() %></td>
								<td><%= histo_piecevalue.getChanged_by() %></td>
								<td><a href="FormActionHisto_pieceServlet?action=delete&id_histo=<%=histo_piecevalue.getId_histo() %>"><i class="far fa-trash-alt"></i></a></td>
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
