package de.devadze.junie.ue3_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Beschreibt das Soll laut Fachvorgabe "ab 100 EUR" und "ab 250 EUR": die Grenzwerte sind inklusive.
 *
 * <p>Läuft nicht im normalen {@code mvn test}, sondern nur mit {@code mvn test -Dgroups=ue3}.
 */
@Tag("ue3")
class TieredDiscountTest {

    @Test
    void belowFirstTierPaysFullPrice() {
        assertEquals(5_000, TieredDiscount.apply(5_000));
    }

    @Test
    void exactly100EuroAlreadyGetsTenPercent() {
        // Fachvorgabe: "Ab 100 EUR", 100,00 EUR ist inklusive.
        assertEquals(9_000, TieredDiscount.apply(10_000));
    }

    @Test
    void between100And250EuroGetsTenPercent() {
        assertEquals(18_000, TieredDiscount.apply(20_000));
    }

    @Test
    void exactly250EuroAlreadyGetsTwentyPercent() {
        // Fachvorgabe: "Ab 250 EUR", 250,00 EUR ist inklusive.
        assertEquals(20_000, TieredDiscount.apply(25_000));
    }

    @Test
    void above250EuroGetsTwentyPercent() {
        assertEquals(32_000, TieredDiscount.apply(40_000));
    }

    @Test
    void roundsDiscountToNearestCent() {
        // 10 % von 123,45 EUR sind 12,345 EUR, gerundet 12,35 EUR Rabatt.
        assertEquals(11_110, TieredDiscount.apply(12_345));
    }

    @Test
    void rejectsNegativeSubtotal() {
        assertThrows(IllegalArgumentException.class, () -> TieredDiscount.apply(-1));
    }
}
