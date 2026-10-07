package de.devadze.junie.ue2_apiclient;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Ein Wechselkurs: 1 Einheit {@code base} kostet {@code rate} Einheiten {@code quote}.
 *
 * @param base Basiswährung, ISO-4217-Code wie "EUR"
 * @param quote Zielwährung, ISO-4217-Code wie "USD"
 * @param rate Kurs, größer als 0
 * @param date Stichtag des Kurses
 */
public record ExchangeRate(String base, String quote, BigDecimal rate, LocalDate date) {
}
