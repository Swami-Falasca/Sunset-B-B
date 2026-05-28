function validateForm() {
    const città = document.getElementById("città").value.trim();
    if (città === "") {
        alert("Inserisci una città prima di cercare.");
        return false;
    }
    return true;
}

function toggleDropdown() {
    const el = document.getElementById("chi-dropdown");
    el.style.display = el.style.display === "block" ? "none" : "block";
}

function increment(id) {
    const el = document.getElementById(id);
    const input = document.getElementById(id + "Input");
    const newVal = parseInt(el.textContent) + 1;
    el.textContent = newVal;
    input.value = newVal;
}

function decrement(id) {
    const el = document.getElementById(id);
    const input = document.getElementById(id + "Input");
    let val = parseInt(el.textContent);
    if (val > 0) {
        el.textContent = val - 1;
        input.value = val - 1;
    }
}

document.addEventListener("click", function (event) {
    const chiDropdown = document.getElementById("chi-dropdown");
    const personeButton = document.querySelector(".btn-chi");
    const textInput = document.querySelector('input[type="text"]');
    const dateInputs = document.querySelectorAll('input[type="date"]');

    const isClickInsidePersone = chiDropdown.contains(event.target) || personeButton.contains(event.target);
    const isClickInsideText = textInput && textInput.contains(event.target);
    const isClickInsideDate = [...dateInputs].some(input => input.contains(event.target));

    if (!isClickInsidePersone) chiDropdown.style.display = "none";
    if (!isClickInsideText) textInput.blur();
    if (!isClickInsideDate) dateInputs.forEach(input => input.blur());
});

function toggleAccountDropdown() {
    const dropdown = document.getElementById("dropdown-menu-account");
    dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
}

document.addEventListener("click", function (event) {
    const dropdown = document.getElementById("dropdown-menu-account");
    const icon = document.querySelector(".account-icon");
    const avatarNav = document.querySelector(".avatar-iniziali-nav");
    const trigger = icon || avatarNav;
    if (dropdown && trigger && !dropdown.contains(event.target) && !trigger.contains(event.target)) {
        dropdown.style.display = "none";
    }
});

function toggleLangMenu() {
    const menu = document.getElementById("langMenu");
    menu.style.display = menu.style.display === "block" ? "none" : "block";
}

function changeLang(code, label) {
    const FLAGS_PATH = './Bandiere_header/';
    document.getElementById("selectedFlag").src = FLAGS_PATH + code + '.png';
    document.getElementById("selectedLang").textContent = label;
    document.getElementById("langMenu").style.display = "none";
    localStorage.setItem('selectedLanguage', JSON.stringify({ code, label }));
}

document.addEventListener("DOMContentLoaded", function () {
    const savedLang = JSON.parse(localStorage.getItem('selectedLanguage'));
    if (savedLang) {
        document.getElementById("selectedFlag").src = './Bandiere_header/' + savedLang.code + '.png';
        document.getElementById("selectedLang").textContent = savedLang.label;
    }

    const now = new Date();
    const currentYear = now.getFullYear();
    const minSelectableDate = new Date(currentYear, 0, 1);

    flatpickr("#checkin", {
        dateFormat: "d/m/Y",
        minDate: minSelectableDate,
        locale: "it",
        allowInput: true
    });

    flatpickr("#checkout", {
        dateFormat: "d/m/Y",
        minDate: minSelectableDate,
        locale: "it",
        allowInput: true
    });

    document.getElementById("home").onclick = function() {
        window.location.href = "#home";
    };

    const searchInput = document.getElementById("città");
    const suggestionsList = document.createElement('div');
    suggestionsList.className = 'suggestions-list';
    searchInput.parentNode.appendChild(suggestionsList);

    let cities = [];

    fetch("citta.json")
        .then(response => response.json())
        .then(data => { cities = data; });

    searchInput.addEventListener('input', (e) => {
        const inputValue = e.target.value.toLowerCase().trim();
        suggestionsList.innerHTML = '';

        if (inputValue.length > 0) {
            const filteredCities = cities.filter(city =>
                city.toLowerCase().startsWith(inputValue)
            );
            suggestionsList.style.display = filteredCities.length ? 'block' : 'none';

            const fragment = document.createDocumentFragment();
            filteredCities.forEach(city => {
                const div = document.createElement('div');
                div.className = 'suggestion-item';
                div.textContent = city;
                div.addEventListener('click', () => {
                    searchInput.value = city;
                    suggestionsList.style.display = 'none';
                });
                fragment.appendChild(div);
            });
            suggestionsList.appendChild(fragment);
        } else {
            suggestionsList.style.display = 'none';
        }
    });

    document.addEventListener('click', (e) => {
        if (!searchInput.contains(e.target) && !suggestionsList.contains(e.target)) {
            suggestionsList.style.display = 'none';
        }
    });
});