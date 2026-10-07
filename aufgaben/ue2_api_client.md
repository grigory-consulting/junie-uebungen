# Ü2 Anforderung zuerst: API-Client

## Lernziel

Sie schreiben eine präzise Anforderung, bevor Junie Code erzeugt. Sie merken dabei selbst, wo Ihre Anforderung Lücken hat, und schließen sie in einer zweiten Runde.

## Ausgangslage

Paket `de.devadze.junie.ue2_apiclient`:

- `ExchangeRate` (Record): Basiswährung, Zielwährung, Kurs, Stichtag
- `ExchangeRateClient` (Interface) mit `fetchRate(String base, String quote)`
- `ExchangeRateException` (geprüfte Exception)
- `HttpExchangeRateClient`: Konstruktor mit Basis-URI und Timeout, `fetchRate` wirft noch `UnsupportedOperationException`

Unter `src/test/java/.../ue2_apiclient/` liegt `FakeRateServer`, eine lokale Fake-API auf Basis von `com.sun.net.httpserver.HttpServer`. Sie beantwortet `GET /rates?base=EUR&quote=USD` mit

```json
{"base":"EUR","quote":"USD","rate":1.0842,"date":"2026-10-14"}
```

Unbekannte Paare liefern Status 404, fehlende Parameter Status 400. Mit `respondWith(status, body)` und `delay(Duration)` stellen Sie Fehlerfälle nach. `FakeRateServerTest` zeigt die Verwendung.

## Aufgaben

**A. Spezifikation schreiben.** Legen Sie im Projektwurzelverzeichnis `ue2_spezifikation.md` an, mit diesen Abschnitten:

1. **Ziel:** was `HttpExchangeRateClient.fetchRate` leistet, in zwei Sätzen
2. **Akzeptanzkriterien:** als Paare Eingabe und erwartetes Ergebnis, zum Beispiel „Server liefert 200 mit gültigem JSON: Rückgabe `ExchangeRate` mit Kurs als `BigDecimal` 1.0842“
3. **Edge Cases:** Was passiert bei 404, 400, 500, Timeout, kaputtem JSON, fehlendem Feld `rate`, Kurs 0 oder negativ, Währungscode in Kleinbuchstaben oder mit falscher Länge, `null` als Argument? Welche Exception mit welcher Meldung?
4. **Nicht im Scope:** was der Client ausdrücklich nicht tut (zum Beispiel Caching, Wiederholungsversuche, Authentifizierung, JSON-Bibliothek)
5. **Technische Vorgaben:** `java.net.http.HttpClient`, keine neuen Abhängigkeiten, minimales JSON-Lesen für genau dieses flache Format

**B. Junie umsetzen lassen.** Übergeben Sie Junie die Spezifikation und lassen Sie zuerst einen Plan zeigen. Erst nach Ihrer Freigabe soll Junie `HttpExchangeRateClient` implementieren und Tests in `HttpExchangeRateClientTest` gegen den `FakeRateServer` schreiben, einen Test je Akzeptanzkriterium und Edge Case.

**C. Ergebnis prüfen.** Lesen Sie den Diff vollständig. Prüfen Sie:

- Hat Junie Annahmen getroffen, die nicht in der Spezifikation stehen (zum Beispiel Wiederholungsversuche, Logging, eine JSON-Bibliothek in der `pom.xml`)?
- Deckt jeder Edge Case aus Ihrer Spezifikation einen Test ab?
- Wird der Timeout aus dem Konstruktor wirklich verwendet?

Nehmen Sie gefundene Annahmen in die Spezifikation auf (als Kriterium oder unter „Nicht im Scope“) und lassen Sie Junie in einer zweiten Runde nachbessern.

## Hinweis zum Junie-Prompt

```text
Lesen Sie ue2_spezifikation.md. Implementieren Sie danach ausschließlich
HttpExchangeRateClient.fetchRate im Paket ue2_apiclient und schreibe
HttpExchangeRateClientTest gegen FakeRateServer (src/test/java, gleiches Paket).
Zeige zuerst einen Plan und warte auf meine Freigabe.
Keine neuen Abhängigkeiten, keine Änderungen an pom.xml, FakeRateServer oder den Records.
Wenn die Spezifikation eine Frage offen lässt: fragen Sie nach, statt anzunehmen.
```

Halten Sie den Umfang klein: ein Client, eine Methode, eine Testklasse. Das spart AI Credits und macht den Diff lesbar.

## Reflexionsfragen

- Wo war Ihre erste Spezifikation zu vage, wo zu detailliert?
- Welche Annahme hat Junie getroffen, die Sie nicht vorhergesehen hatten?
- Wie viele Runden brauchten Spezifikation und Code, bis sie zueinander passten?
