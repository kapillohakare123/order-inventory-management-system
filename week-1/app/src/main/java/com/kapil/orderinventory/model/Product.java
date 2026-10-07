package com.kapil.orderinventory.model;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

public class Product {

    private final UUID id;
    private final String sku;
    private final String name;
    private final BigDecimal price;

    public Product(String sku, String name, BigDecimal price) {
        // Validate required text before calling methods on it.
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU must not be blank");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }

        // Validate price.
        if (price == null) {
            throw new IllegalArgumentException("Price is required");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        if (price.stripTrailingZeros().scale() > 2) {
            throw new IllegalArgumentException(
                    "Price must have at most two decimal places");
        }

        // Assign normalized values after validation succeeds.
        this.id = UUID.randomUUID();
        this.sku = sku.trim().toUpperCase(Locale.ROOT);
        this.name = name.trim();
        this.price = price.setScale(2);
    }

    public UUID getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
