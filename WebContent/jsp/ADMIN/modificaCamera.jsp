<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.unisa.model.Admin, it.unisa.model.Camera" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) { response.sendRedirect("LoginAdmin.jsp"); return; }
    Camera c = (Camera) request.getAttribute("camera");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica B&B</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/dashboard.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/dashboard.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminCamere.css">
</head>
<body>

<div class="dashboard-topbar">
    <h1>Modifica B&B</h1>
    <div class="user-icon"><span><%= admin.getNome().substring(0,1).toUpperCase() %></span></div>
</div>

<main>
    <h2>Modifica: <%= c.getNome() %></h2>
    <div class="città-info">📍 Città: <strong><%= c.getNomeCitta() %></strong></div>

    <form action="<%= request.getContextPath() %>/admin/modificaCamera" method="post">
        <input type="hidden" name="id" value="<%= c.getId() %>">

        <div class="form-group">
            <label>Nome B&B</label>
            <input type="text" name="nome" value="<%= c.getNome() %>" required>
        </div>

        <div class="form-group">
            <label>Descrizione camere</label>
            <textarea name="descrizione"><%= c.getDescrizione() %></textarea>
        </div>

        <div class="form-group">
            <label>Extra / Servizi</label>
            <textarea name="extra"><%= c.getExtra() %></textarea>
        </div>

        <div class="form-group">
            <label>Prezzo per notte (€)</label>
            <input type="number" name="prezzo" step="0.01" value="<%= c.getPrezzo() %>" required>
        </div>

        <div class="azioni-form">
            <button type="submit" class="btn-salva">Salva modifiche</button>
            <a href="<%= request.getContextPath() %>/admin/camere" class="btn-back">Annulla</a>
        </div>
    </form>
</main>
</body>
</html>