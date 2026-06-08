package com.example.order;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTest {
    private final OrderService orderService = new OrderService();

    @Test
    void testCalcEmptyOrder() {
        double result = orderService.calc(Collections.emptyList(), "REGULAR");
        assertEquals(0.0, result);
    }

    @Test
    void testCalcRegularCustomerNoDiscount() {
        List<Item> items = Arrays.asList(new Item("Book", 100.0, 2));
        double result = orderService.calc(items, "REGULAR");
        assertEquals(200.0, result);
    }

    @Test
    void testCalcVipCustomer() {
        List<Item> items = Arrays.asList(new Item("Phone", 500.0, 1)); // 500
        double result = orderService.calc(items, "VIP"); // 500 * 0.9 = 450
        assertEquals(450.0, result);
    }

    @Test
    void testCalcNewCustomer() {
        List<Item> items = Arrays.asList(new Item("Shoes", 200.0, 1)); // 200
        double result = orderService.calc(items, "NEW"); // 200 * 0.95 = 190
        assertEquals(190.0, result);
    }

    @Test
    void testCalcLargeOrderDiscount() {
        List<Item> items = Arrays.asList(new Item("Laptop", 1200.0, 1)); // 1200
        double result = orderService.calc(items, "REGULAR"); // 1200 - 50 = 1150
        assertEquals(1150.0, result);
    }

    @Test
    void testCalcVipWithLargeOrderDiscount() {
        List<Item> items = Arrays.asList(new Item("PC", 1200.0, 1)); // 1200
        double result = orderService.calc(items, "VIP"); // 1200 * 0.9 = 1080 -> 1080 > 1000 -> 1080 - 50 = 1030
        assertEquals(1030.0, result);
    }

    @Test
    void testCalcWithTwoProductsOrder() {
        List<Item> items = Arrays.asList(new Item("PC", 1200.0, 1),
                new Item("Mouse", 100.0, 1)
        ); // 1300)
        double result = orderService.calc(items, "REGULAR"); // 1300  - 50 = 1250
        assertEquals(1250.0, result);
    }

    @Test
    void testCalcDiscountForNumberMoreThanTenItemsOneProduct() {
        List<Item> items = Arrays.asList(new Item("Pen", 10.0, 11)); // 11 * 10 = 110 * 0.99 = 108,9
        double result = orderService.calc(items, "REGULAR");
        assertEquals(108.9, result);
    }

    @Test
    void testCalcDiscountForNumberMoreThanTenItemsDifferentProduct() {
        List<Item> items = Arrays.asList(
                new Item("Pen", 10.0, 5), // 5 * 10 = 50
                new Item("Pencil", 5, 4), // 4 * 5 = 20
                new Item("Ruler", 20,2)); // 2 * 20 = 40 //50 + 20 + 40 = 110 * 0.99 = 108.9
        double result = orderService.calc(items, "REGULAR");
        assertEquals(108.9, result);
    }
}
