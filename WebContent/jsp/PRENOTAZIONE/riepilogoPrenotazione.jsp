<%@ page import="it.unisa.model.Prenotazione" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
Prenotazione p = (Prenotazione) session.getAttribute("prenotazione");
if (p == null) {
    response.sendRedirect(request.getContextPath() + "/jsp/HOME/Home.jsp");
    return;
}

String userJson = (String) session.getAttribute("userInfo");
it.unisa.model.Utente utente = (it.unisa.model.Utente) session.getAttribute("utente");
boolean isLogged = (userJson != null || utente != null);

if (!isLogged) {
    session.setAttribute("redirectAfterLogin", "/jsp/PRENOTAZIONE/Pagamento.jsp");
    response.sendRedirect(request.getContextPath() + "/jsp/ACCEDI/Login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Riepilogo Prenotazione</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/riepilogoPrenotazione.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>

<%@ include file="/jsp/Header.jsp" %>

<h2>Riepilogo Prenotazione</h2>

<div class="riepilogo-container">

    <div class="riepilogo-foto">
        <img src="<%= request.getContextPath() + "/images/IMMAGINI-BNB/" + p.getCitta() + "/" + p.getImmagineBnb() %>"
             alt="<%= p.getCitta() %>">

        <div class="riepilogo-bottoni">
            <form action="<%= request.getContextPath() %>/jsp/HOME/Home.jsp" method="get">
                <button type="submit" class="btn-modifica">Modifica</button>
            </form>
            <form action="<%= request.getContextPath() %>/jsp/PRENOTAZIONE/Pagamento.jsp" method="get">
                <button type="submit" class="btn-avanti">Avanti</button>
            </form>
        </div>
    </div>

    <div class="riepilogo-info">
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