# Ü1 Guidelines und Action Allowlist

## Lernziel

Sie halten Projektregeln in `AGENTS.md` im Projektwurzelverzeichnis fest, damit Junie sie bei jeder Aufgabe anwendet, ohne dass Sie sie im Prompt wiederholen. Sie legen über die Action Allowlist fest, was Junie ohne Rückfrage ausführen darf, und prüfen, ob beides greift.

## Ausgangslage

Paket `de.devadze.junie.ue1_guidelines` enthält eine kleine Aufgabenverwaltung (`Task`, `TaskService`). Der Code ist gewachsen:

- Tabs und Leerzeichen gemischt
- uneinheitliche Namen (`list_all`, `find_by_id`, `Toggle`, Feld `TASKS`, Parameter `ID`)
- `null` als Rückgabe für „nicht gefunden“
- keine Javadoc, keine Tests

Im Projekt gibt es noch keine Guidelines-Datei.

## Aufgaben

**A. `AGENTS.md` schreiben.** Legen Sie im Projektwurzelverzeichnis die Datei `AGENTS.md` an. Die Datei soll enthalten:

1. Projektkontext in zwei Sätzen
2. Java-Version und Build-Werkzeug (JDK 21, Maven, JUnit 5, keine weiteren Bibliotheken)
3. Befehle: Build, alle Tests, Tests der Übung 3 (`mvn -q test`, `mvn -q test -Dgroups=ue3`)
4. Konventionen: Namen (camelCase für Methoden und Felder, keine Unterstriche), Einrückung mit 4 Leerzeichen, Javadoc für öffentliche Methoden, Testklasse `<Klasse>Test` im gleichen Paket unter `src/test/java`
5. Do: was Junie immer tun soll (zum Beispiel Test für neue öffentliche Methoden, vor Änderungen an mehr als einer Datei einen Plan zeigen)
6. Don't: was Junie nie tun soll (zum Beispiel neue Abhängigkeiten, `pom.xml` ändern, Testerwartungen anpassen, damit Tests grün werden, `.env` lesen)
7. Definition of Done: wann eine Aufgabe als erledigt gilt

Junie im AI Chat liest laut Doku die `AGENTS.md` im Projektwurzelverzeichnis. Das Junie-Plugin bevorzugt zusätzlich `.junie/AGENTS.md`; im Kurs bleiben wir beim Wurzelverzeichnis, weil Codex und GitHub Copilot dieselbe Datei lesen.

**B. Action Allowlist anlegen.** Öffnen Sie **Settings | Tools | Junie | Action Allowlist** und legen Sie an:

1. eine Regel vom Typ **RunTest** (Tests über die IDE starten)
2. eine Regel vom Typ **Build** (Projekt über die IDE bauen)
3. eine Regel vom Typ **Terminal** mit einem regulären Ausdruck, der genau `mvn test`, `mvn -q test` und `mvn -q test -Dgroups=ue3` erlaubt, aber keine Verkettung mit `;`, `&`, `|`, `<`, `>` oder `$`

Prüfen Sie Ihren Ausdruck mit „Check Command“ gegen diese Befehle: `mvn -q test` (soll passen), `mvn -q test && rm -rf src` (soll nicht passen), `mvn deploy` (soll nicht passen).

**C. Kleine Änderung beauftragen.** Geben Sie Junie diesen Auftrag, ohne Konventionen im Prompt zu nennen:

```text
Ergänzen Sie in TaskService eine Methode, die die Anzahl der offenen (nicht erledigten)
Aufgaben liefert.
```

**D. Prüfen, ob die Regeln greifen.**

- Hält sich Junie an Ihre Namenskonvention, schreibt Javadoc und einen Test?
- Führt Junie die Tests ohne Rückfrage aus (Allowlist)? Fragt Junie bei anderen Befehlen weiterhin?
- Hat Junie ungefragt bestehenden Code umbenannt oder umformatiert? Ist das nach Ihrer `AGENTS.md` erlaubt?

Schärfen Sie `AGENTS.md` nach, wenn eine Regel nicht befolgt wurde, und wiederholen Sie den Auftrag in einem neuen Chat.

## Hinweis zum Junie-Prompt

Der Auftrag in C ist absichtlich knapp. Sie testen damit die Guidelines, nicht den Prompt. Schreiben Sie Regeln in `AGENTS.md` als prüfbare Anweisungen („Jede neue öffentliche Methode bekommt einen JUnit-5-Test“), nicht als Wunsch („sauberer Code“).

## Reflexionsfragen

- Welche Regel hat Junie beim ersten Versuch nicht befolgt? Wie haben Sie die Formulierung geändert?
- Welche Regel gehört in `AGENTS.md`, welche in die Allowlist, welche in den Review?
- Warum ist eine Terminal-Regel wie `mvn .*` gefährlicher als Ihr Ausdruck?
