# Tagebuch-Applikation mit Authentifizierungsmechanismen

Dieses Projekt ist eine umgeschriebene Version einer ursprünglichen Python-Flask-Tagebuch-Applikation, die in die Java-Spring-Umgebung migriert wurde. Es dient als Demonstration verschiedener Authentifizierungsmechanismen, die in Spring Security umgesetzt wurden.

## Projektumgebung

- **Framework:** Spring 3.4
- **Programmiersprache:** Java 21
- **Frontend:** Thymeleaf für die Darstellung der Benutzeroberfläche

## Branches

Das Repository enthält vier Branches, die verschiedene Stände der Applikation repräsentieren:

### Main Branch
Der `main`-Branch enthält die Applikation ohne jegliche Authentifizierungsmechanismen. Dieser Stand zeigt die Grundstruktur nach der Migration in die Java-Spring-Umgebung. Die API-Endpunkte sind offen und es gibt keine Zugangsbeschränkungen.

### auth/basic
Der Branch `auth/basic` implementiert die HTTP-Basic-Authentifizierung. Hier werden Benutzername und Passwort bei jeder Anfrage übermittelt und durch Spring Security überprüft. Dies wurde über die `.httpBasic()`-Methode in der Security-Konfiguration umgesetzt.

### auth/jwt
Der Branch `auth/jwt` zeigt die token-basierte Authentifizierung mit JSON Web Tokens (JWT). Tokens werden nach erfolgreicher Anmeldung generiert und bei jeder weiteren Anfrage im HTTP-Header übermittelt. Die Validierung erfolgt serverseitig mithilfe eines JwtDecoder.

### auth/session
Der Branch `auth/session` implementiert die session-basierte Authentifizierung. Sitzungsdaten werden serverseitig gespeichert und die Session-IDs in Cookies an den Client übermittelt. Für die Speicherung der Sitzungen wurde Redis integriert, um eine skalierbare Lösung zu ermöglichen.
