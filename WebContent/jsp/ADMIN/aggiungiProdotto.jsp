<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <title>Aggiungi Alloggi</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/aggiungiProdotto.css">
</head>
<body>

<h2>Aggiungi Alloggi</h2>

<form method="post" action="<%= request.getContextPath() %>/admin/aggiungiProdotto" enctype="multipart/form-data">

    <label>Nome: 
        <input type="text" name="nome" required>
    </label><br>

    <label>Descrizione: 
        <textarea name="descrizione" required></textarea>
    </label><br>
   
    <label for="prezzo">Prezzo €:
        <input type="number" id="prezzo" name="prezzo" step="0.01" required>
    </label><br>

    <label>Disponibilità: 
        <input type="number" name="disponibilita" required>
    </label><br>

    <label>Immagine: 
        <input type="file" name="immagine" accept="image/*" multiple>
    </label><br>
    <div id="filePreview"></div>
	
    <button type="submit">Salva</button>
</form>

<a href="<%= request.getContextPath() %>/admin/prodotti">Torna indietro</a>



</body>
</html>
