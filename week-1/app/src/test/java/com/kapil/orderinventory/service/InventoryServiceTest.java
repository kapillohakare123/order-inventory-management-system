package com.kapil.orderinventory.service;

import com.kapil.orderinventory.model.Product;
import com.kapil.orderinventory.repository.InMemoryProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryServiceTest {

    @Test
    void savedProductInitiallyHasZeroStock() {
        // Arrange: save a product and provide its repository to the service.
        InMemoryProductRepository repository = new InMemoryProductRepository();

        Product product = new Product(
                "KB-001",
                "Keyboard",
                new BigDecimal("499.90"));
        repository.save(product);

        InventoryService inventoryService = new InventoryService(repository);

        // Act: check stock before any replenishment.
        int availableStock = inventoryService.getAvailableStock(product.getId());

        // Assert: a saved product starts with zero stock.
        assertEquals(0, availableStock);
    }

    @Test
    void testAddStock() {
        // Arrange: save a product and provide its repository to the service.
        InMemoryProductRepository repository = new InMemoryProductRepository();

        Product product = new Product(
                "KB-001",
                "Keyboard",
                new BigDecimal("499.90"));
        repository.save(product);

        InventoryService inventoryService = new InventoryService(repository);

        // Act: replenish stock and check available stock.
        inventoryService.replenishStock(product.getId(), 10);
        int availableStock = inventoryService.getAvailableStock(product.getId());

        // Assert: the available stock should reflect the replenished quantity.
        assertEquals(10, availableStock);
    }

    @Test
    void testAddStockMore5Products() {
        // Arrange: save a product and provide its repository to the service.
        InMemoryProductRepository repository = new InMemoryProductRepository();

        Product product = new Product(
                "KB-001",
                "Keyboard",
                new BigDecimal("499.90"));
        repository.save(product);

        InventoryService inventoryService = new InventoryService(repository);

        // Act: replenish stock and check available stock.
        inventoryService.replenishStock(product.getId(), 10);
        inventoryService.replenishStock(product.getId(), 5);
        int availableStock = inventoryService.getAvailableStock(product.getId());

        // Assert: the available stock should reflect the replenished quantity.
        assertEquals(15, availableStock);
    }

    @Test
    void testTwoProductsMaintainSeparateStock() {
        // Arrange: save two products and provide their repository to the service.
        InMemoryProductRepository repository = new InMemoryProductRepository();

        Product product1 = new Product(
                "KB-001",
                "Keyboard",
                new BigDecimal("499.90"));
        repository.save(product1);

        Product product2 = new Product(
                "MS-001",
                "Mouse",
                new BigDecimal("299.90"));
        repository.save(product2);

        InventoryService inventoryService = new InventoryService(repository);

        // Act: replenish stock for both products and check available stock.
        inventoryService.replenishStock(product1.getId(), 10);
        inventoryService.replenishStock(product2.getId(), 5);
        int availableStockProduct1 = inventoryService.getAvailableStock(product1.getId());
        int availableStockProduct2 = inventoryService.getAvailableStock(product2.getId());

        // Assert: the available stock should reflect the replenished quantity for each
        // product.
        assertEquals(10, availableStockProduct1);
        assertEquals(5, availableStockProduct2);
    }

    @Test
    void testZeroOrNegativeStockReplenishment() {
        // Arrange: save a product and provide its repository to the service.
        InMemoryProductRepository repository = new InMemoryProductRepository();

        Product product = new Product(
                "KB-001",
                "Keyboard",
                new BigDecimal("499.90"));
        repository.save(product);

        InventoryService inventoryService = new InventoryService(repository);

        inventoryService.replenishStock(product.getId(), 10);

        assertThrows(IllegalArgumentException.class,
                () -> inventoryService.replenishStock(product.getId(), 0));
        assertEquals(10, inventoryService.getAvailableStock(product.getId()));

        assertThrows(IllegalArgumentException.class,
                () -> inventoryService.replenishStock(product.getId(), -5));
        assertEquals(10, inventoryService.getAvailableStock(product.getId()));
    }

    @Test
    void testNullAndUnknownProductId() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        Product product = new Product("KB-001", "Keyboard", new BigDecimal("499.90"));
        repository.save(product);
        InventoryService inventoryService = new InventoryService(repository);
        inventoryService.replenishStock(product.getId(), 10);

        assertThrows(IllegalArgumentException.class,
                () -> inventoryService.getAvailableStock(null));
        assertThrows(IllegalArgumentException.class,
                () -> inventoryService.replenishStock(null, 5));

        var unknownProductId = java.util.UUID.randomUUID();
        assertThrows(IllegalArgumentException.class,
                () -> inventoryService.getAvailableStock(unknownProductId));
        assertThrows(IllegalArgumentException.class,
                () -> inventoryService.replenishStock(unknownProductId, 5));

        // Rejected requests must not affect existing stock.
        assertEquals(10, inventoryService.getAvailableStock(product.getId()));
    }
}
