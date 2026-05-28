<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrati</title>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
	
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Registrati.css">
   <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
    
</head>
<body>
<!-- INCLUSIONE HEADER -->
    <%@ include file="/jsp/Header.jsp" %>
<main>

<div class="form-container">
    <div class="container">
        <h2>Crea un account</h2>
        <form action="<%= request.getContextPath() %>/HOME/GestioneFormRegistrati" method="post" enctype="multipart/form-data">

            <div class="form-group">
                <label for="nome">Nome:</label>
                <input type="text" id="nome" style="outline:none" name="nome" required placeholder="Inserisci il tuo nome">
            </div>

            <div class="form-group">
                <label for="cognome">Cognome:</label>
                <input type="text" id="cognome"style="outline:none" name="cognome" required placeholder="Inserisci il tuo cognome">
            </div>

            <div class="form-group">
                <label for="data_nascita">Data di nascita:</label>
                <input type="date" id="data_nascita"style="outline:none" name="data_nascita" required>
            </div>

            <div class="form-group">
                <label for="documento">Documento di riconoscimento:</label>
                <select id="documento" name="tipo_documento">

                    <option value="">Seleziona un documento</option>
                    <option value="carta_identita">Carta d'identità</option>
                    <option value="passaporto">Passaporto</option>
                    <option value="patente">Patente</option>
                </select>
            </div>

            <div class="form-group">
                <label for="allegato">Allega un file (PDF):</label>
                <input type="file" id="file_pdf" name="file_pdf" accept=".pdf" required>

            </div>

            <div class="form-group">
                <label for="numeroDocumento" id="codiceDocumentoDiv">Numero documento:</label>
                <input type="text" id="numeroDocumento" style="outline:none" name="numero_documento" placeholder="Inserisci il numero del documento">
            </div>

            <div class="form-group">
                <label for="residenza">Residenza:</label>
                <input type="text" id="residenza" style="outline:none"name="residenza" required placeholder="Inserisci il tuo indirizzo">
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" style="outline:none" name="email" required placeholder="Inserisci la tua email">
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" style="outline:none" name="password" required placeholder="Crea una password">
            </div>

            <div class="form-group terms-container">
                <input type="checkbox" id="terms" required>
                <label for="terms">Registrandoti, accetti i nostri <a href="<%=request.getContextPath()%>/jsp/ACCEDI/Termini.jsp">Termini e Condizioni</a></label>
            </div>

            <button type="submit">Registrati</button>
        </form>
    </div>
</div>
</main>

  <%@ include file="/jsp/footer.jsp" %>
</div>
<script src="<%= request.getContextPath() %>/js/registrati.js"></script>

</body>
</html>