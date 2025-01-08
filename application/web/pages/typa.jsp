<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
    List<Typa> typaList = null;
    Typa typa = null;
    
    try {
        typaList = (List<Typa>) request.getAttribute("typaList");
        typa = (Typa) request.getAttribute("typa");
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
                                <th>Id_Typa</th>
                                <th>Libelle</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (typaList != null) { %>
                                <% for (Typa typaValue : typaList) { %>
                                    <tr>
                                        <td><%= typaValue.getId_typa() %></td>
                                        <td><%= typaValue.getLibelle() %></td>
                                        <td>
                                            <a href="FormActionTypaServlet?action=handleUpdate&id_typa=<%= typaValue.getId_typa() %>">
                                                <i class="far fa-edit"></i>
                                            </a>
                                            <a href="FormActionTypaServlet?action=delete&id_typa=<%= typaValue.getId_typa() %>">
                                                <i class="far fa-trash-alt"></i>
                                            </a>
                                        </td>
                                    </tr>
                                <% } %>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</main>
<%@ include file="../templates/footer.jsp" %>
