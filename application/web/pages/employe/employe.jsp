<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
	List<Employe> employeList = null;
	List<Role> roleList = null;
	Employe employe = null;
	
	try {
		employeList = (List<Employe>) request.getAttribute("employeList");
		roleList = (List<Role>) request.getAttribute("roleList");
		employe = (Employe) request.getAttribute("employe");
	} catch (Exception e) {
		// Log the error or handle it appropriately
		e.printStackTrace();
	}
%>
<%@ include file="../../templates/header.jsp" %>
<main id="main" class="main">

	<h2>Rechercher un Employé</h2>
<form method="get" action="TraitementEmployeServlet">
    <div class="row">
        <!-- Ligne 1 -->
		
		<input type="hidden" name="action" id="nom" value="search" >
        <div class="col-md-4">
            <label for="nom">Nom:</label>
            <input type="text" name="nom" id="nom" class="form-control" placeholder="Rechercher par nom">
        </div>
        <div class="col-md-4">
            <label for="email">Email:</label>
            <input type="text" name="email" id="email" class="form-control" placeholder="Rechercher par email">
        </div>
        <div class="col-md-4">
            <label for="telephone">Téléphone:</label>
            <input type="text" name="telephone" id="telephone" class="form-control" placeholder="Rechercher par téléphone">
        </div>
    </div>
    <div class="row mt-3">
        <!-- Ligne 2 -->
        <div class="col-md-4">
            <label for="salaire_min">Salaire Min:</label>
            <input type="number" name="salaire_min" id="salaire_min" class="form-control" placeholder="Salaire Min">
        </div>
        <div class="col-md-4">
            <label for="salaire_max">Salaire Max:</label>
            <input type="number" name="salaire_max" id="salaire_max" class="form-control" placeholder="Salaire Max">
        </div>
        <div class="col-md-4">
            <label for="role">Rôle:</label>
            <select name="role" id="role" class="form-control">
                <option value="">Choisir un rôle</option>
                <% if (roleList != null) { %>
					<% for (Role rolevalue : roleList) { %>
						<option value="<%= rolevalue.getId_role() %>"><%= rolevalue.getLibelle() %></option>
					<% } %>
				<% } %>
            </select>
        </div>
    </div>
    <div class="row mt-3">
        <!-- Ligne 3 -->
        <div class="col-md-4">
            <label for="date_debut">Date Embauche Début:</label>
            <input type="date" name="date_debut" id="date_debut" class="form-control">
        </div>
        <div class="col-md-4">
            <label for="date_fin">Date Embauche Fin:</label>
            <input type="date" name="date_fin" id="date_fin" class="form-control">
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-md-12 text-center">
            <button type="submit" class="btn btn-primary">Rechercher</button>
        </div>
    </div>
</form>

	<section class="section profile">
		<div class="container">
			<div class="row">
				<div class="table-container">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Id</th>
							<th>Nom</th>
							<th>Salaire_personnalise</th>
							<th>Date_embauche</th>
							<th>role</th>
							<th>contact et localisation</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (employeList != null) { %>
							<% for (Employe employevalue : employeList) { %>
								<tr>
								<td><%= employevalue.getId_employe() %></td>
								<td><%= employevalue.getNom() %></td>
								<td><%= employevalue.getSalaire_personnalise() %></td>
								<td><%= employevalue.getDate_embauche() %></td>
								<td><%= employevalue.getRole().getLibelle() %></td>
								<td>
									<a href="FormActionEmployeServlet?action=detailsEmployer&id=<%= employevalue.getId_employe() %>">
										voir
									</a>
								</td>								
								<td><a href="FormActionEmployeServlet?action=handleUpdate&id_employe=<%=employevalue.getId_employe() %>"><i class="far fa-edit"></i></a>
								<a href="FormActionEmployeServlet?action=delete&id_employe=<%=employevalue.getId_employe() %>"><i class="far fa-trash-alt"></i></a></td>
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
