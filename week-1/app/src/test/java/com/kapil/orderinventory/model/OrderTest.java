package com.kapil.orderinventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class OrderTest {
    @Test
    void calculatesTotalForMultipleItems() {
        Order order = new Order(List.of(
                new OrderItem(new Product("KB-001", "Keyboard", new BigDecimal("499.90")), 2),
                new OrderItem(new Product("HS-001", "Headset", new BigDecimal("1499.00")), 1)));

        assertEquals(new BigDecimal("2498.80"), order.getTotal());
    }

    @Test
    void singleItemTotalEqualsLineTotal() {
        OrderItem item = new OrderItem(
                new Product("KB-001", "Keyboard", new BigDecimal("499.90")), 2);
        Order order = new Order(List.of(item));

        assertEquals(item.getLineTotal(), order.getTotal());
    }

    @Test
    void assignsIdAndCreationTimeAndRetainsItems() {
        OrderItem item = new OrderItem(
                new Product("HS-001", "Headset", new BigDecimal("1499.00")), 1);
        Order order = new Order(List.of(item));

        assertNotNull(order.getId());
        assertNotNull(order.getCreatedAt());
        assertEquals(List.of(item), order.getItems());
    }

    @Test
    void rejectsNullList() {
        assertThrows(IllegalArgumentException.class, () -> new Order(null));
    }

    @Test
    void rejectsEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> new Order(List.of()));
    }

    @Test
    void rejectsListContainingNullItem() {
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(
                new Product("KB-001", "Keyboard", new BigDecimal("499.90")), 1));
        items.add(null);

        assertThrows(IllegalArgumentException.class, () -> new Order(items));
    }

    @Test
    void rejectsTwoItemsReferencingTheSameProduct() {
        Product keyboard = new Product("KB-001", "Keyboard", new BigDecimal("499.90"));
        OrderItem firstItem = new OrderItem(keyboard, 2);
        OrderItem secondItem = new OrderItem(keyboard, 1);

        assertThrows(IllegalArgumentException.class,
                () -> new Order(List.of(firstItem, secondItem)));
    }

    @Test
    void changingOriginalListDoesNotChangeOrder() {
        OrderItem item = new OrderItem(
                new Product("KB-001", "Keyboard", new BigDecimal("499.90")), 1);
        List<OrderItem> items = new ArrayList<>();
        items.add(item);
        Order order = new Order(items);

        items.clear();

        assertEquals(List.of(item), order.getItems());
        assertEquals(new BigDecimal("499.90"), order.getTotal());
    }

    @Test
    void returnedItemsCannotBeModified() {
        OrderItem item = new OrderItem(
                new Product("KB-001", "Keyboard", new BigDecimal("499.90")), 1);
        Order order = new Order(List.of(item));

        assertThrows(UnsupportedOperationException.class, () -> order.getItems().clear());
        assertEquals(List.of(item), order.getItems());
    }
}
