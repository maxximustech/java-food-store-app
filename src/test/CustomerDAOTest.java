/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import test.controllers.CustomerDAO;
import test.models.Address;
import test.models.Customer;
import test.utils.Database;

import javax.lang.model.type.NullType;
import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerDAOTest {
    @BeforeAll
    static void setUpTables() throws Exception {
        new Database().initDBTables(true);
    }
    @Test
    @Order(1)
    @DisplayName("Should successfully create a new customer")
    void shouldCreateANewCustomer() {
        assertTrue(new CustomerDAO().addCustomer(new Customer("Azeez Abdullahi",
                new Address("31 Cicero Street","Harpurhey","Manchester","UK","M9 4GN"),
                "07778496797")
                )
        );
    }

    @Test
    @Order(2)
    @DisplayName("Should successfully fetch all customers")
    void shouldFetchAllCustomers() {
        assertInstanceOf(ArrayList.class,new CustomerDAO().findAllCustomers());
    }

    @Test
    @Order(3)
    @DisplayName("Should successfully fetch a single customer")
    void shouldFetchASingleCustomer() {
        assertInstanceOf(Customer.class,new CustomerDAO().findCustomer(1));
    }

    @Test
    @Order(4)
    @DisplayName("Should fail while trying to fetch a single customer that does not exist")
    void shouldFailWhileTryingToFetchASingleCustomer() {
        assertNull(new CustomerDAO().findCustomer(2));
    }

    @Test
    @Order(5)
    @DisplayName("Should successfully update a customer details")
    void shouldUpdateCustomerDetails() {
        assertTrue(new CustomerDAO().updateCustomer(new Customer(1,"Babajide Owolabi",
                        new Address("31 Cicero Street","Harpurhey","Manchester","UK","M9 4GN"),
                        "07778496797")
                )
        );
    }

    @Test
    @Order(6)
    @DisplayName("Should successfully delete a customer with id 2")
    void shouldDeleteACustomer() {
        //Adds the second customer as id 2
        new CustomerDAO().addCustomer(new Customer("Azeez Abdullahi",
                new Address("31 Cicero Street","Harpurhey","Manchester","UK","M9 4GN"),
                "07778496797")
        );
        assertTrue(new CustomerDAO().deleteCustomer(2));
    }
}