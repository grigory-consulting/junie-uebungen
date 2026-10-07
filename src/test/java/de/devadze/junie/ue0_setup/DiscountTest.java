package de.devadze.junie.ue0_setup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DiscountTest {

    @Test
    void appliesPercentage() {
        assertEquals(8_000, Discount.apply(10_000, 20));
    }

    @Test
    void rejectsPercentAboveHundred() {
        assertThrows(IllegalArgumentException.class, () -> Discount.apply(10_000, 101));
    }
}
