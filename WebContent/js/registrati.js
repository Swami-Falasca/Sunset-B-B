document.addEventListener("DOMContentLoaded", function () {

    // === VALIDAZIONI ESISTENTI (maxlength, maiuscolo) ===

    const documentoSelect = document.getElementById("documento");
    const numeroDocumentoInput = document.getElementById("numeroDocumento");
    const nomeInput = document.getElementById("nome");
    const cognomeInput = document.getElementById("cognome");


    function aggiornaMaxLength() {
        if (!documentoSelect || !numeroDocumentoInput) return;

        const documento = documentoSelect.value;
        let maxLength = 0;

        switch (documento) {
            case "carta_identita":
                maxLength = 9;
                break;

            case "passaporto":
                maxLength = 8;
                break;

            case "patente":
                maxLength = 10;
                break;
        }

        numeroDocumentoInput.setAttribute("maxlength", maxLength);
    }


    if (documentoSelect && numeroDocumentoInput) {

        documentoSelect.addEventListener("change", aggiornaMaxLength);

        numeroDocumentoInput.addEventListener("input", function () {
            const maxLength = parseInt(numeroDocumentoInput.getAttribute("maxlength"));

            if (numeroDocumentoInput.value.length > maxLength) {
                numeroDocumentoInput.value =
                    numeroDocumentoInput.value.slice(0, maxLength);
            }
        });
    }


    function convertiInMaiuscolo(inputField) {
        if (!inputField) return;

        inputField.addEventListener("input", function () {
            inputField.value = inputField.value.toUpperCase();
        });
    }


    convertiInMaiuscolo(numeroDocumentoInput);
    convertiInMaiuscolo(nomeInput);
    convertiInMaiuscolo(cognomeInput);



    // =====================================================
    // === NUOVE VALIDAZIONI FORM REGISTRAZIONE ============
    // =====================================================


    const form = document.getElementById("registraForm");

    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    const dataInput = document.getElementById("data_nascita");


    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // almeno 8 caratteri, una lettera e un numero
    const regexPassword = /^(?=.*[A-Za-z])(?=.*\d).{8,}$/;



    function mostraErrore(id, messaggio) {
        const elemento = document.getElementById(id);

        if (elemento) {
            elemento.textContent = messaggio;
        }
    }


    function pulisciErrore(id) {
        const elemento = document.getElementById(id);

        if (elemento) {
            elemento.textContent = "";
        }
    }



    // === CONTROLLO EMAIL ===

    function validaEmail() {

        const valore = emailInput.value.trim();


        if (valore === "") {

            mostraErrore(
                "errore-email",
                "L'email è obbligatoria."
            );

            return false;
        }


        if (!regexEmail.test(valore)) {

            mostraErrore(
                "errore-email",
                "Formato email non valido."
            );

            return false;
        }


        pulisciErrore("errore-email");

        return true;
    }




    // === CONTROLLO PASSWORD ===

    function validaPassword() {

        const valore = passwordInput.value;


        if (valore === "") {

            mostraErrore(
                "errore-password",
                "La password è obbligatoria."
            );

            return false;
        }


        if (!regexPassword.test(valore)) {

            mostraErrore(
                "errore-password",
                "Minimo 8 caratteri, almeno una lettera e un numero."
            );

            return false;
        }


        pulisciErrore("errore-password");

        return true;
    }





    // === CONTROLLO DATA DI NASCITA ===

    function validaData() {

        const valore = dataInput.value;


        if (!valore) {

            mostraErrore(
                "errore-data",
                "La data di nascita è obbligatoria."
            );

            return false;
        }


        const nascita = new Date(valore);
        const oggi = new Date();


        let eta = oggi.getFullYear() - nascita.getFullYear();

        const mese =
            oggi.getMonth() - nascita.getMonth();


        if (
            mese < 0 ||
            (mese === 0 && oggi.getDate() < nascita.getDate())
        ) {
            eta--;
        }


        if (eta < 18) {

            mostraErrore(
                "errore-data",
                "Devi avere almeno 18 anni."
            );

            return false;
        }


        pulisciErrore("errore-data");

        return true;
    }





    // Eventi controllo campi

    if (emailInput) {
        emailInput.addEventListener("change", validaEmail);
    }

    if (passwordInput) {
        passwordInput.addEventListener("change", validaPassword);
    }

    if (dataInput) {
        dataInput.addEventListener("change", validaData);
    }




    // Controllo finale prima dell'invio

    if (form) {

        form.addEventListener("submit", function (event) {

            const emailOk = validaEmail();
            const passwordOk = validaPassword();
            const dataOk = validaData();


            if (!emailOk || !passwordOk || !dataOk) {

                event.preventDefault();

            }

        });

    }

});
