package de.devadze.junie.ue3_tests;

/**
 * Staffelrabatt für Warenkörbe.
 *
 * <ul>
 *   <li>Ab 100,00 EUR Warenwert: 10 % Rabatt auf den Gesamtbetrag.</li>
 *   <li>Ab 250,00 EUR Warenwert: 20 % Rabatt auf den Gesamtbetrag.</li>
 *   <li>Beträge in Cent, der Rabatt wird kaufmännisch auf ganze Cent gerundet.</li>
 * </ul>
 */
public final class TieredDiscount {

    static final long TIER_1_CENTS = 10_000;
    static final long TIER_2_CENTS = 25_000;

    private TieredDiscount() {
    }

    /**
     * Liefert den zu zahlenden Betrag nach Staffelrabatt.
     *
     * @param subtotalCents Warenwert in Cent, nicht negativ
     * @return Betrag in Cent nach Rabatt
     */
    public static long apply(long subtotalCents) {
        if (subtotalCents < 0) {
            throw new IllegalArgumentException("subtotal must not be negative");
        }
        if (subtotalCents > TIER_2_CENTS) {
            return subtotalCents - Math.round(subtotalCents * 0.20);
        }
        if (subtotalCents > TIER_1_CENTS) {
            return subtotalCents - Math.round(subtotalCents * 0.10);
        }
        return subtotalCents;
    }
}
