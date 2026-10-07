# Ü0 Setup-Check: Junie erledigt eine kleine Aufgabe

## Lernziel

Sie prüfen, ob IntelliJ IDEA, das Plugin AI Assistant, Junie im AI Chat, JDK 21 und Maven zusammenspielen. Sie sehen einmal den vollständigen Ablauf: Auftrag, Plan, Freigaben, Änderungen, Testlauf, Diff.

## Ausgangslage

Paket `de.devadze.junie.ue0_setup`:

- `Discount.apply(long priceInCents, int percent)` berechnet einen Rabatt in Cent und rundet ab.
- `DiscountTest` enthält zwei Tests, beide grün.

## Aufgaben

**A. Projekt prüfen.** Öffnen Sie das Projekt in IntelliJ IDEA (Ordner `junie-uebungen`, als Maven-Projekt). Stellen Sie unter **File | Project Structure** das SDK auf JDK 21. Führen Sie `mvn test` im Terminal aus oder starten Sie `DiscountTest` über das grüne Dreieck im Editor. Erwartung: alle Tests grün.

**B. Junie beauftragen.** Öffnen Sie den AI Chat, wählen Sie „Junie by JetBrains“ und geben Sie diesen Auftrag ein:

```text
Ergänzen Sie in src/main/java/de/devadze/junie/ue0_setup/Discount.java eine Methode
public static long applyCoupon(long priceInCents, String code).
Der Code "SAVE10" gibt 10 % Rabatt, abgerundet wie bei apply().
Jeder andere Code und null führen zu einer IllegalArgumentException.
Schreiben Sie dazu JUnit-5-Tests in DiscountTest (Rabatt, Rundung, unbekannter Code, null)
und führen Sie danach mvn test aus. Ändern Sie keine anderen Dateien.
```

**C. Ablauf beobachten.** Notieren Sie, wann Junie um Freigabe fragt (Befehl, Dateiänderung) und was Sie jeweils freigeben. Lesen Sie den angezeigten Befehl, bevor Sie „Yes“ wählen.

**D. Ergebnis prüfen.** Öffnen Sie die Liste der geänderten Dateien und jede Datei im Diff. Prüfen Sie: Wurden nur `Discount.java` und `DiscountTest.java` geändert? Laufen alle Tests grün? Prüft ein Test die Abrundung (zum Beispiel 10.001 Cent ergeben 9.000 Cent)?

## Hinweis zum Junie-Prompt

Der Auftrag nennt Datei, Signatur, Verhalten, Fehlerfälle, die gewünschten Tests, den Prüfbefehl und eine Grenze („keine anderen Dateien“). So bleibt die Aufgabe klein und der Lauf billig. Wählen Sie „Always allow“ in dieser Übung noch nicht; das regeln Sie in Ü1 über die Action Allowlist.

## Reflexionsfragen

- Wie oft hat Junie um Freigabe gefragt, und bei welchen Aktionen?
- Hat Junie vor dem Schreiben den vorhandenen Code und die Tests gelesen?
- Welche Kosten zeigt das Dashboard Ihres Anbieters für diesen Lauf?
