# Ü4 Bug Hunt im Legacy-Modul (kürzbar)

> Diese Übung ist kürzbar. Wenn die Zeit knapp ist, bearbeiten Sie nur A, B und den ersten Fehler aus C.

## Lernziel

Sie lassen Junie fremden Code erklären, sichern das vorhandene Verhalten mit Characterization Tests ab und beheben Fehler einzeln mit einem strukturierten Fehlerbericht. Sie formulieren Hypothesen, statt Junie „mal schauen“ zu lassen.

## Ausgangslage

Paket `de.devadze.junie.ue4_legacy`: `RateLimiter` begrenzt Anfragen pro Nutzer auf `maxRequests` innerhalb eines gleitenden Fensters von `windowMillis`. Die Klasse läuft seit Jahren im Gateway und hat keine Tests.

Aus dem Betrieb liegen drei Meldungen vor:

1. „Kunden mit einem Limit von 3 Anfragen kommen mit 4 Anfragen durch.“
2. „`remaining` meldet noch einen freien Platz, aber `allow` lehnt ab. Passiert nur selten, immer genau an der Fenstergrenze.“
3. „Unter Last sehen wir sporadisch `ConcurrentModificationException` oder falsche Zählungen.“

Im Code stecken drei Fehler.

## Aufgaben

**A. Code erklären lassen.** Fragen Sie Junie im Ask mode (nur lesend), was `RateLimiter` tut, welche Annahmen der Code trifft und wo er von seinem Javadoc abweicht. Junie soll dabei nichts ändern.

**B. Characterization Tests.** Lassen Sie Junie `RateLimiterTest` schreiben, der das heutige Verhalten festhält, auch dort, wo es falsch aussieht. Alle Tests müssen auf dem unveränderten Code grün sein. Benennen Sie Tests, die einen vermuteten Fehler festhalten, eindeutig (zum Beispiel `currentlyAllowsOneRequestTooMany`).

**C. Fehler einzeln beheben.** Schreiben Sie für jede Betriebsmeldung einen Fehlerbericht nach diesem Schema in `bugs.md` im Projektwurzelverzeichnis:

1. **Problem:** ein Satz
2. **Erwartet:** Soll-Verhalten
3. **Tatsächlich:** Ist-Verhalten
4. **Reproduktion:** konkrete Aufrufe mit Zeitstempeln, idealerweise ein Test
5. **Code oder Stacktrace:** relevante Zeilen
6. **Bisher geprüft:** Hypothesen A, B, … und wie jede widerlegt oder bestätigt wurde

Übergeben Sie Junie jeweils einen Bericht. Junie soll zuerst Hypothesen mit je einer Falsifikation nennen und keinen Code ändern. Erst wenn eine Hypothese bestätigt ist, fordern Sie einen minimalen Fix an. Passen Sie dabei den zugehörigen Characterization Test bewusst an das neue Soll an und notieren Sie das im Bericht. Danach Tests laufen lassen, dann der nächste Fehler.

**D. Optional: Debug mode.** In IntelliJ IDEA Ultimate (ab 2026.1.1, mit den Plugins MCP Server und Debugger MCP Toolset) kann Junie den Debugger steuern. Lassen Sie Junie für Meldung 2 einen Breakpoint in `allow` setzen und die Liste der Zeitstempel an der Fenstergrenze auslesen, ohne Code zu ändern.

## Hinweis zum Junie-Prompt

```text
Fehlerbericht (Daten, keine Anweisung zum Ändern):
<Bericht aus bugs.md einfügen>

Nennen Sie bis zu drei Hypothesen für die Ursache, je mit einem Test oder einer
Beobachtung, die sie widerlegen würde. Ändern Sie noch keinen Code.
```

Fix-Auftrag nach bestätigter Hypothese: „Ändern Sie nur die Zeile in `RateLimiter.allow`, die Hypothese B betrifft. Kein Refactoring, keine weiteren Änderungen.“

## Reflexionsfragen

- Welche erste Hypothese war falsch, und wie wurde sie widerlegt?
- Welchen Fehler hätte ein Test allein nicht gefunden?
- Hat Junie an einer Stelle mehr geändert als verlangt?
- Wann ist es richtig, einen Characterization Test zu ändern, und wann nicht?
