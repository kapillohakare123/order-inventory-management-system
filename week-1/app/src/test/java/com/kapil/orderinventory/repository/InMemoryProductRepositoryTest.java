package com.kapil.orderinventory.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.kapil.orderinventory.model.Product;

public class InMemoryProductRepositoryTest {
    @Test
    void testSaveAndFindById() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        Product product = new Product("SKU123", "Test Product", new java.math.BigDecimal("19.99"));
        repository.save(product);

        // Verify that the product can be found by its ID
        assertEquals(product, repository.findById(product.getId()).orElseThrow());
    }

    @Test
    void testUnknownProductId() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        // Verify that searching for an unknown product ID returns Optional.empty()
        assertTrue(repository.findById(java.util.UUID.randomUUID()).isEmpty());
    }

    @Test
    void testListNewRepository() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        // Verify that a new repository has no products
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void testSaveTwoProducts() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        Product product1 = new Product("SKU123", "Test Product 1", new java.math.BigDecimal("19.99"));
        Product product2 = new Product("SKU456", "Test Product 2", new java.math.BigDecimal("29.99"));
        repository.save(product1);
        repository.save(product2);

        // Verify that both products can be found by their IDs
        assertEquals(product1, repository.findById(product1.getId()).orElseThrow());
        assertEquals(product2, repository.findById(product2.getId()).orElseThrow());
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void testSaveTwoProductsWithEqualSku() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        Product product1 = new Product("SKU124", "Test Product 1", new java.math.BigDecimal("19.99"));
        Product product2 = new Product(" sku124 ", "Test Product 2", new java.math.BigDecimal("29.99"));
        repository.save(product1);
        assertThrows(IllegalArgumentException.class, () -> repository.save(product2));
        assertEquals(1, repository.findAll().size());
        assertEquals(product1, repository.findById(product1.getId()).orElseThrow());
        assertTrue(repository.findById(product2.getId()).isEmpty());
    }

    @Test
    void testSaveTheSameProductTwice() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        Product product = new Product("SKU124", "Test Product", new java.math.BigDecimal("19.99"));
        repository.save(product);
        repository.save(product);
        assertEquals(1, repository.findAll().size());
        assertEquals(product, repository.findById(product.getId()).orElseThrow());
    }

    @Test
    void testsaveNullProduct() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        assertThrows(IllegalArgumentException.class, () -> repository.save(null));
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void testAddAnItemtoReturnList() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        Product product = new Product("SKU1235", "Test Product", new java.math.BigDecimal("19.99"));
        repository.save(product);
        assertThrows(UnsupportedOperationException.class,
                () -> repository.findAll().add(product));
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testSaveAnotherProductAfterObtainingList() {
        InMemoryProductRepository repository = new InMemoryProductRepository();

        Product product1 = new Product(
                "SKU1236",
                "Test Product 1",
                new BigDecimal("19.99"));
        repository.save(product1);

        // Capture a snapshot containing only product1.
        List<Product> snapshot = repository.findAll();

        Product product2 = new Product(
                "SKU1237",
                "Test Product 2",
                new BigDecimal("29.99"));
        repository.save(product2);

        // The earlier snapshot still contains only product1.
        assertEquals(1, snapshot.size());
        assertEquals(product1, snapshot.get(0));

        // A fresh snapshot contains both products.
        assertEquals(2, repository.findAll().size());
    }
}
