/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.models;

import java.sql.Date;

/**
 * The Food product model.
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class FoodProduct {
    private int id;
    private String SKU; //stock keeping unit (a unique code for each product)
    private String description;
    private String category;
    private int price;
    private int stock;
    private Date createdOn;
    private Date updatedOn;
    private Date expiresOn;

    /**
     * Creates a new instance of {@link FoodProduct} model
     *
     * @param id          the product id
     * @param SKU         the product stock keeping unit (a unique code for each product)
     * @param description the product description
     * @param category    the product category
     * @param price       the product price
     * @param stock       the product stock
     * @param expiresOn   the product expiry date
     */
    public FoodProduct(int id, String SKU, String description, String category, int price, int stock, Date expiresOn) {
        this.id = id;
        this.SKU = SKU;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.expiresOn = expiresOn;
    }

    /**
     * Creates a new instance of {@link FoodProduct} model
     *
     * @param SKU         the product stock keeping unit (a unique code for each product)
     * @param description the product description
     * @param category    the product category
     * @param price       the product price
     * @param stock       the product stock
     * @param expiresOn   the product expiry date
     */
    public FoodProduct(String SKU, String description, String category, int price, int stock, Date expiresOn) {
        this.SKU = SKU;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.expiresOn = expiresOn;
    }

    /**
     * Creates a new instance of {@link FoodProduct} model
     *
     * @param SKU         the product stock keeping unit (a unique code for each product)
     * @param description the product description
     * @param category    the product category
     * @param price       the product price
     * @param stock       the product stock
     * @param createdOn   the product creation date
     * @param updatedOn   the product last updated date
     * @param expiresOn   the product expiry date
     */
    public FoodProduct(int id, String SKU, String description, String category, int price, int stock, Date createdOn, Date updatedOn, Date expiresOn) {
        this.id = id;
        this.SKU = SKU;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.expiresOn = expiresOn;
    }

    /**
     * Get id int.
     *
     * @return the int
     */
    public int getID(){
        return id;
    }

    /**
     * Get sku string.
     *
     * @return the string
     */
    public String getSKU(){
        return SKU;
    }

    /**
     * Get description string.
     *
     * @return the string
     */
    public String getDescription(){
        return description;
    }

    /**
     * Get category string.
     *
     * @return the string
     */
    public String getCategory(){
        return category;
    }

    /**
     * Get price int.
     *
     * @return the int
     */
    public int getPrice(){
        return price;
    }

    /**
     * Set sku.
     *
     * @param sku the sku
     */
    public void setSKU(String sku){
        this.SKU = sku;
    }

    /**
     * Set description.
     *
     * @param description the description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Set category.
     *
     * @param category the category
     */
    public void setCategory(String category){
        this.category = category;
    }

    /**
     * Set price.
     *
     * @param price the price
     */
    public void setPrice(int price){
        this.price = price;
    }

    /**
     * Gets stock.
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets stock.
     *
     * @param stock the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets expires on.
     *
     * @return the expires on
     */
    public Date getExpiresOn() {
        return expiresOn;
    }

    /**
     * Gets created on.
     *
     * @return the created on
     */
    public Date getCreatedOn() {
        return createdOn;
    }

    /**
     * Gets updated on.
     *
     * @return the updated on
     */
    public Date getUpdatedOn() {
        return updatedOn;
    }

    @Override
    public String toString() {
        return "FoodProduct{" +
                "id=" + id +
                ", SKU='" + SKU + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", expiresOn=" + expiresOn +
                '}';
    }
}