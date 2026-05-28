<%@ page import="it.unisa.model.Utente" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
if (session.getAttribute("prenotazione") == null) {
    response.sendRedirect(request.getContextPath() + "/jsp/HOME/Home.jsp");
    return;
}

String userJson = (String) session.getAttribute("userInfo");
Utente utente = (Utente) session.getAttribute("utente");

boolean isLogged = (userJson != null || utente != null);

if (!isLogged) {
    session.setAttribute("redirectAfterLogin", "PRENOTAZIONE/Pagamento.jsp");
    response.sendRedirect(request.getContextPath() + "/jsp/ACCEDI/Login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pagamento</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Pagamento.css">
</head>
<body>

    <%@ include file="/jsp/Header.jsp" %>

    <h2>Pagamento</h2>
    <form action="<%= request.getContextPath() %>/ConfermaPagamentoServlet" method="post">
        <label for="titolare">Titolare della carta:</label>
        <input type="text" id="titolare" name="titolare" required /> <br> <br>

        <label for="numeroCarta">Numero della carta:</label>
        <input type="text" id="numeroCarta" name="numeroCarta" required /> <br> <br>

        <label for="scadenza">Scadenza (MM/AA):</label>
        <input type="text" id="scadenza" name="scadenza" placeholder="MM/AA" required /> <br> <br>

        <label for="cvc">CVC:</label>
        <input type="text" id="cvc" name="cvc" required /> <br> <br>

        <button type="submit">Prenota e paga</button> <br> <br>
    </form>

</body>
</html>