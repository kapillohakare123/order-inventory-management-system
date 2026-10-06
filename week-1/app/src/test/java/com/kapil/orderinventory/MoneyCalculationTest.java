package com.kapil.orderinventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

public class MoneyCalculationTest {
    @Test
    void calculatesTotalForTwoKeyboardsAndOneHeadset() {
        // Arrange: prepare prices and quantities.
        BigDecimal keyboardPrice = new BigDecimal("50.00");
        BigDecimal headsetPrice = new BigDecimal("80.00");

        // Act: calculate the order total.
        BigDecimal total = keyboardPrice.multiply(new BigDecimal(2)).add(headsetPrice);

        // Assert: compare monetary values numerically.
        BigDecimal expected = new BigDecimal("180.00");
        assertEquals(0, expected.compareTo(
                total));

    }

}
