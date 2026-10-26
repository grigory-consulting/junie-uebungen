# Junie-Übungen

Übungsprojekt zur Schulung „JetBrains Junie: KI-unterstützte Code Generierung für Entwickler“. Sie lösen die Übungen mit Junie im AI Chat von IntelliJ IDEA.

**Folien:** [grigory-consulting.github.io/junie-uebungen](https://grigory-consulting.github.io/junie-uebungen/) (im Browser, Pfeiltasten oder Leertaste zum Blättern, `Esc` für die Übersicht)

## Öffnen und prüfen

1. IntelliJ IDEA: **File | New | Project from Version Control**, URL `https://github.com/grigory-consulting/junie-uebungen.git` eintragen, **Clone**. Alternativ im Terminal `git clone https://github.com/grigory-consulting/junie-uebungen.git` und **File | Open**.
2. **File | Project Structure | Project**: SDK auf JDK 21 stellen.
3. Tests im Terminal ausführen:

```bash
mvn -q test
```

Ohne Ausgabe und mit Exit-Code 0 ist alles grün. Ohne installiertes Maven nutzen Sie das Maven-Werkzeugfenster der IDE (**Lifecycle | test**).

## Sonderfall Übung 3

Die Tests der Übung 3 sind absichtlich rot. Sie tragen das JUnit-Tag `ue3`, und die `pom.xml` schließt dieses Tag im normalen Lauf aus. So bleibt `mvn test` für alle anderen Übungen grün.

```bash
mvn -q test -Dgroups=ue3    # nur die Tests der Übung 3
```

Sobald `-Dgroups` gesetzt ist, aktiviert die `pom.xml` das Profil `ue3` und hebt den Ausschluss auf. Startet man Tests direkt in der IDE (grünes Dreieck, „Run All Tests“), gilt der Ausschluss nicht; dann ist `TieredDiscountTest` rot, bis Sie Übung 3 gelöst haben.

## Übungen

| Übung | Paket | Aufgabe |
|---|---|---|
| Ü0 Setup-Check | `ue0_setup` | [aufgaben/ue0_setup_check.md](aufgaben/ue0_setup_check.md) |
| Ü1 Guidelines und Action Allowlist | `ue1_guidelines` | [aufgaben/ue1_guidelines_allowlist.md](aufgaben/ue1_guidelines_allowlist.md) |
| Ü2 Anforderung zuerst: API-Client | `ue2_apiclient` | [aufgaben/ue2_api_client.md](aufgaben/ue2_api_client.md) |
| Ü3 Tests grün gepromptet | `ue3_tests` | [aufgaben/ue3_tests_gruen_gepromptet.md](aufgaben/ue3_tests_gruen_gepromptet.md) |
| Ü4 Bug Hunt im Legacy-Modul (kürzbar) | `ue4_legacy` | [aufgaben/ue4_bug_hunt_legacy.md](aufgaben/ue4_bug_hunt_legacy.md) |
| Ü5 Prompt Injection erkennen (kürzbar) | `ue5_injection` | [aufgaben/ue5_prompt_injection.md](aufgaben/ue5_prompt_injection.md) |
| Ü6 Abschlussprojekt | `ue6_abschluss` | [aufgaben/ue6_abschlussprojekt.md](aufgaben/ue6_abschlussprojekt.md), Vorlage [aufgaben/KI_EINSATZ_PROTOKOLL.md](aufgaben/KI_EINSATZ_PROTOKOLL.md) |

Alle Pakete liegen unter `de.devadze.junie`. Abhängigkeiten: nur JUnit 5; HTTP und Fake-Server nutzen JDK-Bordmittel (`java.net.http`, `com.sun.net.httpserver`).

## Hinweise

- Eine `AGENTS.md` gibt es im Projekt absichtlich nicht. Sie schreiben sie in Ü1.
- Die Datei `.env` enthält nur Platzhalter. Sie gehört zu Ü5.
- Committen Sie vor jeder Übung, damit Sie Junies Änderungen jederzeit vergleichen und zurückrollen können.
