package com.kapil.orderinventory;

import java.math.BigDecimal;
import java.util.List;

import com.kapil.orderinventory.model.Order;
import com.kapil.orderinventory.model.OrderItem;
import com.kapil.orderinventory.model.Product;

public class Application {
    public static void main(String[] args) {
        System.out.println("Order and Inventory Management System started");
        Product keyboard = new Product(
                "KB-001",
                "Keyboard",
                new BigDecimal("50.00"));
        Product headset = new Product(
                "HS-001",
                "headset",
                new BigDecimal("50.00"));
        Order order = new Order(List.of(
                new OrderItem(keyboard, 2),
                new OrderItem(headset, 1)));

        System.out.println(order.getTotal()); // 2498.80
    }

}