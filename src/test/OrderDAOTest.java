/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package test;

import org.junit.jupiter.api.*;
import test.controllers.OrderDAO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderDAOTest {

    @Test
    @Order(1)
    @DisplayName("Should add product with id 1 to basket of customer with id 1")
    void addProductToCustomerBasket() {
        assertTrue(new OrderDAO().addProductToCustomerBasket(1,1));
    }

    @Test
    @Order(2)
    @DisplayName("Should remove product with id 1 from basket of customer with id 1")
    void removeProductFromCustomerBasket() {
        assertTrue(new OrderDAO().removeProductFromCustomerBasket(1,1));
    }

    @Test
    @Order(3)
    @DisplayName("Should fetch basket items for customer with id 1")
    void findCustomerBasketItems() {
        assertInstanceOf(ArrayList.class, new OrderDAO().findCustomerBasketItems(1));
    }

    @Test
    @Order(4)
    @DisplayName("Should create a new order for customer with id 1")
    void addOrder() {
        new OrderDAO().addProductToCustomerBasket(1,1);
        assertTrue(new OrderDAO().addOrder(1));
    }

    @Test
    @Order(5)
    @DisplayName("Should fetch all orders")
    void findAllOrders() {
        assertInstanceOf(ArrayList.class, new OrderDAO().findAllOrders());
    }

    @Test
    @Order(6)
    @DisplayName("Should fetch orders for customer with id 1")
    void findCustomerOrders() {
        assertInstanceOf(ArrayList.class, new OrderDAO().findCustomerOrders(1));
    }
}