/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package test.controllers;

import test.controllers.FoodProductDAO;
import test.models.*;
import test.utils.Database;
import test.utils.Helper;

import java.sql.*;
import java.util.ArrayList;

/**
 * Order Data Access Object - Responsible for all methods relating to {@link Order} model
 * @see Order
 * @see OrderItem
 * @see Customer
 * @see FoodProduct
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class OrderDAO {
    /**
     * Finds all orders list from database.
     *
     * @return {@link Order} model
     */
    public ArrayList<Order> findAllOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        try{
            Connection con = new Database().getConnection();
            if(con == null){
                return orders;
            }
            PreparedStatement statement = con.prepareStatement("""
                SELECT orders.*, customer.*, customer.createdOn as customerCreatedOn, customer.updatedOn as customerUpdatedOn 
                FROM orders INNER JOIN customer on customer.customerId = orders.customerId ORDER BY orders.updatedOn DESC
            """);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                orders.add(setUpOrderFromQuery(result));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Finds a customer's orders from the database.
     *
     * @param customerId the customer id
     * @return {@link ArrayList} of {@link Order} model
     */
    public ArrayList<Order> findCustomerOrders(int customerId){
        ArrayList<Order> orders = new ArrayList<>();
        try{
            Connection con = new Database().getConnection();
            if(con == null){
                return orders;
            }
            PreparedStatement statement = con.prepareStatement("""
                SELECT orders.*, customer.*, customer.createdOn as customerCreatedOn, customer.updatedOn as customerUpdatedOn 
                FROM orders INNER JOIN customer on customer.customerId = orders.customerId WHERE orders.customerId = ? ORDER BY orders.updatedOn DESC
            """);
            statement.setInt(1,customerId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                orders.add(setUpOrderFromQuery(result));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Searches a single food product from database using the order number as the parameter.
     *
     * @param orderNo the order number of the order to be queried
     * @return {@link Order} model
     */
    public Order findOrder(String orderNo){
        Order order = null;
        try{
            Connection con = new Database().getConnection();
            if(con == null){
                return null;
            }
            PreparedStatement statement = con.prepareStatement("""
                SELECT orders.*, customer.*, customer.createdOn as customerCreatedOn, customer.updatedOn as customerUpdatedOn 
                FROM orders INNER JOIN customer on customer.customerId = orders.customerId 
                WHERE orders.orderNo = ?
            """);
            statement.setString(1,orderNo);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                order = setUpOrderFromQuery(result);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return order;
    }

    /**
     * Finds an order's items list from the database.
     *
     * @param orderNo the order number of the order to be queried
     * @return {@link ArrayList} of {@link OrderItem} model
     */
    public ArrayList<OrderItem> findOrderItems(String orderNo){
        ArrayList<OrderItem> items = new ArrayList<>();
        try{
            Connection con = new Database().getConnection();
            if(con == null){
                return items;
            }
            PreparedStatement statement = con.prepareStatement(" SELECT * FROM orderItem WHERE orderNo = ?");
            statement.setString(1,orderNo);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                items.add(new OrderItem(new FoodProductDAO().findProduct(result.getInt("productId")),result.getInt("quantity")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Finds a customer basket items list from the database.
     *
     * @param customerId the customer id to be queried
     * @return {@link ArrayList} of {@link OrderItem} model
     */
    public ArrayList<OrderItem> findCustomerBasketItems(int customerId){
        ArrayList<OrderItem> items = new ArrayList<>();
        try{
            Connection con = new Database().getConnection();
            if(con == null){
                return items;
            }
            PreparedStatement statement = con.prepareStatement(" SELECT * FROM basket WHERE customerId = ?");
            statement.setInt(1,customerId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                items.add(new OrderItem(new FoodProductDAO().findProduct(result.getInt("productId")),result.getInt("quantity")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Adds a new customer order to the database.
     *
     * @param customerId the customer id to be queried
     * @return {@link Boolean} - true if the order was added successfully, false - if otherwise
     */
    public boolean addOrder(int customerId){
        try{
            Connection con = new Database().getConnection();
            if(con == null){
                return false;
            }
            StringBuilder orderNo = new StringBuilder();
            boolean orderNoExists = true;
            while(orderNoExists){
                for(int i = 0; i < 4; i++){
                    orderNo.append(new Helper().generateRandomString(4));
                    if(i > 0 && i < 3){
                        orderNo.append("-");
                    }
                }
                orderNoExists = findOrder(orderNo.toString()) != null;
            }
            String sqlQuery = "INSERT INTO orders (orderNo, customerId, createdOn, updatedOn) VALUES (?,?,DATE('now'),DATE('now'))";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setString(1,orderNo.toString());
            statement.setInt(2,customerId);
            statement.execute();
            statement.close();
            for(OrderItem item : findCustomerBasketItems(customerId)){
                PreparedStatement st = con.prepareStatement("INSERT INTO orderItem (orderNo, quantity, productId) VALUES (?,?,?)");
                st.setString(1,orderNo.toString());
                st.setInt(2,item.getQuantity());
                st.setInt(3,item.getProduct().getID());
                st.execute();
                st.close();
                FoodProduct product = new FoodProductDAO().findProduct(item.getProduct().getID());
                product.setStock(product.getStock() - item.getQuantity());
                new FoodProductDAO().updateProduct(product);
            }
            sqlQuery = "DELETE FROM basket WHERE customerId = ?";
            PreparedStatement delSt = con.prepareStatement(sqlQuery);
            delSt.setInt(1,customerId);
            delSt.execute();
            delSt.close();
            con.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an order from the database.
     *
     * @param orderNo the order no to be deleted
     * @return {@link Boolean} - true if the order was deleted successfully, false - if otherwise
     */
    public boolean deleteOrder(String orderNo){
        try {
            Order order = findOrder(orderNo);
            if(order == null){
                return false;
            }
            Connection connection = new Database().getConnection();
            if (connection != null) {
                for(OrderItem item : order.getOrderItems()){
                    FoodProduct product = new FoodProductDAO().findProduct(item.getProduct().getID());
                    product.setStock(product.getStock() + item.getQuantity());
                    new FoodProductDAO().updateProduct(product);
                }
                PreparedStatement st = connection.prepareStatement("DELETE FROM orderItem WHERE orderNo = ?");
                st.setString(1,orderNo);
                st.execute();
                st.close();
                String sqlQuery = "DELETE FROM orders WHERE orderNo = ?";
                PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
                readyStatement.setString(1, orderNo);
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
     * Adds a product to customer's basket.
     *
     * @param productId  the product id
     * @param customerId the customer id
     * @return {@link Boolean} - true if the product was added to customer basket successfully, false - if otherwise
     */
    public boolean addProductToCustomerBasket(int productId, int customerId){
        try{
            Connection con = new Database().getConnection();
            if(con == null){
                return false;
            }
            ArrayList<OrderItem> basket = findCustomerBasketItems(customerId);
            boolean itemExists = false;
            int quantity = 1;
            for(OrderItem item : basket){
                if (item.getProduct().getID() == productId) {
                    itemExists = true;
                    quantity = item.getQuantity() + 1;
                    break;
                }
            }
            if(!(new FoodProductDAO().isStockEnough(productId,quantity))){
                return false;
            }
            if(!itemExists){
                String sql = "INSERT INTO basket (quantity,customerId,productId) VALUES (?,?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1,1);
                st.setInt(2,customerId);
                st.setInt(3,productId);
                st.execute();
                st.close();
            }else{
                String sql = "UPDATE basket SET quantity = ? WHERE customerId = ? AND productId = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1,quantity);
                st.setInt(2,customerId);
                st.setInt(3,productId);
                st.execute();
                st.close();
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes a product from customer's basket.
     *
     * @param productId  the product id
     * @param customerId the customer id
     * @return {@link Boolean} - true if the product was removed from customer basket successfully, false - if otherwise
     */
    public boolean removeProductFromCustomerBasket(int productId, int customerId) {
        try {
            Connection con = new Database().getConnection();
            if (con == null) {
                return false;
            }
            String sql = "DELETE FROM basket WHERE customerId = ? AND productId = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,customerId);
            st.setInt(2,productId);
            st.execute();
            st.close();
            con.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private Order setUpOrderFromQuery(ResultSet result) throws SQLException {
        String orderNo = result.getString("orderNo");
        Address address = new Address(result.getString("addressLine1"),result.getString("addressLine2"),
                result.getString("addressLine3"),result.getString("country"),
                result.getString("postCode"));
        Customer customer = new Customer(result.getInt("customerId"),result.getString("name"),
                address,result.getString("phone"), Date.valueOf(result.getString("customerCreatedOn")),
                Date.valueOf(result.getString("customerUpdatedOn")));
        Date createdOn = Date.valueOf(result.getString("createdOn"));
        Date updatedOn = Date.valueOf(result.getString("updatedOn"));
        return new Order(orderNo,customer,findOrderItems(orderNo),createdOn,updatedOn);
    }
}
