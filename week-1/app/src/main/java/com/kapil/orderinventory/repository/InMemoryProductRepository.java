package com.kapil.orderinventory.repository;

import com.kapil.orderinventory.model.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryProductRepository {
    private final Map<UUID, Product> products = new HashMap<>();

    public void save(Product product) {
        // Reject null.
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getId() == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        // Reject an existing SKU belonging to a different product ID.
        for (Product existing : products.values()) {
            boolean sameSku = existing.getSku().equals(product.getSku());
            boolean differentId = !existing.getId().equals(product.getId());

            if (sameSku && differentId) {
                throw new IllegalArgumentException("SKU already exists");
            }
        }

        // Store the product using its ID as the key.
        products.put(product.getId(), product);
    }

    public Optional<Product> findById(UUID id) {
        // Return the matching product, or Optional.empty().
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> findAll() {
        // Return an unmodifiable snapshot of the stored products.
        return List.copyOf(products.values());
    }

}
