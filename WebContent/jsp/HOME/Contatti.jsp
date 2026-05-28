<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Contatti</title>

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">

    <!-- Foglio di stile principale -->
    <style><%@include file="/css/Contatti.css" %></style>
   
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>
<%
    String successo = request.getParameter("successo");
    if ("true".equals(successo)) {
%>
    <div id="messaggio-successo" style="color: green; font-weight: bold;">
        Messaggio inviato con successo!
    </div>
    <script src="<%= request.getContextPath() %>/js/contatti.js"></script>
<%
    }
%>
<header>
    <div class="header">
        <img id="logo_header" src="<%= request.getContextPath() %>/images/IMMAGINI-VARIE/LOGO_BNB.png" alt="Logo Sunset B&B">
        <h1 style="text-align: right;">Contattaci</h1>
    </div>
    <nav>
        <ul class="nav-links">
            <li><a href="<%= request.getContextPath() %>/jsp/HOME/Home.jsp">Home</a></li>
            <li><a href="<%= request.getContextPath() %>/jsp/HOME/Contatti.jsp">Contatti</a></li>
        </ul>
    </nav>
</header>

<main class="pagina-contatti">
    <h2>Inviaci un messaggio</h2>
    <form action="<%= request.getContextPath() %>/ContattaciServlet" method="post" class="form-contatti">

        <label for="nome">Nome</label>
        <input type="text" id="nome" name="nome" placeholder="Il tuo nome" required>

        <label for="email">Email</label>
        <input type="email" id="email" name="email" placeholder="La tua email" required>

        <label for="messaggio">Messaggio</label>
        <textarea id="messaggio" name="messaggio" rows="6" placeholder="Scrivi qui il tuo messaggio" required></textarea>

        <button type="submit" class="nav-buttons">Invia</button>
    </form>
</main>

  <%@ include file="/jsp/footer.jsp" %>

</body>
</html>