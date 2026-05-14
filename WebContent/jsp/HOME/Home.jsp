<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>

<%@ page import="it.unisa.model.Utente, it.unisa.model.UtenteGoogle" %>
<%@ page import="org.json.JSONObject" %>
<%
    String userJson = (String) session.getAttribute("userInfo");
    Utente utenteNormale = (Utente) session.getAttribute("utente");
    UtenteGoogle utenteGoogle = (UtenteGoogle) session.getAttribute("utenteLoggato");
    boolean isLogged = (userJson != null || utenteNormale != null || utenteGoogle != null);

    String nomeUtente = "";
    String fotoUtente = "";

    if (utenteGoogle != null) {
        nomeUtente = utenteGoogle.getNome() != null ? utenteGoogle.getNome() : "";
        fotoUtente = utenteGoogle.getImmagineProfilo() != null ? utenteGoogle.getImmagineProfilo() : "";
    } else if (userJson != null) {
        try {
            JSONObject user = new JSONObject(userJson);
            nomeUtente = user.optString("name", "");
            fotoUtente = user.optString("picture", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else if (utenteNormale != null) {
        nomeUtente = utenteNormale.getNome() + " " + utenteNormale.getCognome();
        fotoUtente = "";
    }
%>

<%
    // Parametri passati da riepilogoPrenotazione.jsp
    String cittaParam = request.getParameter("citta");
    String checkinParam = request.getParameter("checkin");
    String checkoutParam = request.getParameter("checkout");
    String adultiParam = request.getParameter("adulti");
    String bambiniParam = request.getParameter("bambini");
    String camereParam = request.getParameter("camere");
%>
    
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Security-Policy" content="img-src * data: blob: https:;">
<meta name="referrer" content="no-referrer">



<!-- CSS Flatpickr -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">




<style><%@include file="/css/Home.css" %></style>
<style><%@include file="/css/Citta.css"%></style>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Sunset B&B</title>

</head>
<body>

  
  
  <header>
    <div class="header">
      <img  id="logo_header" alt="LOGO BNB" src="<%= request.getContextPath() %>/images/IMMAGINI-VARIE/LOGO_BNB.png" >
      <h3>SUNSET B&B</h3>
    </div>
    
	<!-- ✅ Selettore lingua con bandiera -->
	<nav>
  <div class="menu-toggle" id="menu-toggle">
    <span></span>
    <span></span>
    <span></span>
  </div>

  <ul class="nav-links" id="nav-links">
    
       <li>
  <div class="gtranslate_wrapper"></div>
</li>
    
    
   

    <!-- Link di navigazione -->
    <li><a class="nav-buttons" id="home" href="#home">Home</a></li>
    <!-- <li><a class="nav-buttons" href="#home">Home</a></li> -->
    <li><a class="nav-buttons" href="<%= request.getContextPath() %>/jsp/HOME/Contatti.jsp">Contatti</a></li>

    <li>
          <% if (isLogged) { %>
            <div class="dropdown">
              <% if (!fotoUtente.isEmpty()) { %>
    <img src="<%= fotoUtente %>" alt="Profilo" class="account-icon" onclick="toggleAccountDropdown()" />
<% } else { %>
    <div class="avatar-iniziali-nav" onclick="toggleAccountDropdown()">
        <%= nomeUtente.isEmpty() ? "?" : String.valueOf(nomeUtente.charAt(0)) %>
    </div>
<% } %>
            <div id="dropdown-menu-account" class="dropdown-content">
    <p><strong><%= nomeUtente %></strong></p>
    <form action="<%= request.getContextPath() %>/logout" method="get">
        <a href="<%= request.getContextPath() %>/VisualizzaProfiloServlet">Visualizza Profilo</a> <br> <br>
        <a href="<%= request.getContextPath() %>/VisualizzaPrenotazioneServlet">Visualizza Prenotazioni</a> <br> <br>
        <button type="submit">Esci</button>
    </form>
</div>
            </div>
          <% } else { %>
            <a href="<%= request.getContextPath() %>/jsp/ACCEDI/Login.jsp" class="login-btn">
              <img src="<%= request.getContextPath() %>/images/IMMAGINI-VARIE/LOGIN-IMAGE.png" alt="Icona login">
              Accedi
            </a>
          <% } %>
        </li>
  </ul>
</nav>
    
   
  </header>

 
  

<!-- BARRA DI RICERCA -->
<div class="img-container">
	<img src="<%= request.getContextPath() %>/images/IMMAGINI-VARIE/SFONDO-HOME.jpg" class="SFONDO-PROVA">
	<form id="searchForm" action="<%= request.getContextPath() %>/jsp/CAMERE/Bnb.jsp" method="get" onsubmit="return validateForm()">

<div class="search-bar">
  <input type="text" id="città" name="citta" placeholder="Dove vuoi andare?" />
  <input type="date" id="checkin" name="checkin" placeholder="Check-in" required />
<input type="date" id="checkout" name="checkout" placeholder="Check-out" required />


 
    <button type="button" class="btn-chi" onclick="toggleDropdown()">Persone</button>

    
    <div id="chi-dropdown" class="dropdown-content">
  <div class="option">
    <span>Adulti</span>
    <div>
   <span class="btn-circle" onclick="decrement('adulti')">−</span>
	<span id="adulti">1</span>
	<span class="btn-circle" onclick="increment('adulti')">+</span>
	   
    </div>
  </div>
  <div class="option">
    <span>Bambini (0-13)</span>
    <div>
      <span class="btn-circle" onclick="decrement('bambini')">−</span>
	  <span id="bambini">0</span>
	  <span class="btn-circle" onclick="increment('bambini')">+</span>
      
    </div>
  </div>
  <div class="option">
    <span>Camere</span>
    <div>
      <span class="btn-circle" onclick="decrement('camere')">−</span>
      <span id="camere">1</span>
      <span class="btn-circle" onclick="increment('camere')">+</span>
    </div>
  </div>
</div>
    
  <input type="hidden" name="adulti" id="adultiInput" value="1" />
<input type="hidden" name="bambini" id="bambiniInput" value="0" />
<input type="hidden" name="camere" id="camereInput" value="1" />
  

  <button type="submit" class="cerca-btn">Cerca</button>
</div>
  </form>
  </div>

 

<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/it.js"></script>
<script src="<%= request.getContextPath() %>/js/home.js"></script>
 <footer>
 <p>&copy;2025 SUNSET B&B - TUTTI I DIRITTI RISERVATI</p>

 
 </footer>

</body>
</html>