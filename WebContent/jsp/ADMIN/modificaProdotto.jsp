<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <title>Modifica Prodotto</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/modificaProdotto.css">
</head>
<body>

    <h2>Modifica Prodotto</h2>

    <%
        it.unisa.model.Prodotto prodotto = (it.unisa.model.Prodotto) request.getAttribute("prodotto");
        if (prodotto == null) {
    %>
        <p>Errore: Prodotto non trovato.</p>
    <%
        } else {
    %>
    <form method="post" action="<%= request.getContextPath() %>/admin/modificaProdotto" enctype="multipart/form-data">

        <input type="hidden" name="id" value="<%= prodotto.getId() %>" />

        <label>Nome: 
            <input type="text" name="nome" value="<%= prodotto.getNome() %>" required>
        </label><br>

        <label>Descrizione: 
            <textarea name="descrizione" required><%= prodotto.getDescrizione() %></textarea>
        </label><br>

        <label>Prezzo €: 
            <input type="number" step="0.01" name="prezzo" value="<%= prodotto.getPrezzo() %>" required>
        </label><br>

        <label>Disponibilità: 
            <input type="number" name="disponibilita" value="<%= prodotto.getDisponibilita() %>" required>
        </label><br>

        <% 
            if (prodotto.getImmagine() != null && !prodotto.getImmagine().isEmpty()) {
                String[] immagini = prodotto.getImmagine().split(";");
                for (String img : immagini) { 
        %>
            <img src="<%= request.getContextPath() + "/" + img.trim() %>" class="prodotto-img" alt="immagine esistente" />
        <% 
                } 
            } 
        %>

        <label for="immagine">Nuova immagine:</label>
        <input type="file" id="immagine" name="immagine" multiple><br><br><br>

        <button type="submit">Salva Modifiche</button><br>
    </form>

    <a href="<%= request.getContextPath() %>/admin/prodotti">Torna indietro</a>

    <%
        }
    %>

</body>
</html>
