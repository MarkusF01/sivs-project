// Funktion zum Laden von Tagebucheinträgen für einen bestimmten Benutzer
async function loadDiaryEntries() {
    try {
        // Lade die Tagebucheinträge vom Server
        const entriesResponse = await loadJSON(`/api/users/current/diary-entries`);
        console.log(entriesResponse);

        document.getElementById('username-display').textContent = entriesResponse.username;

        // Holen Sie die HTML-Liste, in die die Einträge eingefügt werden sollen
        const entriesList = document.getElementById('diary-entries');

        // Iteriere durch die Einträge und erstelle jeweils eine Box
        entriesResponse.entries.forEach(entry => {
            addEntryBoxToDomList(entry, entriesList)
        });

    } catch (error) {
        // Fehlerbehandlung: Bei einem Fehler weiterleiten zur Startseite
        console.error('Error fetching data:', error);
        window.location.href = "/";
    }
}

// Funktion zum Löschen eines Tagebucheintrags
async function deleteDiaryEntry(event) {
    // Extrahiere die ID des zu löschenden Eintrags aus dem Button
    var button = event.target.id;
    var delete_button = document.getElementById(button);
    const parent = delete_button.getAttribute("parent");

    try {
        // Sende Anfrage zum Löschen an den Server
        const response = await deleteRequest(`/api/users/current/diary-entries/${parent}`);

        // Entferne die gelöschte Eintrag-Box aus dem DOM
        var entry = document.getElementById(parent);
        entry.remove();
    } catch (error) {
        // Fehlerbehandlung, falls das Löschen fehlschlägt
    }
}

// Funktion zum Hinzufügen eines neuen Tagebucheintrags
async function addDiaryEntry(event) {
    event.preventDefault();

    // Holen Sie sich Werte aus den Formularfeldern
    var title = document.getElementById("entry_title").value;
    var date = document.getElementById("entry_date").value;
    var content = document.getElementById("entry_content").value;

    // Überprüfen, ob alle Felder ausgefüllt sind
    if (title.trim() === '' || date.trim() === '' || content.trim() === '') {
        throw new Error('Tagebucheintrag unvollständig!');
    }

    // Formulardaten für den neuen Eintrag

    var formdata = {
        "entryTitle": title,
        "entryContent": content,
        "entryDate": date
    };

    try {
        // API-Endpunkt für das Hinzufügen eines Tagebucheintrags
        const apiUrl = `/api/users/current/diary-entries`;
        
        // Sende Anfrage zum Hinzufügen an den Server
        const response = await postJSON(apiUrl, formdata);

        // Setze das Formular zurück und lade die Seite neu
        document.getElementById('login-form').reset();
        document.getElementById('entry_date').value = formattedDate;
        const entriesList = document.getElementById('diary-entries');
        addEntryBoxToDomList(response, entriesList);
    } catch (error) {
        // Fehlerbehandlung, falls das Hinzufügen fehlschlägt
        throw new Error('Nicht veröffentlicht!');
    }
}

// Holen Sie das aktuelle Datum und formatieren Sie es als yyyy-mm-dd
var currentDate = new Date();
var formattedDate = currentDate.toISOString().slice(0, 10);

// Setze den Wert des Datumsfeldes auf das heutige Datum
document.getElementById('entry_date').value = formattedDate;

// Lade vorhandene Tagebucheinträge für den Benutzer
loadDiaryEntries();

// Füge dem "Posten"-Button einen Eventlistener hinzu, der die Funktion zum Hinzufügen aufruft
document.getElementById('post-button').addEventListener('click', async function (event) {
    addDiaryEntry(event);
});

document.getElementById('logout-button').addEventListener('click', async function (event) {
    window.location.href = "/";
});

function addEntryBoxToDomList(entry, entriesList) {
    const div_row = createDiaryBox(entry.id, entry.entryTitle, entry.entryDate, entry.entryContent);
    entriesList.appendChild(div_row);
}

// Funktion zum Erstellen einer Box für einen Tagebucheintrag
function createDiaryBox(id, title, date, content) {
    const div_row = document.createElement('div');
    div_row.setAttribute("id", id)
    div_row.className = "row"
    const div_col = document.createElement("div");
    div_col.className = "col";
    const entryDiv = document.createElement("div");
    entryDiv.className = "card mt-3";

    // Erstelle und füge Titel mit Löschbutton hinzu
    var titleElement = document.createElement("div");
    titleElement.className = "card-header d-flex justify-content-between align-items-center";
    titleElement.textContent = title;

    // Erstelle den Löschbutton
    var deleteButton = document.createElement("button");
    deleteButton.setAttribute("parent", id);
    deleteButton.setAttribute("id", "delete_" + id);
    deleteButton.className = "btn btn-outline-danger btn-sm";
    deleteButton.innerHTML = "&times;"; // HTML-Code für das 'x'-Zeichen
    deleteButton.addEventListener("click", function (event) {
        deleteDiaryEntry(event);
    });

    // Füge den Löschbutton zum TitelElement hinzu
    titleElement.appendChild(deleteButton);
    entryDiv.appendChild(titleElement);

    // Erstelle und füge das Datum hinzu
    var dateElement = document.createElement("div");
    dateElement.className = "card-body text-muted";
    dateElement.textContent = date;
    entryDiv.appendChild(dateElement);

    // Erstelle und füge den Textinhalt hinzu
    var contentElement = document.createElement("div");
    contentElement.className = "card-body";
    // TO TEXT CONTENT
    contentElement.innerHTML = content;
    entryDiv.appendChild(contentElement);

    div_col.appendChild(entryDiv);
    div_row.appendChild(div_col);
    return div_row;
}

