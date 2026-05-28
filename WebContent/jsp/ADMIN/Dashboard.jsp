<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ page import="it.unisa.model.Admin" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("LoginAdmin.jsp");
        return;
    }

    String adminName = admin.getNome();  
    String gender = admin.getGender();   
    String saluto = "Benvenuto";

    if (gender != null) {
        if (gender.equalsIgnoreCase("F")) {
            saluto = "Benvenuta";
        } else if (gender.equalsIgnoreCase("M")) {
            saluto = "Benvenuto";
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <title>Dashboard Admin</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/dashboard.css">
</head>
<body>

<div class="dashboard-topbar">
    <h1><%= saluto %>, <%= adminName %>!</h1>
    <div class="user-icon">
        <span><%= adminName.substring(0,1).toUpperCase() %></span>
    </div>
</div>

<div class="card-container">
<div class="admin-card">
    <a href="<%= request.getContextPath() %>/admin/camere">
        <img src="<%= request.getContextPath() %>/images/Dashboard_header/carrello.png" alt="B&B">
        <span>Gestione B&B</span>
    </a>
</div>

    <div class="admin-card">
        <a href="<%= request.getContextPath() %>/admin/ordini">
            <img src="<%= request.getContextPath() %>/images/Dashboard_header/img-ordine.png" alt="Ordini">
            <span>Visualizza Ordini</span>
        </a>
    </div>
    
    <div class="admin-card">
    <a href="<%= request.getContextPath() %>/admin/recensioni">
        <img src="<%= request.getContextPath() %>/images/Dashboard_header/recensioni.png" alt="Recensioni">
        <span>Gestione Recensioni</span>
    </a>
</div>

    <div class="admin-card">
        <a href="<%= request.getContextPath() %>/admin/logout">
            <img src="<%= request.getContextPath() %>/images/Dashboard_header/freccia.png" alt="Logout">
            <span>Logout</span>
        </a>
    </div>
</div>

</body>
</html>
