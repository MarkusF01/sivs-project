// Holt das Formular-Element mit der ID 'login-form'
const form = document.getElementById('login-form');

// Fügt einen Eventlistener für das "submit"-Event dem Formular hinzu
form.addEventListener('submit', async function (event) {
    // Verhindert das Standardverhalten des Formulars (kein automatisches Neuladen der Seite)
    event.preventDefault();

    // Gibt eine Konsolennachricht aus, wenn der Submit-Button gedrückt wurde
    console.log("Submit Button pressed");

    // Holt die Werte von Benutzername und Passwort aus den entsprechenden Eingabefeldern
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Überprüft, ob Benutzername oder Passwort Leerzeichen enthalten; beendet die Funktion in diesem Fall
    if (username.trim() === '' || password.trim() === '') {
        return;
    }

    // Erstellt ein Objekt mit den Anmeldedaten
    const loginRequest = {
        password: password
    }

    try {
        // Sendet die Anmeldedaten an den Server und wartet auf die Antwort
        const response = await postJSON(`/api/users/${username}/authorize`, loginRequest);

        // Leitet den Benutzer zur Homeseite weiter, wenn die Anmeldung erfolgreich war
        window.location.href = response.path;
    } catch (error) {
        // Tritt ein Fehler während der Anmeldung auf

        // ID für das Warnungselement erstellen
        const alertId = 'login-alert';

        // Holt das Element für das Passwortfeld
        const password_div = document.getElementById('password_box');

        // Entfernt vorhandene Warnungen, falls vorhanden
        const existingAlert = document.getElementById(alertId);
        if (existingAlert) {
            existingAlert.remove();
        }

        // Erstellt eine Fehlermeldung
        const alert = "Benutzeranmeldung nicht möglich. Passwort und/oder Benutzername falsch!";

        // Erstellt ein Warnungselement und fügt es zum Passwortfeld hinzu
        const alertBox = document.createElement('div');
        alertBox.innerHTML = alert;
        alertBox.id = alertId;
        alertBox.classList.add("alert", "alert-danger", "border-danger");
        password_div.appendChild(alertBox);
    }
});
