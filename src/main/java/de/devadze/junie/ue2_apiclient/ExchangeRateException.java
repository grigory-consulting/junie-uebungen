package de.devadze.junie.ue2_apiclient;

/** Der Kursdienst hat keinen gültigen Kurs geliefert. */
public class ExchangeRateException extends Exception {

    public ExchangeRateException(String message) {
        super(message);
    }

    public ExchangeRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
