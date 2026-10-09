package com.kapil.orderinventory.service;

import com.kapil.orderinventory.repository.InMemoryProductRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryService {

    private final InMemoryProductRepository productRepository;
    private final Map<UUID, Integer> stock = new HashMap<>();

    public InventoryService(
            InMemoryProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public int getAvailableStock(UUID productId) {
        if (productId == null || productRepository.findById(productId).isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        return stock.getOrDefault(productId, 0);
    }

    public void replenishStock(UUID productId, int quantity) {
        if (productId == null || productRepository.findById(productId).isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero.");
        }
        stock.put(productId, stock.getOrDefault(productId, 0) + quantity);
    }
}
