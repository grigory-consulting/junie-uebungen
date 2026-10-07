package de.devadze.junie.ue2_apiclient;

import java.net.URI;
import java.time.Duration;

/**
 * {@link ExchangeRateClient} über HTTP mit {@code java.net.http.HttpClient}.
 *
 * <p>Endpunkt: {@code GET <baseUri>/rates?base=EUR&quote=USD}. Antwortformat und
 * Fehlerverhalten legt Ihre Spezifikation aus Übung 2 fest.
 */
public class HttpExchangeRateClient implements ExchangeRateClient {

    private final URI baseUri;
    private final Duration timeout;

    public HttpExchangeRateClient(URI baseUri, Duration timeout) {
        this.baseUri = baseUri;
        this.timeout = timeout;
    }

    @Override
    public ExchangeRate fetchRate(String base, String quote) throws ExchangeRateException {
        throw new UnsupportedOperationException("noch nicht implementiert, siehe aufgaben/ue2_api_client.md");
    }
}
