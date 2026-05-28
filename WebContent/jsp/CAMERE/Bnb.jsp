<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, it.unisa.model.Camera, it.unisa.model.dao.CameraDAO,
                 it.unisa.model.Recensione, it.unisa.model.dao.RecensioneDAO,
                 it.unisa.model.Utente, it.unisa.model.UtenteGoogle" %>
<%
    // Parsing robusto città con fallback multipli
    String citta = null;
    String queryString = request.getQueryString();

    // Tentativo 1: decodifica UTF-8
    if (queryString != null) {
        for (String param : queryString.split("&")) {
            try {
                String decoded = java.net.URLDecoder.decode(param, "UTF-8");
                if (decoded.startsWith("città=") || decoded.startsWith("citta=")) {
                    citta = decoded.substring(decoded.indexOf('=') + 1);
                    break;
                }
            } catch (Exception ex) { /* ignora */ }
        }
    }

    // Tentativo 2: decodifica ISO-8859-1 (per citt%E0 dal redirect servlet)
    if (citta == null && queryString != null) {
        for (String param : queryString.split("&")) {
            try {
                String decoded = java.net.URLDecoder.decode(param, "ISO-8859-1");
                if (decoded.startsWith("città=") || decoded.startsWith("citta=")) {
                    citta = decoded.substring(decoded.indexOf('=') + 1);
                    break;
                }
            } catch (Exception ex) { /* ignora */ }
        }
    }

    // Tentativo 3: getParameter diretto senza accento
    if (citta == null || citta.trim().isEmpty()) {
        citta = request.getParameter("citta");
    }

    // Tentativo 4: getParameter diretto con accento
    if (citta == null || citta.trim().isEmpty()) {
        citta = request.getParameter("città");
    }

    if (citta == null || citta.trim().isEmpty()) {
        out.println("<h1>Errore: città non specificata</h1>");
        return;
    }

    if (citta.contains(",")) citta = citta.split(",")[0].trim();
    citta = citta.substring(0, 1).toUpperCase() + citta.substring(1);

    List<Camera> camere = new java.util.ArrayList<>();
    try {
        CameraDAO dao = new CameraDAO();
        camere = dao.findByCitta(citta);
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Utente loggato?
    Utente utente = (Utente) session.getAttribute("utente");
    UtenteGoogle utenteGoogle = (UtenteGoogle) session.getAttribute("utenteLoggato");
    boolean loggato = (utente != null || utenteGoogle != null);

    // Feedback recensione
    String recStatus = (String) session.getAttribute("recensioneInviata");
    if (recStatus != null) session.removeAttribute("recensioneInviata");

    // Camera da riaprire dopo invio recensione
    String openCamera = request.getParameter("openCamera");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= citta %> - Sunset B&B</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Camera.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/recensioni.css">
</head>
<body>
<div class="page-wrapper">
<%@ include file="/jsp/Header.jsp" %>

<% if ("ok".equals(recStatus)) { %>
    <div class="toast show" id="toastOk">✅ Recensione inviata con successo!</div>
    <script>setTimeout(() => document.getElementById('toastOk').classList.remove('show'), 5000);</script>
<% } else if ("errore".equals(recStatus)) { %>
    <div class="toast show" style="background:#c62828;" id="toastErr">❌ Errore nell'invio della recensione.</div>
    <script>setTimeout(() => document.getElementById('toastErr').classList.remove('show'), 5000);</script>
<% } %>

<h1>Benvenuti a <%= citta %></h1>

<div class="gallery">
<% if (camere.isEmpty()) { %>
    <p>Camere non disponibili per questa città.</p>
<% } else {
    RecensioneDAO recDAO = new RecensioneDAO();
    for (Camera cam : camere) {
        String[] immagini = cam.getImmagini() != null ? cam.getImmagini().split(",") : new String[0];
        List<Recensione> recensioni = recDAO.getByCamera(cam.getId());
        double media = recDAO.getMediaStelle(cam.getId());
        int mediaInt = (int) Math.round(media);
%>
    <div class="card">
        <div class="slideshow-container">
            <% for (int k = 0; k < immagini.length; k++) { %>
                <img src="<%= request.getContextPath() + "/images/IMMAGINI-BNB/" + citta + "/" + immagini[k].trim() %>"
                     alt="<%= cam.getNome() %>"
                     class="<%= (k == 0 ? "active" : "") %>">
            <% } %>
            <a class="prev" onclick="slidePrev(this)">&#10094;</a>
            <a class="next" onclick="slideNext(this)">&#10095;</a>
        </div>

        <h3><%= cam.getNome() %></h3>
        <p><%= cam.getDescrizione() %></p>
        <p><strong>Extra:</strong> <%= cam.getExtra() %></p>
        <p class="prezzo">€<%= String.format("%.0f", cam.getPrezzo()) %> / notte</p>

        <!-- Prenota + media stelle -->
        <div class="prenota-area">
            <form action="<%= request.getContextPath() %>/RiepilogoPrenotazioneServlet" method="post">
                <input type="hidden" name="citta"     value="<%= citta %>" />
                <input type="hidden" name="nomeBnb"   value="<%= cam.getNome() %>" />
                <input type="hidden" name="imgBnb"    value="<%= immagini.length > 0 ? immagini[0].trim() : "" %>" />
                <input type="hidden" name="checkin"   value="<%= request.getParameter("checkin") %>" />
                <input type="hidden" name="checkout"  value="<%= request.getParameter("checkout") %>" />
                <input type="hidden" name="adulti"    value="<%= request.getParameter("adulti") %>" />
                <input type="hidden" name="bambini"   value="<%= request.getParameter("bambini") %>" />
                <input type="hidden" name="camere"    value="<%= request.getParameter("camere") %>" />
                <button type="submit" class="prenota-btn">Prenota</button>
            </form>
            <div class="media-stelle">
                <% if (recensioni.isEmpty()) { %>
                    <span style="color:#999;font-size:13px;">Nessuna recensione</span>
                <% } else { %>
                    <% for (int s = 1; s <= 5; s++) { %><span><%= s <= mediaInt ? "★" : "☆" %></span><% } %>
                    (<%= String.format("%.1f", media) %> · <%= recensioni.size() %> recension<%= recensioni.size()==1?"e":"i" %>)
                <% } %>
            </div>
        </div>

        <!-- Bottone mostra/nascondi recensioni -->
        <button class="btn-recensioni" onclick="toggleRec('rec-<%= cam.getId() %>')">
            💬 Vedi recensioni (<%= recensioni.size() %>)
        </button>

        <div id="rec-<%= cam.getId() %>" style="display:none;">

            <!-- Form nuova recensione -->
            <div class="rec-form">
                <strong>Lascia una recensione:</strong><br>
                <form action="<%= request.getContextPath() %>/InviaRecensione" method="post">
                    <input type="hidden" name="idCamera" value="<%= cam.getId() %>" />
                    <input type="hidden" name="citta"    value="<%= citta %>" />
                    <div class="star-rating">
                        <input type="radio" name="stelle" id="s5-<%= cam.getId() %>" value="5"><label for="s5-<%= cam.getId() %>">★</label>
                        <input type="radio" name="stelle" id="s4-<%= cam.getId() %>" value="4"><label for="s4-<%= cam.getId() %>">★</label>
                        <input type="radio" name="stelle" id="s3-<%= cam.getId() %>" value="3"><label for="s3-<%= cam.getId() %>">★</label>
                        <input type="radio" name="stelle" id="s2-<%= cam.getId() %>" value="2"><label for="s2-<%= cam.getId() %>">★</label>
                        <input type="radio" name="stelle" id="s1-<%= cam.getId() %>" value="1"><label for="s1-<%= cam.getId() %>">★</label>
                    </div>
                    <textarea name="commento" rows="3" placeholder="Scrivi il tuo commento (opzionale)..."></textarea>
                    <button type="submit">Invia recensione</button>
                </form>
            </div>

            <!-- Lista recensioni esistenti -->
            <div class="recensioni-lista">
                <% if (recensioni.isEmpty()) { %>
                    <p style="font-size:13px;color:#999;">Nessuna recensione ancora. Sii il primo!</p>
                <% } else { for (Recensione r : recensioni) { %>
                    <div class="rec-item">
                        <div class="rec-header">
                            <span class="rec-nome"><%= r.getNomeUtente() %></span>
                            <span class="rec-stelle"><% for(int s=1;s<=5;s++) out.print(s<=r.getStelle()?"★":"☆"); %></span>
                        </div>
                        <div class="rec-data"><%= r.getDataRec() %></div>
                        <% if (r.getCommento() != null && !r.getCommento().isEmpty()) { %>
                            <div class="rec-commento"><%= r.getCommento() %></div>
                        <% } %>
                    </div>
                <% } } %>
            </div>
        </div>
    </div>
<% } } %>
</div>

<!-- Lightbox -->
<div id="lightbox" class="lightbox">
    <span class="close">&times;</span>
    <img class="lightbox-content" id="lightbox-img">
    <div class="lightbox-prev">&#10094;</div>
    <div class="lightbox-next">&#10095;</div>
</div>

<script src="<%= request.getContextPath() %>/js/bnb.js"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script>
function toggleRec(id) {
    const el = document.getElementById(id);
    el.style.display = el.style.display === 'none' ? 'block' : 'none';
}
const openCamera = "<%= openCamera != null ? openCamera : "" %>";
if (openCamera) {
    const el = document.getElementById('rec-' + openCamera);
    if (el) el.style.display = 'block';
}
</script>

<%@ include file="/jsp/footer.jsp" %>
</div>
</body>
</html>