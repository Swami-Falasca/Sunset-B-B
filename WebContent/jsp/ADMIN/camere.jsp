<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.unisa.model.Admin, it.unisa.model.Camera, it.unisa.model.Recensione, java.util.List" %>
<%@ page import="it.unisa.model.dao.RecensioneDAO" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) { response.sendRedirect("LoginAdmin.jsp"); return; }
    List<Camera> camere = (List<Camera>) request.getAttribute("camere");

    RecensioneDAO recDAO = new RecensioneDAO();
    List<Recensione> tutteRec = recDAO.getAll();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestione B&B</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/dashboard.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/camere.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/recensioni.css">
</head>
<body>

<div class="dashboard-topbar">
    <h1>Gestione B&B</h1>
    <div class="user-icon"><span><%= admin.getNome().substring(0,1).toUpperCase() %></span></div>
</div>

<main>
    <div class="page-header">
        <h2>Tutte le strutture (<%= camere != null ? camere.size() : 0 %>)</h2>
        <a href="<%= request.getContextPath() %>/jsp/ADMIN/Dashboard.jsp" class="btn-back">← Dashboard</a>
    </div>

    <table class="camere-table">
        <thead>
            <tr>
                <th>#</th>
                <th>Città</th>
                <th>Nome B&B</th>
                <th>Descrizione</th>
                <th>Extra</th>
                <th>Prezzo/notte</th>
                <th>Azione</th>
            </tr>
        </thead>
        <tbody>
        <% if (camere != null) { for (Camera c : camere) { %>
            <tr>
                <td><%= c.getId() %></td>
                <td><span class="città-tag"><%= c.getNomeCitta() %></span></td>
                <td><strong><%= c.getNome() %></strong></td>
                <td><%= c.getDescrizione() %></td>
                <td><%= c.getExtra() %></td>
                <td>€<%= String.format("%.2f", c.getPrezzo()) %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/admin/modificaCamera?id=<%= c.getId() %>"
                       class="btn-modifica">Modifica</a>
                </td>
            </tr>
        <% } } %>
        </tbody>
    </table>

    <!-- ===== SEZIONE RECENSIONI ===== -->
    <div class="rec-admin-section">
        <h2>⭐ Tutte le Recensioni (<%= tutteRec.size() %>)</h2>
        <% if (tutteRec.isEmpty()) { %>
            <p>Nessuna recensione presente.</p>
        <% } else { %>
        <table class="rec-admin-table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>ID Camera</th>
                    <th>Utente</th>
                    <th>Email</th>
                    <th>Stelle</th>
                    <th>Commento</th>
                    <th>Data</th>
                </tr>
            </thead>
            <tbody>
            <% for (Recensione r : tutteRec) { %>
                <tr>
                    <td><%= r.getId() %></td>
                    <td><%= r.getIdCamera() %></td>
                    <td><%= r.getNomeUtente() %></td>
                    <td><%= r.getEmailUtente() %></td>
                    <td class="stelle-gold">
                        <% for (int s=1;s<=5;s++) out.print(s<=r.getStelle()?"★":"☆"); %>
                        (<%= r.getStelle() %>)
                    </td>
                    <td><%= r.getCommento() != null ? r.getCommento() : "-" %></td>
                    <td><%= r.getDataRec() %></td>
                </tr>
            <% } %>
            </tbody>
        </table>
        <% } %>
    </div>

</main>
</body>
</html>