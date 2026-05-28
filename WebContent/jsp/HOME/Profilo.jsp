<%@ page import="it.unisa.model.Utente" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Profilo - Dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
    <!-- CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Profilo.css">
</head>

<body>
<%
    Utente utente = (Utente) request.getAttribute("utente");
%>

<div class="wrapper">
   <%@ include file="/jsp/Header.jsp" %>

    <main>
        <% if (utente != null) { %>
        <div class="riepilogo-box">
            <h2>Benvenuto <%= utente.getNome() %> 👋</h2>
            <p>Ecco il riepilogo dei tuoi dati:</p>
            <ul>
                <li><strong>Nome:</strong> <%= utente.getNome() %></li>
                <li><strong>Cognome:</strong> <%= utente.getCognome() %></li>
                <li><strong>Data di nascita:</strong> <%= utente.getDataNascita() %></li>
                <li><strong>Documento:</strong> <%= utente.getTipoDocumento() %></li>
                <li><strong>Numero documento:</strong> <%= utente.getNumeroDocumento() %></li>
                <li><strong>Residenza:</strong> <%= utente.getResidenza() %></li>
                <li><strong>Email:</strong> <%= utente.getEmail() %></li>
                
            </ul>
            <a href="prenota.jsp" class="prenota-btn">Prenota una camera</a>
            <br><br>
            <a href="ordini.jsp" class="prenota-btn">Visualizza i tuoi ordini</a>
        </div>
        <% } else { %>
        <div class="riepilogo-box">
            <h2>Errore</h2>
            <p>I dati dell'utente non sono disponibili. Riprova più tardi.</p>
        </div>
        <% } %>
    </main>

      <%@ include file="/jsp/footer.jsp" %>
</div>
</body>
</html>