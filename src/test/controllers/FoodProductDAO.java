/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package test.controllers;

import test.models.FoodProduct;
import test.utils.Database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Food Product Data Access Object - Responsible for all methods relating to {@link FoodProduct} model
 * @see FoodProduct
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class FoodProductDAO {
    /**
     * Finds all food products list from database.
     *
     * @param fetchExpired - set {@link true} to return expired products only, set {@link false} to return all products
     * @param searchQuery  set a non-empty string to return only food products which its description or sku matches the search string, set {@link null} to return all food products
     * @return {@link ArrayList} of {@link FoodProduct} model
     */
    public ArrayList<FoodProduct> findAllProducts(boolean fetchExpired,String searchQuery){
        ArrayList<FoodProduct> products = new ArrayList<>();
        try {
            Connection connection = new Database().getConnection();
            if (connection != null) {
                String whereCondition = "";
                if(fetchExpired){
                    whereCondition = "WHERE expiresOn < DATE('now')";
                }
                if(searchQuery != null && !searchQuery.trim().isEmpty()){
                    if(fetchExpired){
                        whereCondition += " AND (description LIKE '%"+searchQuery+"%' OR SKU LIKE '%"+searchQuery+"%')";
                    }else{
                        whereCondition += " WHERE description LIKE '%"+searchQuery+"%' OR SKU LIKE '%"+searchQuery+"%'";
                    }
                }
                String sqlQuery = "SELECT * FROM foodProduct " + whereCondition + " ORDER BY updatedOn DESC";
                PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
                ResultSet queryResult = readyStatement.executeQuery();
                while (queryResult.next()) {
                    products.add(setupFoodProductFromQuery(queryResult));
                }
                queryResult.close();
                readyStatement.close();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Searches a single food product from database using the product id as the parameter.
     *
     * @param id the product id to be searched
     * @return {@link FoodProduct} model
     */
    public FoodProduct findProduct(int id){
        ArrayList<FoodProduct> products = new ArrayList<>();
        FoodProduct product = null;
        try {
            Connection connection = new Database().getConnection();
            if (connection != null) {
                String sqlQuery = "SELECT * FROM foodProduct WHERE id = ?";
                PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
                readyStatement.setInt(1, id);
                ResultSet queryResult = readyStatement.executeQuery();
                while (queryResult.next()) {
                    products.add(setupFoodProductFromQuery(queryResult));
                }
                queryResult.close();
                readyStatement.close();
                connection.close();
                if(products.size() > 0){
                    product = products.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * Deletes a single food product from the database.
     *
     * @param id the product id to be deleted
     * @return {@link Boolean} - true if the food product was deleted successfully, false - if otherwise
     */
    public boolean deleteProduct(int id){
        try {
            if(findProduct(id) == null){
                return false;
            }
            Connection connection = new Database().getConnection();
            if (connection != null) {
                String sqlQuery = "DELETE FROM foodProduct WHERE id = ?";
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
     * Updates a food product on the database.
     *
     * @param product the {@link FoodProduct} model to be updated
     * @return {@link Boolean} - true if the food product was updated successfully, false - if otherwise
     */
    public boolean updateProduct(FoodProduct product){
        try {
            if(findProduct(product.getID()) == null){
                return false;
            }
            Connection connection = new Database().getConnection();
            if (connection != null) {
                String sqlQuery = "UPDATE foodProduct SET sku = ?, description = ?,category = ?, price = ?, stock = ?, expiresOn = ?, updatedOn = DATE('now') WHERE id = ?";
                PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
                readyStatement.setString(1, product.getSKU());
                readyStatement.setString(2, product.getDescription());
                readyStatement.setString(3, product.getCategory());
                readyStatement.setInt(4, product.getPrice());
                readyStatement.setInt(5, product.getStock());
                readyStatement.setString(6, String.valueOf(product.getExpiresOn()));
                readyStatement.setInt(7, product.getID());
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
     * Adds a new food product to the database.
     *
     * @param product the {@link FoodProduct} model to be added
     * @return {@link Boolean} - true if the food product was added successfully, false - if otherwise
     */
    public boolean addProduct(FoodProduct product){
        try {
            Connection connection = new Database().getConnection();
            if (connection != null) {
                String sqlQuery = "INSERT INTO foodProduct (sku,description,category,price,stock,expiresOn,createdOn,updatedOn) VALUES (?,?,?,?,?,?,DATE('now'),DATE('now'))";
                PreparedStatement readyStatement = connection.prepareStatement(sqlQuery);
                readyStatement.setString(1, product.getSKU());
                readyStatement.setString(2, product.getDescription());
                readyStatement.setString(3, product.getCategory());
                readyStatement.setInt(4, product.getPrice());
                readyStatement.setInt(5, product.getStock());
                readyStatement.setString(6, String.valueOf(product.getExpiresOn()));
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
     * Checks if a food product has enough stock up to a given amount of quantity.
     *
     * @param id       the food product id to be queried
     * @param quantity the quantity to be checked
     * @return {@link Boolean} - true if the food product has enough stock available, false - if otherwise
     */
    public boolean isStockEnough(int id, int quantity){
        FoodProduct product = findProduct(id);
        return product != null && product.getStock() >= quantity;
    }
    private FoodProduct setupFoodProductFromQuery(ResultSet result) throws SQLException{
        int id = result.getInt("id");
        String sku = result.getString("sku");
        String desc = result.getString("description");
        String category = result.getString("category");
        int price = result.getInt("price");
        int stock = result.getInt("stock");
        Date expiresOn = Date.valueOf(result.getString("expiresOn"));
        Date createdOn = Date.valueOf(result.getString("createdOn"));
        Date updatedOn = Date.valueOf(result.getString("updatedOn"));
        return new FoodProduct(id,sku,desc,category,price,stock,createdOn,updatedOn,expiresOn);
    }
}
