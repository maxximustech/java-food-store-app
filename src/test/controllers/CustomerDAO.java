/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package test.controllers;


import java.sql.Date;
import java.util.*;
import java.sql.*;

import test.utils.Database;
import test.models.Customer;
import test.models.Address;

/**
 * Customer Data Access Object - Responsible for all methods relating to {@link Customer} model
 * @see Customer
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class CustomerDAO {
    /**
     * Finds all customer list from database.
     *
     * @return {@link ArrayList} of {@link Customer} model
     */
    public ArrayList<Customer> findAllCustomers(){
        ArrayList<Customer> customerArray = new ArrayList<>();
        try{
            Connection con = new Database().getConnection();
            if(con == null){
                return customerArray;
            }
            PreparedStatement statement = con.prepareStatement("SELECT * FROM customer ORDER BY updatedOn DESC");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                customerArray.add(setUpCustomerFromQuery(result));
            }
            return customerArray;
        }catch(Exception e){
            e.printStackTrace();
        }
        return customerArray;
    }

    /**
     * Searches a single customer from database using the customer id as the parameter.
     *
     * @param id the customer id to be searched
     * @return {@link Customer} model
     */
    public Customer findCustomer(int id){
        ArrayList<Customer> customers = new ArrayList<>();
        Customer customer = null;
        try {
            Connection connection = new Database().getConnection();
            if (connection != null) {
                String sqlQuery = "SELECT * FROM customer WHERE customerId = ?";
                PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
                readyStatement.setInt(1, id);
                ResultSet result = readyStatement.executeQuery();
                while (result.next()) {
                    customers.add(setUpCustomerFromQuery(result));
                }
                result.close();
                readyStatement.close();
                connection.close();
                if(customers.size() > 0){
                    customer = customers.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    /**
     * Deletes a single customer from the database.
     *
     * @param id the customer id to be deleted
     * @return {@link Boolean} - true if the customer was deleted successfully, false - if otherwise
     */
    public boolean deleteCustomer(int id){
        try {
            if(findCustomer(id) == null){
                return false;
            }
            Connection connection = new Database().getConnection();
            if (connection != null) {
                String sqlQuery = "DELETE FROM customer WHERE customerId = ?";
                PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
                readyStatement.setInt(1, id);
                readyStatement.executeUpdate();
                readyStatement.close();
                connection.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates a customer on the database.
     *
     * @param customer the {@link Customer} model to be updated
     * @return {@link Boolean} - true if the customer was updated successfully, false - if otherwise
     */
    public boolean updateCustomer(Customer customer){
        try {
            if(findCustomer(customer.getCustomerID()) == null){
                return false;
            }
            Connection connection = new Database().getConnection();
            if (connection != null) {
                String sqlQuery = "UPDATE customer SET name = ?, addressLine1 = ?,addressLine2 = ?, addressLine3 = ?, country = ?, postCode = ?, phone = ?, createdOn = DATE('now'), updatedOn = DATE('now')  WHERE customerId = ?";
                PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
                readyStatement.setString(1, customer.getCustomerName());
                readyStatement.setString(2, customer.getAddress().getAddressLine1());
                readyStatement.setString(3, customer.getAddress().getAddressLine2());
                readyStatement.setString(4, customer.getAddress().getAddressLine3());
                readyStatement.setString(5, customer.getAddress().getCountry());
                readyStatement.setString(6, customer.getAddress().getPostCode());
                readyStatement.setString(7, customer.getTelephoneNumber());
                readyStatement.setInt(8, customer.getCustomerID());
                readyStatement.executeUpdate();
                readyStatement.close();
                connection.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Adds a new customer to the database.
     *
     * @param customer the {@link Customer} model to be added
     * @return {@link Boolean} - true if the customer was added successfully, false - if otherwise
     */
    public boolean addCustomer(Customer customer){
        try {
            Connection connection = new Database().getConnection();
            if (connection != null) {
                String sqlQuery = "INSERT INTO customer (name,addressLine1,addressLine2,addressLine3,country,postCode,phone,createdOn,updatedOn) VALUES (?,?,?,?,?,?,?,DATE('now'),DATE('now'))";
                PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
                readyStatement.setString(1, customer.getCustomerName());
                readyStatement.setString(2, customer.getAddress().getAddressLine1());
                readyStatement.setString(3, customer.getAddress().getAddressLine2());
                readyStatement.setString(4, customer.getAddress().getAddressLine3());
                readyStatement.setString(5, customer.getAddress().getCountry());
                readyStatement.setString(6, customer.getAddress().getPostCode());
                readyStatement.setString(7, customer.getTelephoneNumber());
                readyStatement.executeUpdate();
                readyStatement.close();
                connection.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private Customer setUpCustomerFromQuery(ResultSet result) throws SQLException{
        Address address = new Address(result.getString("addressLine1"),result.getString("addressLine2"),
                result.getString("addressLine3"),result.getString("country"),
                result.getString("postCode"));
        return new Customer(result.getInt("customerId"),result.getString("name"),
                address,result.getString("phone"), Date.valueOf(result.getString("createdOn")),
                Date.valueOf(result.getString("updatedOn")));
    }
}

