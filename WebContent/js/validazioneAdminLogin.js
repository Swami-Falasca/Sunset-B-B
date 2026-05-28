document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginAdminForm");

    form.addEventListener("submit", function (e) {
        const username = document.getElementById("username");
        const password = document.getElementById("password");
        let valido = true;

        // Reset messaggi errore
        document.getElementById("error-username").textContent = "";
        document.getElementById("error-password").textContent = "";

        // Controllo username (non vuoto)
        if (username.value.trim() === "") {
            document.getElementById("error-username").textContent = "Inserisci l'username.";
            valido = false;
        }

        // Controllo password (almeno 6 caratteri)
        if (password.value.length < 6) {
            document.getElementById("error-password").textContent = "La password deve contenere almeno 6 caratteri.";
            valido = false;
        }

        if (!valido) {
            e.preventDefault(); // Impedisce l'invio se ci sono errori
        }
    });
});
