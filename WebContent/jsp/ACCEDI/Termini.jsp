<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Informativa sulla Privacy – Sunset B&B</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Termini.css">
   <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css"> 
  <link rel="preconnect" href="https://fonts.googleapis.com">
 <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
 <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
</head>
<body>

<%@ include file="/jsp/Header.jsp" %>
<section class="container">
    <h2>Informativa sulla Privacy – Sunset B&B</h2>
    <p>Ultimo aggiornamento: 21/05/2025</p>
    <p>Presso Sunset B&B, la privacy dei nostri ospiti è una priorità assoluta. Raccogliamo e trattiamo i dati personali dei nostri clienti in conformità al Regolamento (UE) 2016/679 (GDPR) e ad altre normative vigenti in materia di protezione dei dati. La presente Informativa ha lo scopo di illustrare, con chiarezza e trasparenza, come raccogliamo, utilizziamo, conserviamo e proteggiamo i tuoi dati personali.</p>

    <h2>1. Dati Personali Raccolti</h2>
    <h3>1.1 Dati necessari per il soggiorno</h3>
    <ul>
        <li>Dati anagrafici: nome, cognome, data e luogo di nascita.</li>
        <li>Dati di contatto: numero di telefono, indirizzo email.</li>
        <li>Dati relativi al soggiorno: date di arrivo e partenza, camera assegnata, numero di ospiti, preferenze specifiche.</li>
        <li>Dati di pagamento: informazioni parziali su carta di credito, IBAN, oppure conferma dell’avvenuto pagamento tramite servizi terzi.</li>
    </ul>
    <h3>1.2 Dati forniti volontariamente</h3>
    <ul>
        <li>Intolleranze alimentari o esigenze specifiche (es. colazione senza glutine).</li>
        <li>Recensioni o comunicazioni tramite email, modulo di contatto o app di messaggistica (es. WhatsApp).</li>
    </ul>

    <h2>2. Modalità del Trattamento</h2>
    <p>Il trattamento dei dati personali da parte di Sunset B&B avviene nel pieno rispetto dei principi di liceità, correttezza, trasparenza, minimizzazione, integrità e riservatezza, così come stabiliti dall’art. 5 del GDPR.</p>
    <p>Il trattamento può avvenire mediante strumenti sia manuali che elettronici, idonei a garantire la sicurezza e la riservatezza dei dati stessi.</p>

    <h2>3. Conservazione dei Dati</h2>
    <ul>
        <li><strong>5 anni</strong> per dati identificativi e di soggiorno, come richiesto dalla normativa fiscale.</li>
        <li>Fino alla revoca del consenso per dati usati a fini di marketing o promozione (es. email di offerte future).</li>
        <li><strong>24 ore</strong> per i dati richiesti da autorità di pubblica sicurezza (es. registrazione ospiti).</li>
    </ul>

    <h2>4. Comunicazione a Terzi</h2>
    <p>I tuoi dati non verranno venduti o diffusi a terzi.</p>
    <p>Potrebbero però essere comunicati alle autorità (es. Polizia, Guardia di Finanza), solo se richiesto per legge per finalità quali prevenzione di reati, verifiche fiscali o obblighi normativi.</p>

    <h2>5. Trasferimento dei Dati all’Estero</h2>
    <p>I dati personali non saranno trasferiti al di fuori dello Spazio Economico Europeo (SEE).</p>

    <h2>6. Diritti dell’Interessato</h2>
    <p>In qualunque momento, puoi esercitare i seguenti diritti:</p>
    <ul>
        <li><strong>Accesso</strong>: conoscere quali dati trattiamo.</li>
        <li><strong>Rettifica</strong>: aggiornare o correggere i tuoi dati.</li>
        <li><strong>Cancellazione</strong>: richiederne la rimozione quando non più necessari.</li>
    </ul>
    <p>Per esercitare i tuoi diritti, puoi contattare il Titolare del trattamento all’indirizzo email o postale fornito. In caso di mancata risposta o insoddisfazione, hai diritto
di rivolgerti all’Autorità Garante per la Protezione dei Dati Personali.</p>

    <h2>7. Cookie e Dati di Navigazione</h2>
    <p>Il sito di Sunset B&B può usare cookie, cioè piccoli file che raccolgono informazioni sulla tua navigazione per:</p>
    <ul>
        <li>Migliorare l’esperienza d’uso;</li>
        <li>Ricordare preferenze o personalizzazioni dell’utente.</li>
    </ul>
    <p>L’utente può gestire le preferenze relative ai cookie tramite le impostazioni del proprio browser. Disabilitare i cookie può limitare alcune funzionalità del sito.</p>

    <h2>8. Modifiche alla presente Informativa</h2>
    <p>Sunset B&B può modificare questa informativa in futuro (ad es. per aggiornarsi alle leggi o cambiare i processi interni). Se ci sono modifiche importanti, ti informeremo tramite:</p>
    <ul>
        <li>Il sito web;</li>
        <li>Oppure email, se hai fornito il consenso.</li>
    </ul>

    <a href="<%= request.getContextPath() %>/jsp/HOME/Registrati.jsp">Torna alla registrazione</a>
</section>
        <%@ include file="/jsp/footer.jsp" %>
</body>
</html>