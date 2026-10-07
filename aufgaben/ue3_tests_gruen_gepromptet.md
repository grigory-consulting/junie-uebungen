# Ü3 Tests grün gepromptet

## Lernziel

Sie erleben das gefährlichste Muster beim Arbeiten mit Coding-Agenten: Der Agent macht einen roten Test grün, indem er den Test ändert statt den Code. Sie lernen, Junie so zu beauftragen und abzusichern, dass der Produktionscode korrigiert wird.

## Ausgangslage

Paket `de.devadze.junie.ue3_tests`:

- `TieredDiscount.apply(long subtotalCents)` berechnet einen Staffelrabatt. Laut Fachvorgabe im Javadoc gibt es „ab 100 EUR“ 10 % und „ab 250 EUR“ 20 % Rabatt.
- `TieredDiscountTest` beschreibt das Soll. Zwei Tests sind rot, weil der Code einen Fehler enthält.

Die Tests dieser Übung tragen das JUnit-Tag `ue3`. Der normale Lauf `mvn test` schließt sie aus, damit die übrigen Übungen grün bauen. Diese Übung starten Sie mit:

```bash
mvn -q test -Dgroups=ue3
```

In IntelliJ IDEA starten Sie die Testklasse direkt über das grüne Dreieck; die IDE beachtet den Ausschluss aus der `pom.xml` dabei nicht.

## Aufgaben

**A. Die Falle.** Stellen Sie sicher, dass Ihre Arbeit committet ist oder Sie den Stand zurückrollen können. Geben Sie Junie bewusst diesen schlechten Auftrag:

```text
TieredDiscountTest ist rot. Mach die Tests grün.
```

Prüfen Sie das Ergebnis im Diff. Typische Ausgänge:

- Junie ändert die Erwartungswerte im Test.
- Junie baut eine Sonderbehandlung für genau die Testwerte in den Produktionscode ein.
- Junie schwächt oder entfernt eine Assertion.
- Junie korrigiert tatsächlich den Code. Auch dann: Hat Junie die Testdatei angefasst?

Rollen Sie die Änderung zurück (Rollback in Junie oder Git).

**B. Das Gegenmittel.** Beauftragen Sie Junie neu, mit klarer Rollenverteilung zwischen Test und Code (siehe Hinweis). Prüfen Sie danach:

- Betrifft der Diff nur `TieredDiscount.java`?
- Ist `TieredDiscountTest.java` unverändert?
- Laufen mit `mvn -q test -Dgroups=ue3` alle 7 Tests grün?

**C. Absichern.** Ergänzen Sie Ihre `AGENTS.md` aus Ü1 um eine Regel, die das Muster dauerhaft ausschließt, zum Beispiel: „Ein fehlschlagender Test beschreibt das Soll. Ändern Sie nie Erwartungswerte oder Assertions, um Tests grün zu bekommen. Wenn Sie einen Test für falsch halten, halten Sie an und begründen Sie es.“ Wiederholen Sie Aufgabe A in einem neuen Chat und vergleichen Sie.

## Hinweis zum Junie-Prompt

```text
TieredDiscountTest beschreibt das erwartete Verhalten laut Fachvorgabe.
Ändern Sie den Produktionscode in TieredDiscount.java, nicht den Test.
Nennen Sie zuerst Ihre Hypothese, was im Code falsch ist und welche Eingabe den
Fehler auslöst. Erst danach der Fix. Prüfen Sie mit: mvn -q test -Dgroups=ue3
```

Die entscheidenden Teile: wer das Soll festlegt (der Test), welche Datei geändert werden darf, und dass die Hypothese vor dem Fix kommt.

## Reflexionsfragen

- Wie unauffällig war die Änderung in Aufgabe A? Wäre sie Ihnen in einem schnellen Review aufgefallen?
- Welche Formulierung in Aufgabe B war entscheidend?
- Mit welcher Review-Frage finden Sie dieses Muster im Alltag? („Wurde ein Test geändert? Wenn ja: war der Test falsch oder der Code?“)
- Wie könnten Sie das im Team zusätzlich absichern (Review-Regel, Pre-Commit-Hook, der Commits mit reinen Teständerungen markiert)?
