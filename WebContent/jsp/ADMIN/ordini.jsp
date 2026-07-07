<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.unisa.model.Admin, it.unisa.model.Prenotazione, java.util.List" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
if (admin == null) { response.sendRedirect(request.getContextPath() + "/jsp/ACCEDI/Login.jsp"); return; }
    List<Prenotazione> prenotazioni = (List<Prenotazione>) request.getAttribute("prenotazioni");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Visualizza Ordini</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/dashboard.css">
 <link rel="stylesheet" href="<%= request.getContextPath() %>/css/dashboard.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminCamere.css">
</head>
<body>

<div class="dashboard-topbar">
    <h1>Visualizza Ordini</h1>
    <div class="user-icon"><span><%= admin.getNome().substring(0,1).toUpperCase() %></span></div>
</div>

<main>
    <div class="page-header">
        <h2>Tutte le prenotazioni
            (<%= prenotazioni != null ? prenotazioni.size() : 0 %>)
        </h2>
        <a href="<%= request.getContextPath() %>/jsp/ADMIN/Dashboard.jsp" class="btn-back">← Dashboard</a>
    </div>

    <% if (prenotazioni == null || prenotazioni.isEmpty()) { %>
        <div class="empty-state">
            📭 Nessuna prenotazione trovata.
        </div>
    <% } else { %>
    <div class="tabella-responsive">
    <table class="ordini-table">
        <thead>
            <tr>
                <th>#</th>
                <th>Ospite</th>
                <th>Città</th>
                <th>B&B</th>
                <th>Check-in</th>
                <th>Check-out</th>
                <th>Notti</th>
                <th>Adulti</th>
                <th>Bambini</th>
                <th>Camere</th>
                <th>Totale</th>
            </tr>
        </thead>
        <tbody>
        <% for (Prenotazione p : prenotazioni) {
            long notti = java.time.temporal.ChronoUnit.DAYS.between(p.getCheckin(), p.getCheckout());
        %>
            <tr>
                <td><strong>#<%= p.getId() %></strong></td>
                <td>
                    <%= p.getNomeUtente() != null ? p.getNomeUtente() : "" %>
                    <%= p.getCognomeUtente() != null ? p.getCognomeUtente() : "" %>
                </td>
                <td><span class="badge-citta"><%= p.getCitta() %></span></td>
                <td><strong><%= p.getNomeBnb() %></strong></td>
                <td><%= p.getCheckin() %></td>
                <td><%= p.getCheckout() %></td>
                <td><span class="notti-badge"><%= notti %> notti</span></td>
                <td><%= p.getAdulti() %></td>
                <td><%= p.getBambini() %></td>
                <td><%= p.getCamere() %></td>
                <td><span class="badge-prezzo">€<%= String.format("%.2f", p.getPrezzoTotale()) %></span></td>
            </tr>
        <% } %>
        </tbody>
    </table>
    </div>
    <% } %>
</main>
</body>
</html>
