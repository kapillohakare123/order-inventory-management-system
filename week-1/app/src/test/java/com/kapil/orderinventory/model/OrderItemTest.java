package com.kapil.orderinventory.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderItemTest {

    @Test
    void capturesProductDetailsAndQuantity() {
        Product product = new Product("KB-001", "Keyboard", new BigDecimal("499.90"));
        OrderItem item = new OrderItem(product, 2);

        assertEquals(product.getId(), item.getProductId());
        assertEquals("Keyboard", item.getProductName());
        assertEquals(new BigDecimal("499.90"), item.getUnitPrice());
        assertEquals(2, item.getQuantity());
    }

    @Test
    void calculatesLineTotalForTwoItems() {
        Product product = new Product("KB-001", "Keyboard", new BigDecimal("499.90"));
        OrderItem item = new OrderItem(product, 2);

        assertEquals(new BigDecimal("999.80"), item.getLineTotal());
    }

    @Test
    void calculatesLineTotalForOneItem() {
        Product product = new Product("KB-001", "Keyboard", new BigDecimal("499.90"));
        OrderItem item = new OrderItem(product, 1);

        assertEquals(new BigDecimal("499.90"), item.getLineTotal());
    }

    @Test
    void rejectsNullProduct() {
        assertThrows(IllegalArgumentException.class, () -> new OrderItem(null, 1));
    }

    @Test
    void rejectsZeroQuantity() {
        Product product = new Product("KB-001", "Keyboard", new BigDecimal("499.90"));
        assertThrows(IllegalArgumentException.class, () -> new OrderItem(product, 0));
    }

    @Test
    void rejectsNegativeQuantity() {
        Product product = new Product("KB-001", "Keyboard", new BigDecimal("499.90"));
        assertThrows(IllegalArgumentException.class, () -> new OrderItem(product, -1));
    }
}
