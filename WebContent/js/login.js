document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("loginForm");
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    const erroreEmail = document.getElementById("errore-email");
    const errorePassword = document.getElementById("errore-password");

    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    function validaEmail() {
        const val = emailInput.value.trim();
        if (val === "") {
            erroreEmail.textContent = "L'email è obbligatoria.";
            return false;
        }
        if (!regexEmail.test(val)) {
            erroreEmail.textContent = "Formato email non valido.";
            return false;
        }
        erroreEmail.textContent = "";
        return true;
    }

    function validaPassword() {
        if (passwordInput.value.trim() === "") {
            errorePassword.textContent = "La password è obbligatoria.";
            return false;
        }
        errorePassword.textContent = "";
        return true;
    }

    // Evento change: valida appena l'utente lascia il campo
    emailInput.addEventListener("change", validaEmail);
    passwordInput.addEventListener("change", validaPassword);

    // Evento submit: blocca l'invio se qualcosa non va
    form.addEventListener("submit", function (e) {
        const emailOk = validaEmail();
        const passwordOk = validaPassword();
        if (!emailOk || !passwordOk) {
            e.preventDefault();
        }
    });
});
