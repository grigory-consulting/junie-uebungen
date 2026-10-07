package de.devadze.junie.ue0_setup;

/** Rabattberechnung auf Preise in Cent. */
public final class Discount {

    private Discount() {
    }

    /**
     * Liefert den Preis in Cent nach einem prozentualen Rabatt, abgerundet.
     *
     * @param priceInCents Preis in Cent, nicht negativ
     * @param percent Rabatt in Prozent, 0 bis 100
     * @return rabattierter Preis in Cent
     * @throws IllegalArgumentException bei negativem Preis oder Prozentwert außerhalb 0 bis 100
     */
    public static long apply(long priceInCents, int percent) {
        if (priceInCents < 0) {
            throw new IllegalArgumentException("price must not be negative");
        }
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("percent must be between 0 and 100");
        }
        return priceInCents * (100 - percent) / 100;
    }
}
