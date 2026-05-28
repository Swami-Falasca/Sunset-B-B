<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ page import="it.unisa.model.Admin, it.unisa.model.Recensione, java.util.List" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("LoginAdmin.jsp");
        return;
    }
    List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione Recensioni</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/dashboard.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/adminRecensioni.css">
</head>
<body>

<div class="dashboard-topbar">
    <h1>Gestione Recensioni</h1>
    <div class="user-icon">
        <span><%= admin.getNome().substring(0,1).toUpperCase() %></span>
    </div>
</div>

<a href="<%= request.getContextPath() %>/jsp/ADMIN/Dashboard.jsp" class="back-btn">← Torna alla Dashboard</a>

<h2 class="page-title">Tutte le recensioni</h2>

<% if (recensioni == null || recensioni.isEmpty()) { %>
    <p class="nessuna">Nessuna recensione presente.</p>
<% } else { %>
    <table class="recensioni-table">
        <thead>
            <tr>
                <th>#</th>
                <th>Camera</th>
                <th>Utente</th>
                <th>Email</th>
                <th>Stelle</th>
                <th>Commento</th>
                <th>Data</th>
                <th>Azione</th>
            </tr>
        </thead>
        <tbody>
        <% for (Recensione r : recensioni) { %>
            <tr>
                <td><%= r.getId() %></td>
                <td><%= r.getIdCamera() %></td>
                <td><%= r.getNomeUtente() %></td>
                <td><%= r.getEmailUtente() %></td>
                <td class="stelle">
                    <% for (int s = 1; s <= 5; s++) { %>
                        <%= s <= r.getStelle() ? "★" : "☆" %>
                    <% } %>
                </td>
                <td class="commento-cell">
                    <%= (r.getCommento() != null && !r.getCommento().isEmpty())
                        ? r.getCommento() : "<span class='nessun-commento'>—</span>" %>
                </td>
                <td><%= r.getDataRec() %></td>
                <td>
                    <form action="<%= request.getContextPath() %>/admin/recensioni" method="post"
                          onsubmit="return confirm('Sei sicuro di voler eliminare questa recensione?')">
                        <input type="hidden" name="idRecensione" value="<%= r.getId() %>" />
                        <button type="submit" class="btn-elimina">Elimina</button>
                    </form>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
<% } %>

</body>
</html>