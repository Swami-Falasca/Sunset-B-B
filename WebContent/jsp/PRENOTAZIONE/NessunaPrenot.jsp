<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nessuna Prenotazione</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/NessunaPrenot.css">
     <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>

<%@ include file="/jsp/Header.jsp" %>

<div class="nessuna-container">
    <h2>📭 Nessuna prenotazione effettuata</h2>
    <p>Non hai ancora prenotato nessun soggiorno.<br>Esplora i nostri B&B e trova quello che fa per te!</p>
    <a href="<%= request.getContextPath() %>/jsp/HOME/Home.jsp" class="btn-home">
        Esplora i B&B
    </a>
</div>

 <%@ include file="/jsp/footer.jsp" %>

</body>
</html>