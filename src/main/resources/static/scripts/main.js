// Funktion zum Senden von JSON-Daten über einen POST-Request
async function postJSON(url, data) {
    console.log(`Zugriff auf URL ${url}`);
    try {
        const token = localStorage.getItem('jwt');

        // Sendet einen POST-Request mit den angegebenen Daten
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(data)
        });

        // Überprüft, ob die Anfrage erfolgreich war
        if (response.ok) {
            // Gibt die JSON-Antwort zurück
            return await response.json();
        } else {
            // Wirft einen Fehler, wenn die Anfrage nicht erfolgreich war
            throw new Error('Anfrage fehlgeschlagen');
        }
    } catch (error) {
        // Behandelt Fehler beim Senden der Anfrage
        console.error("Fehler beim Abrufen der Daten", error);
        throw new Error('Netzwerkantwort war nicht in Ordnung.');
    }
}

// Funktion zum Laden von JSON-Daten über einen GET-Request
async function loadJSON(url) {
    console.log(`Zugriff auf URL ${url}`);
    try {
        const token = localStorage.getItem('jwt');

        if(token === null || token.length === 0) {
            throw new Error('User not authenticated');
        }

        // Sendet einen GET-Request
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            },
        });

        // Überprüft, ob die Anfrage erfolgreich war
        if (response.ok) {
            // Gibt die JSON-Antwort zurück
            return await response.json();
        } else {
            // Wirft einen Fehler, wenn die Anfrage nicht erfolgreich war
            throw new Error('Anfrage fehlgeschlagen');
        }
    } catch (error) {
        // Behandelt Fehler beim Laden der Daten
        console.error("Fehler beim Abrufen der Daten", error);
        throw new Error(error.message);
    }
}

// Funktion zum Löschen von Daten über einen DELETE-Request
async function deleteRequest(url) {
    console.log(`Zugriff auf URL ${url}`);
    try {
        const token = localStorage.getItem('jwt');
        // Sendet einen DELETE-Request mit den angegebenen Daten
        const response = await fetch(url, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            },
        });

        if (!response.ok) {
            // Wirft einen Fehler, wenn die Anfrage nicht erfolgreich war
            throw new Error('Anfrage fehlgeschlagen');
        }
    } catch (error) {
        // Behandelt Fehler beim Löschen der Daten
        console.error("Fehler beim Löschen der Daten", error);
        throw new Error('Netzwerkantwort war nicht in Ordnung.');
    }
}

// Funktion zum Erstellen eines Accounts
async function createAccount(data) {
    try {
        const token = localStorage.getItem('jwt');
        // Sendet einen POST-Request mit den angegebenen Daten
        const response = await fetch(`/api/users`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });

        // Überprüft, ob die Anfrage erfolgreich war
        if (!response.ok) {
            throw new Error('Anfrage fehlgeschlagen');
        }
    } catch (error) {
        // Behandelt Fehler beim Senden der Anfrage
        console.error("Fehler beim Abrufen der Daten", error);
        throw new Error('Netzwerkantwort war nicht in Ordnung.');
    }
}

async function postLogin(credentials) {
    try {
        const response = await fetch(`/api/users/login`, {
            method: 'POST',
            headers: {
                'Authorization': `Basic ${credentials}`
            },
        });

        // Überprüft, ob die Anfrage erfolgreich war
        if (response.ok) {
            // Gibt die JSON-Antwort zurück
            return await response.json();
        } else {
            // Wirft einen Fehler, wenn die Anfrage nicht erfolgreich war
            throw new Error('Anfrage fehlgeschlagen');
        }
    } catch (error) {
        // Behandelt Fehler beim Senden der Anfrage
        console.error("Fehler beim Abrufen der Daten", error);
        throw new Error('Netzwerkantwort war nicht in Ordnung.');
    }
}
