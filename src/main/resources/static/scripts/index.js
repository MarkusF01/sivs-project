// Holt das Formular-Element mit der ID 'login-form'
const login = document.getElementById('login-button');

// Fügt einen Eventlistener für das "submit"-Event dem Formular hinzu
login.addEventListener('click', async function (event) {
    event.preventDefault();
    console.log("Login Button pressed");
    let response = await fetch(`/api/users/authorize`);
    if(response.ok) {
        response = await response.json();
        // Leitet den Benutzer zur Homeseite weiter, wenn die Anmeldung erfolgreich war
        window.location.href = response.path;
    }
});
