/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import test.controllers.FoodProductDAO;
import test.models.FoodProduct;

import java.sql.Date;
import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FoodProductDAOTest {

    @Test
    @Order(1)
    @DisplayName("Should successfully create a new product")
    void addProduct() {
        assertTrue(new FoodProductDAO()
                .addProduct(new FoodProduct("5667JQ68O1TY","A test product","Test",50,5, Date.valueOf("2024-03-15")))
        );
    }

    @Test
    @Order(2)
    @DisplayName("Should successfully fetch all products")
    void findAllProducts() {
        assertInstanceOf(ArrayList.class, new FoodProductDAO().findAllProducts(false,null));
    }

    @Test
    @Order(3)
    @DisplayName("Should successfully fetch all expired products")
    void findAllExpiredProducts() {
        new FoodProductDAO().addProduct(new FoodProduct("89TYB25T39N12","Another sample product","Sample",200,100, Date.valueOf("2023-12-23")));
        assertInstanceOf(ArrayList.class, new FoodProductDAO().findAllProducts(true,null));
    }

    @Test
    @Order(4)
    @DisplayName("Should successfully fetch all products which description or sku matches the keyword 'test'")
    void filterProducts() {
        assertInstanceOf(ArrayList.class, new FoodProductDAO().findAllProducts(false,"test"));
    }

    @Test
    @Order(5)
    @DisplayName("Should successfully fetch a single product with id 1")
    void findProduct() {
        assertInstanceOf(FoodProduct.class, new FoodProductDAO().findProduct(1));
    }

    @Test
    @Order(6)
    @DisplayName("Should successfully update a product with id 1")
    void updateProduct() {
        //Updated stocks to 2
        assertTrue(new FoodProductDAO()
                .updateProduct(new FoodProduct(1,"5667JQ68O1TY","A test product","Test",50,2, Date.valueOf("2024-03-15")))
        );
    }

    @Test
    @Order(7)
    @DisplayName("Should check if the product with id 1 has up to 2 stocks")
    void isStockEnough() {
        assertTrue(new FoodProductDAO().isStockEnough(1,2));
    }

    @Test
    @Order(8)
    @DisplayName("Should delete the product with id 3")
    void deleteProduct() {
        //Adds a new product as id 3
        new FoodProductDAO().addProduct(new FoodProduct("77878HJHBSH7667","Another product","Sample",150,14, Date.valueOf("2023-12-23")));
        assertTrue(new FoodProductDAO().deleteProduct(3));
    }
}