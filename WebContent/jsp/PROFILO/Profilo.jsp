<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.model.Utente, it.unisa.model.UtenteGoogle" %>

<%
    String tipoUtente = (String) request.getAttribute("tipoUtente");
    Utente u = (Utente) request.getAttribute("profiloUtente");
    UtenteGoogle g = (UtenteGoogle) request.getAttribute("profiloGoogle");

    String nome = "";
    String cognome = "";
    String email = "";
    String residenza = "";
    String dataNascita = "";
    String tipoDoc = "";
    String numeroDoc = "";
    String fotoProfilo = "";

    if ("normale".equals(tipoUtente) && u != null) {
        nome = u.getNome() != null ? u.getNome() : "";
        cognome = u.getCognome() != null ? u.getCognome() : "";
        email = u.getEmail() != null ? u.getEmail() : "";
        residenza = u.getResidenza() != null ? u.getResidenza() : "Non specificata";
        dataNascita = u.getDataNascita() != null ? u.getDataNascita().toString() : "Non specificata";
        tipoDoc = u.getTipoDocumento() != null ? u.getTipoDocumento() : "Non specificato";
        numeroDoc = u.getNumeroDocumento() != null ? u.getNumeroDocumento() : "Non specificato";
    } else if ("google".equals(tipoUtente) && g != null) {
        String fullName = g.getNome() != null ? g.getNome() : "";
        String[] parts = fullName.split(" ", 2);
        nome = parts[0];
        cognome = parts.length > 1 ? parts[1] : "";
        email = g.getEmail() != null ? g.getEmail() : "";
        fotoProfilo = g.getImmagineProfilo() != null ? g.getImmagineProfilo() : "";
        residenza = "Non specificata";
        dataNascita = "Non specificata";
        tipoDoc = "Non specificato";
        numeroDoc = "Non specificato";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profilo - SUNSET B&B</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Profilo.css">
</head>
<body>

<header>
    <div class="header">
        <img id="logo_header" alt="LOGO BNB"
             src="<%= request.getContextPath() %>/images/IMMAGINI-VARIE/LOGO_BNB.png">
        <h3>SUNSET B&B</h3>
    </div>
    <a href="<%= request.getContextPath() %>/jsp/HOME/Home.jsp" class="btn-torna">
        <i class="fas fa-arrow-left"></i> Torna alla Home
    </a>
</header>

<div class="profilo-wrapper">
    <h2>Il tuo Profilo</h2>

    <div class="profilo-card">

        <!-- Avatar -->
        <div class="avatar-container">
            <% if (!fotoProfilo.isEmpty()) { %>
                <img src="<%= fotoProfilo %>" alt="Foto profilo" class="avatar-foto">
            <% } else { %>
                <div class="avatar-iniziali">
                    <%= nome.isEmpty() ? "?" : String.valueOf(nome.charAt(0)) %>
                </div>
            <% } %>
        </div>

        <!-- Campi profilo -->
        <div class="profilo-campi">

            <div class="campo">
                <i class="fas fa-user"></i>
                <div>
                    <span class="campo-label">Nome</span>
                    <span class="campo-valore"><%= nome.isEmpty() ? "Non specificato" : nome %></span>
                </div>
            </div>

            <div class="campo">
                <i class="fas fa-user"></i>
                <div>
                    <span class="campo-label">Cognome</span>
                    <span class="campo-valore"><%= cognome.isEmpty() ? "Non specificato" : cognome %></span>
                </div>
            </div>

            <div class="campo">
                <i class="fas fa-envelope"></i>
                <div>
                    <span class="campo-label">Email</span>
                    <span class="campo-valore"><%= email.isEmpty() ? "Non specificata" : email %></span>
                </div>
            </div>

    

        </div>
    </div>
</div>

<footer>
    <p>&copy; 2025 SUNSET B&B - Tutti i diritti riservati</p>
</footer>

</body>
</html>