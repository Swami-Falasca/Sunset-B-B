<%@ page import="it.unisa.model.Prenotazione" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
Prenotazione p = (Prenotazione) session.getAttribute("prenotazione");

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>La tua prenotazione</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/riepilogoPrenotazione.css">
     <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>

<%@ include file="/jsp/Header.jsp" %>

<h2>📋 Dettagli Prenotazione</h2>

<div class="riepilogo-container">

    <!-- FOTO + BOTTONI -->
    <div class="riepilogo-foto">
        <img src="<%= request.getContextPath() + "/images/IMMAGINI-BNB/" + p.getCitta() + "/" + p.getImmagineBnb() %>"
             alt="<%= p.getCitta() %>">

        <!-- Bottone cancella -->
        <div class="riepilogo-bottoni">
            <form action="<%= request.getContextPath() %>/CancellaPrenotazioneServlet" method="post">
                <button type="submit" class="btn-modifica">Cancella Prenotazione</button>
            </form>
        </div>
    </div>

    <!-- INFO PRENOTAZIONE -->
    <div class="riepilogo-info">
        <!-- Nome utente sopra il B&B -->

        <p><i class="fas fa-location-dot icon"></i><strong>Città:</strong> <%= p.getCitta() %></p>
        <p><i class="fas fa-bed icon"></i><strong>B&B:</strong> <%= p.getNomeBnb() %></p>
        <p><i class="fas fa-calendar-check icon"></i><strong>Check-in:</strong> <%= p.getCheckin() %></p>
        <p><i class="fas fa-calendar-minus icon"></i><strong>Check-out:</strong> <%= p.getCheckout() %></p>
        <p><i class="fas fa-user icon"></i><strong>Adulti:</strong> <%= p.getAdulti() %></p>
        <p><i class="fas fa-child-reaching icon"></i><strong>Bambini:</strong> <%= p.getBambini() %></p>
        <p><i class="fas fa-door-closed icon"></i><strong>Camere:</strong> <%= p.getCamere() %></p>
        <p><i class="fas fa-euro-sign icon"></i><strong>Prezzo Totale:</strong> €<%= p.getPrezzoTotale() %></p>
    </div>

</div>
<%@ include file="/jsp/footer.jsp" %>
</body>
</html>
