package com.kapil.orderinventory.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    @Test
    void testProductCreation() {
        // Arrange
        String name = "Keyboard";
        String sku = "KB-001";
        // Use a decimal string instead of a double.
        BigDecimal price = new BigDecimal("50.00");

        // Act
        Product product = new Product(
                sku,
                name,
                price);

        assertEquals(name, product.getName());
        assertEquals(sku, product.getSku());
        assertEquals(0, price.compareTo(product.getPrice()));
        assertNotNull(product.getId());

    }

    @Test
    void rejectsBlankSku() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(
                        "   ",
                        "Keyboard",
                        new BigDecimal("50.00")));
    }

    @Test
    void normalizesSkuAndTrimsName() {
        Product product = new Product(
                " kb-001 ", " Keyboard ", new BigDecimal("50.00"));

        assertEquals("KB-001", product.getSku());
        assertEquals("Keyboard", product.getName());
    }

    @Test
    void rejectsNullSku() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, "Keyboard", new BigDecimal("50.00")));
    }

    @Test
    void rejectsNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("KB-001", null, new BigDecimal("50.00")));
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("KB-001", "   ", new BigDecimal("50.00")));
    }

    @Test
    void rejectsNullPrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("KB-001", "Keyboard", null));
    }

    @Test
    void rejectsZeroPrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("KB-001", "Keyboard", new BigDecimal("0.00")));
    }

    @Test
    void rejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("KB-001", "Keyboard", new BigDecimal("-1.00")));
    }

    @Test
    void rejectsMoreThanTwoMeaningfulDecimalPlaces() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("KB-001", "Keyboard", new BigDecimal("499.999")));
    }

    @Test
    void acceptsTrailingZerosAndStoresTwoDecimalPlaces() {
        Product product = new Product(
                "KB-001", "Keyboard", new BigDecimal("499.900"));

        // BigDecimal.equals checks both the value and its scale here.
        assertEquals(new BigDecimal("499.90"), product.getPrice());
    }
}
