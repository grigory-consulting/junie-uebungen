# Ü6 Abschlussprojekt: überfällige Aufgaben als CSV

## Lernziel

Sie setzen eine kleine Anforderung vollständig mit Junie um: Anforderung, Plan, Umsetzung, Tests, Review, Dokumentation des KI-Einsatzes. Sie wenden dabei alles an, was Sie im Lauf des Tages eingerichtet haben (`AGENTS.md`, Allowlist, `.aiignore`).

## Ausgangslage

Die Aufgabenverwaltung aus Ü1 (`de.devadze.junie.ue1_guidelines.TaskService` und `Task`) soll einen Export bekommen. Das Paket `de.devadze.junie.ue6_abschluss` ist leer bis auf `package-info.java`.

## Anforderung

Das Team möchte überfällige Aufgaben als CSV-Datei an die Projektleitung schicken.

- Neue Klasse `OverdueCsvExport` im Paket `ue6_abschluss` mit einer Methode, die für einen Stichtag die überfälligen Aufgaben eines `TaskService` als CSV-Text liefert.
- Überfällig ist eine Aufgabe, die nicht erledigt ist und deren Fälligkeitsdatum vor dem Stichtag liegt. Aufgaben ohne Fälligkeitsdatum sind nie überfällig. Fällig am Stichtag selbst ist noch nicht überfällig.
- Spalten: `id,title,due,days_overdue`. Erste Zeile ist die Kopfzeile. Zeilenende `\n`.
- Sortierung: älteste Fälligkeit zuerst, bei gleichem Datum nach `id` aufsteigend.
- Datum im Format ISO 8601 (`2026-10-14`).
- Titel mit Komma, Anführungszeichen oder Zeilenumbruch werden nach RFC 4180 in Anführungszeichen gesetzt, enthaltene Anführungszeichen verdoppelt.
- Keine überfälligen Aufgaben: nur die Kopfzeile.

**Nicht im Scope:** Schreiben in eine Datei, andere Trennzeichen, Zeitzonen, Änderungen an `TaskService` außer dem Nötigsten.

## Definition of Done

- [ ] Plan von Junie gelesen und freigegeben, bevor Code entstand
- [ ] `OverdueCsvExport` mit Javadoc, Namen nach `AGENTS.md`
- [ ] `OverdueCsvExportTest` deckt jede Regel der Anforderung ab (mindestens: leer, Stichtag-Grenze, ohne Datum, erledigt, Sortierung, Komma und Anführungszeichen im Titel)
- [ ] `mvn -q test` grün
- [ ] keine neuen Abhängigkeiten, `pom.xml` unverändert
- [ ] Diff vollständig gelesen; keine Änderungen außerhalb von `ue6_abschluss` ohne Begründung
- [ ] `KI_EINSATZ_PROTOKOLL.md` ausgefüllt

## Aufgaben

**A. Auftrag formulieren.** Schreiben Sie Ihren Auftrag an Junie auf Basis der Anforderung. Verlangen Sie einen Plan.

**B. Umsetzen lassen.** Geben Sie den Plan frei oder korrigieren Sie ihn. Lassen Sie Junie implementieren und testen.

**C. Review.** Prüfen Sie den Diff gegen die Definition of Done. Achten Sie besonders auf: Grenzfall am Stichtag, Sortierung bei gleichem Datum, Escaping. Lassen Sie Junie Befunde einzeln beheben.

**D. Dokumentieren.** Kopieren Sie `aufgaben/KI_EINSATZ_PROTOKOLL.md` in das Projektwurzelverzeichnis und füllen Sie es aus.

## Hinweis zum Junie-Prompt

Nutzen Sie die Muster des Tages: Anforderung zuerst (Ü2), Plan vor Code, Grenzen nennen („nur Paket `ue6_abschluss`“), Prüfbefehl nennen. Wenn `TaskService` eine Änderung braucht (zum Beispiel eine unveränderliche Sicht auf die Liste), soll Junie das im Plan begründen.

## Reflexionsfragen

- Welche Regel der Anforderung hat Junie zuerst falsch oder gar nicht umgesetzt?
- Hat Ihre `AGENTS.md` aus Ü1 ohne Wiederholung im Prompt gewirkt?
- Was würden Sie in Ihrem Team in die Vorlage für das KI-Einsatzprotokoll aufnehmen?
