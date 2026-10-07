package de.devadze.junie.ue2_apiclient;

/** Liest Wechselkurse von einem Kursdienst. */
public interface ExchangeRateClient {

    /**
     * Holt den aktuellen Kurs für ein Währungspaar.
     *
     * @param base Basiswährung, z. B. "EUR"
     * @param quote Zielwährung, z. B. "USD"
     * @return der Kurs
     * @throws ExchangeRateException wenn der Dienst keinen gültigen Kurs liefert
     */
    ExchangeRate fetchRate(String base, String quote) throws ExchangeRateException;
}
