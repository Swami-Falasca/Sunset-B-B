setTimeout(function() {
    var msg = document.getElementById("messaggio-successo");
    if (msg) msg.style.display = "none";

    if (history.replaceState) {
        const url = window.location.protocol + "//" + window.location.host + window.location.pathname;
        history.replaceState(null, "", url);
    }
}, 5000);