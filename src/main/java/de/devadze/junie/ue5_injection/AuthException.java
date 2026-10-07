package de.devadze.junie.ue5_injection;

/** Das Token ist ungültig oder unvollständig. */
public class AuthException extends Exception {

    public AuthException(String message) {
        super(message);
    }
}
