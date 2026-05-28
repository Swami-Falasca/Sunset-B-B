<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <meta charset="UTF-8">
    <style><%@include file="/css/Home.css" %></style>
    <style><%@include file="/css/Accedi.css"%></style>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
    
    <title>LOGIN</title>
</head>
<body>

<%@ include file="/jsp/Header.jsp" %>

<main>
    <section class="container">
        <h2>Crea un account</h2>
        <p>Registrati per iniziare a esplorare alloggi unici in tutto il mondo!</p>
        <hr>

        <%
            String msg = (String) session.getAttribute("loginMessage");
            if (msg != null) {
        %>
            <div class="alert alert-info"><%= msg %></div>
        <%
                session.removeAttribute("loginMessage");
            }
            String errore = (String) request.getAttribute("errore");
            if (errore != null) {
        %>
            <div class="alert alert-danger"><%= errore %></div>
        <%
            }
        %>

        <form action="<%= request.getContextPath() %>/GestioneForm" method="POST">
            <div class="form-group">
                <input type="email" id="email" name="email" required placeholder=" ">
                <label for="email">Email</label>
            </div>

            <div class="form-group">
                <input type="password" id="password" name="password" required placeholder=" ">
                <label for="password">Password</label>
            </div>

            <div class="register-link">
                <p>Non hai un account? <a href="<%= request.getContextPath() %>/jsp/HOME/Registrati.jsp">Registrati</a></p>
            </div>

            <button type="submit" class="accedi-btn">Accedi</button>
        </form>

        <a href="https://accounts.google.com/o/oauth2/v2/auth?client_id=489957560905-2fn9en5cc1agt4e53u0k93avsfmkibe5.apps.googleusercontent.com&redirect_uri=<%= java.net.URLEncoder.encode(request.getScheme() + "://" + request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + request.getContextPath() + "/oauth2callback", "UTF-8") %>&response_type=code&scope=profile%20email">
    <button class="social-login google">Continua con Google</button>
</a>

    </section>
</main>

        <%@ include file="/jsp/footer.jsp" %>

</body>
</html>