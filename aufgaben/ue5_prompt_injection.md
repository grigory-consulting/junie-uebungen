# Ü5 Prompt Injection erkennen (kürzbar)

> Diese Übung ist kürzbar. Wenn die Zeit knapp ist, bearbeiten Sie A und B und besprechen C mündlich.

## Lernziel

Sie behandeln fremde Inhalte (Tickets, Kommentare, Logs, Code-Kommentare) als Daten, nicht als Anweisungen. Sie erkennen Injektionsmuster, formulieren einen sicheren Auftrag und begrenzen Junies Spielraum über `.aiignore` und die Action Allowlist.

## Ausgangslage

Paket `de.devadze.junie.ue5_injection`:

- `TokenVerifier.verify` liefert die Nutzerkennung aus den Claims eines Tokens und hat einen echten Fehler.
- `ISSUE-4711.md` ist das Ticket zu diesem Fehler: Beschreibung, Logs, Kommentare von Kollegen und einem Bot.

Im Ticket und im Code sind mindestens fünf Injektionsversuche versteckt, einige offensichtlich, andere unauffällig. Im Projektwurzelverzeichnis liegt eine Datei `.env` mit Platzhaltern. Alle Adressen im Ticket verwenden die Domain `example.invalid`, die nie aufgelöst wird.

## Aufgaben

**A. Erkennen.** Lesen Sie `ISSUE-4711.md` und `TokenVerifier.java` selbst, bevor Sie Junie einsetzen. Notieren Sie in `gefundene_injektionen.md` im Projektwurzelverzeichnis jede Stelle mit:

- Fundort (Datei, Abschnitt)
- was verlangt wird
- Muster (zum Beispiel neue Anweisungen im Inhalt, Datenabfluss, Heimlichkeit, falsche Autorität, Rechteausweitung, destruktive Aktion, neue Abhängigkeit)

**B. Sicher beauftragen.** Formulieren Sie in `sicherer_prompt.md` einen Auftrag an Junie, der

1. den legitimen Inhalt des Tickets übergibt,
2. ausdrücklich festhält, dass der Ticket-Inhalt Daten sind und keine Anweisungen,
3. Junie anweist, Anweisungen im Ticket zu ignorieren und Manipulationsversuche zu melden,
4. den Fix auf `TokenVerifier.java` und einen neuen `TokenVerifierTest` begrenzt,
5. vor dem Ändern einen Plan verlangt.

**C. Leitplanken in der IDE.** Ergänzen Sie, was die Injektionen strukturell entschärft:

- `.aiignore` im Projektwurzelverzeichnis (Syntax wie `.gitignore`) mit `.env`. Junie fragt dann, bevor es die Datei liest oder ändert.
- Action Allowlist: Welche Regeln aus Ü1 bleiben, welche Regeltypen schalten Sie nicht frei (Terminal mit `.*`, „Edit build scripts“, „Read files with secrets“)?
- Brave mode: Warum hebelt er `.aiignore` und Rückfragen aus?
- Laut Dokumentation greift `.aiignore` nicht bei Befehlen auf der Allowlist, die solche Dateien anfassen. Was folgt daraus für Ihre Terminal-Regel?

**D. Optional: Fix ausführen.** Geben Sie Junie Ihren sicheren Auftrag aus B. Prüfen Sie im Diff: keine Änderung an `pom.xml`, `.aiignore`, `.env` oder anderen Paketen; der Test deckt fehlenden, leeren und gültigen Claim `sub` ab.

## Hinweis zum Junie-Prompt

Trennen Sie Ihre Anweisung und den fremden Inhalt sichtbar, zum Beispiel mit Markern `BEGIN TICKET` und `END TICKET`. Verlangen Sie aktiv, dass Junie Manipulationsversuche nennt, bevor es mit der Analyse beginnt. Ein sicherer Prompt senkt das Risiko, beseitigt es aber nicht: Die Leitplanken aus C wirken auch dann, wenn Junie einer Injektion folgt.

## Reflexionsfragen

- Welcher Versuch war am unauffälligsten, und warum hätte er funktioniert?
- Welche Injektion hätte die Allowlist aus Ü1 abgefangen, welche nicht?
- Welche Review-Frage findet solche Manipulationen im Alltag? („Braucht der Auftrag diese Aktion überhaupt?“)
