<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
Boolean successo = (Boolean) request.getAttribute("successo");
if (successo == null || !successo) {
    response.sendRedirect(request.getContextPath() + "/jsp/HOME/Home.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Conferma Pagamento</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/confermaPagamento.css">
</head>
<body>

    <div class="success-container">
        <h1>PAGAMENTO EFFETTUATO<br>CON SUCCESSO</h1>

        <div class="success-icon">
            <span>✓</span>
        </div>

        <p class="success-message">Il tuo pagamento è stato completato con successo.</p>
        <p class="success-redirect">Sarai reindirizzato alla home.</p>
    </div>

    <script>
        var contextPath = "<%= request.getContextPath() %>";
    </script>
    <script src="<%= request.getContextPath() %>/js/confermaPagamento.js"></script>

</body>
</html>
