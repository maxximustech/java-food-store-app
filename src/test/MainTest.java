/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package test;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({CustomerDAOTest.class,FoodProductDAOTest.class,OrderDAOTest.class,UserDAOTest.class})
public class MainTest {
}
