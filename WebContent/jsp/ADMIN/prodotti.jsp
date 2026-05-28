<%@page import="it.unisa.model.Prodotto"%> 
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <title>Catalogo Alloggi</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
   
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/catalogo.css">
</head>
<body>

    <h2>Catalogo Alloggi</h2>
    <a class="btn add-button" href="<%= request.getContextPath() %>/jsp/ADMIN/aggiungiProdotto.jsp"> Aggiungi nuovo alloggio </a>

	<div class="tabella-responsive">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Immagine</th>
            <th>Nome</th>
            <th>Descrizione</th>
            <th>Prezzo</th>
            <th>Disponibilità</th>
            <th>Azioni</th>
        </tr>

        <%
            List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
            if (prodotti != null) {
                for (Prodotto p : prodotti) {
                    if (!p.isEliminato()) {
        %>
   <tr>
    <td><%= p.getId() %></td>
    <td>
        <% if (p.getImmagine() != null && !p.getImmagine().isEmpty()) {
            String[] immagini = p.getImmagine().split(";");
            for (String img : immagini) { %>
                <img src="<%= request.getContextPath() + "/" + img.trim() %>" 
                     class="img-catalogo"
                     onclick="apriLightbox('<%= request.getContextPath() + "/" + img.trim() %>')" 
                     alt="immagine prodotto" />
        <%  } 
        } else { %>
            Nessuna immagine
        <% } %>
    </td> 

    <td><%= p.getNome() %></td>
    <td><%= p.getDescrizione() %></td>
    <td><%= String.format("€%.2f", p.getPrezzo()) %></td>
    <td><%= p.getDisponibilita() %></td>
    <td class="actions">
        <a href="<%= request.getContextPath() %>/admin/modificaProdotto?id=<%= p.getId() %>" title="Modifica">
            <i class="fas fa-pencil-alt action-icon"></i>
        </a>
        <a href="<%= request.getContextPath() %>/admin/eliminaProdotto?id=<%= p.getId() %>" onclick="return confirm('Sei sicuro di voler eliminare questo prodotto?');" title="Elimina">
            <i class="fas fa-trash action-icon"></i>
        </a>
    </td>
</tr>

        <% 
                    }
                }
            }
        %>
    </table>
</div>
    <a class="btn return-button" href="<%= request.getContextPath() %>/jsp/ADMIN/Dashboard.jsp">
    <i class="fas fa-arrow-left"></i> Torna alla Dashboard </a>

</body>
</html>
