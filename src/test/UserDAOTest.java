/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import test.controllers.UserDAO;
import test.models.User;
import test.utils.Database;

import javax.lang.model.type.NullType;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    @Order(1)
    @DisplayName("Should create a new user")
    void addUser() {
        assertTrue(new UserDAO().addUser(new User("maxx","tech")));
    }

    @Test
    @Order(2)
    @DisplayName("Should fetch all users")
    void findAllUsers() {
        assertInstanceOf(ArrayList.class, new UserDAO().findAllUsers());
    }

    @Test
    @Order(3)
    @DisplayName("Should fail while trying to login as a user using the incorrect credentials")
    void failLoginAsUser() {
        assertNull(new UserDAO().loginAsUser("maxx","qwerty"));
    }

    @Test
    @Order(4)
    @DisplayName("Should successfully change user password")
    void changeUserPassword() {
        assertTrue(new UserDAO().changeUserPassword("maxx","tech","12345"));
    }

    @Test
    @Order(5)
    @DisplayName("Should successfully delete the")
    void deleteUser() {
        assertTrue(new UserDAO().deleteUser(1));
    }
}