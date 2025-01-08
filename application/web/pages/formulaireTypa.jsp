<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%
    Typa typa = null;

    try {
        typa = (Typa) request.getAttribute("typa");
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
                <form action="FormActionTypaServlet" method="post">
                    <div class="form-group">
                        <input type="hidden" class="form-control" id="id_typa" name="id_typa" 
                               value="<%= (typa != null && typa.getId_typa() != null) ? typa.getId_typa() : "" %>">
                        <label for="libelle">Libelle</label>
                        <input type="text" class="form-control" id="libelle" name="libelle" 
                               value="<%= (typa != null && typa.getLibelle() != null) ? typa.getLibelle() : "" %>">
                    </div>
                    
                    <input type="hidden" name="action" value="<%= (request.getAttribute("action") == null) ? "insert" : ("handleUpdate".equals(request.getAttribute("action")) ? "update" : "insert") %>">
                    
                    <button type="submit" class="btn btn-primary">
                        <%= (request.getAttribute("action") == null) ? "Insert" : ("handleUpdate".equals(request.getAttribute("action")) ? "Update" : "Insert") %>
                    </button>
                </form>
            </div>
        </div>
    </section>
</main>
<%@ include file="../templates/footer.jsp" %>
