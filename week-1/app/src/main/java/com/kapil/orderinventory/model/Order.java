package com.kapil.orderinventory.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Order {
    private final UUID id;
    private final Instant createdAt;
    private final List<OrderItem> items;

    public Order(List<OrderItem> items) {
        Set<UUID> productIds = new HashSet<>();
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        for (OrderItem item : items) {
            if (item == null) {
                throw new IllegalArgumentException("Order item must not be null");
            }

            if (!productIds.add(item.getProductId())) {
                throw new IllegalArgumentException(
                        "An order cannot contain duplicate products");
            }
        }

        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.items = List.copyOf(items);
    }

    public UUID getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.getLineTotal());
        }

        return total;
    }
}
