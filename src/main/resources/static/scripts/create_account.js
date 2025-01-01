
var form = document.getElementById("createAccountForm");

form.addEventListener("submit", async function (event) {
    // Verhindern Sie das Standardverhalten des Formulars
    event.preventDefault();

    // Validiert die Formularfelder
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var secretQuestion = document.getElementById("secret_question").value;
    var secretAnswer = document.getElementById("secret_answer").value;

    // Überprüfen Sie, ob alle Formularfelder ausgefüllt sind
    if (username && password && secretQuestion && secretAnswer) {
        // Erstellen Sie ein JSON-Objekt mit den Formulardaten
        var formData = {
            username: username,
            password: password,
            secretQuestion: secretQuestion,
            secretAnswer: secretAnswer
        };

        // Sendet die Daten an den API-Endpunkt für Account-Erstellung
        try {
            var response = await createAccount(formData);

            // Weiterleitung zur Startseite nach erfolgreicher Anmeldung
            window.location.href = "/";
        } catch (error) {
            alert("Netzwerkfehler");
        }
    } else {
        // Behandlung von Validierungsfehlern (z.B. Anzeige einer Warnung)
        alert("Bitte füllen Sie alle Formularfelder aus.");
    }
});
