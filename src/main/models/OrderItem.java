/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.models;

/**
 * The Order Item model.
 * @see FoodProduct
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class OrderItem {
    private final FoodProduct product;
    private final int quantity;

    /**
     * Creates a new Order Item instance.
     *
     * @param product  the {@link FoodProduct}
     * @param quantity the quantity
     */
    public OrderItem(final FoodProduct product, final int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public FoodProduct getProduct() {
        return product;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Get total price int.
     *
     * @return the int
     */
    public int getTotalPrice(){
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}
