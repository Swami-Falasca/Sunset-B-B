document.addEventListener("DOMContentLoaded", function () {
    var documentoSelect = document.getElementById("documento");
    var numeroDocumentoInput = document.getElementById("numeroDocumento");
    var nomeInput = document.getElementById("nome");
    var cognomeInput = document.getElementById("cognome");

    function aggiornaMaxLength() {
        var documento = documentoSelect.value;
        let maxLength = 0;
        switch (documento) {
            case "carta_identita": maxLength = 9; break;
            case "passaporto": maxLength = 8; break;
            case "patente": maxLength = 10; break;
        }
        numeroDocumentoInput.setAttribute("maxlength", maxLength);
    }

    documentoSelect.addEventListener("change", aggiornaMaxLength);

    numeroDocumentoInput.addEventListener("input", function () {
        var maxLength = parseInt(numeroDocumentoInput.getAttribute("maxlength"));
        if (numeroDocumentoInput.value.length > maxLength) {
            numeroDocumentoInput.value = numeroDocumentoInput.value.slice(0, maxLength);
        }
    });

    function convertiInMaiuscolo(inputField) {
        inputField.addEventListener("input", function () {
            inputField.value = inputField.value.toUpperCase();
        });
    }

    convertiInMaiuscolo(numeroDocumentoInput);
    convertiInMaiuscolo(nomeInput);
    convertiInMaiuscolo(cognomeInput);
});