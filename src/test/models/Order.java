/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package test.models;

import test.models.Customer;
import test.models.FoodProduct;
import test.models.OrderItem;

import java.sql.Date;
import java.util.ArrayList;

/**
 * The Order model.
 * @see FoodProduct
 * @see Customer
 * @see OrderItem
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class Order {
    private final String orderNo;
    private final Customer customer;
    private final ArrayList<OrderItem> orderItems;
    private Date createdOn;
    private Date updatedOn;

    /**
     * Creates a new Order instance.
     *
     * @param orderNo    the order number
     * @param customer   the {@link Customer}
     * @param orderItems the {@link ArrayList} of {@link OrderItem}
     */
    public Order(String orderNo, Customer customer, ArrayList<OrderItem> orderItems) {
        this.orderNo = orderNo;
        this.customer = customer;
        this.orderItems = orderItems;
    }

    /**
     * Creates a new Order instance.
     *
     * @param orderNo    the order number
     * @param customer   the {@link Customer}
     * @param orderItems the {@link ArrayList} of {@link OrderItem}
     * @param createdOn  the order creation date
     * @param updatedOn  the order last updated date
     */
    public Order(String orderNo, Customer customer, ArrayList<OrderItem> orderItems, Date createdOn, Date updatedOn) {
        this.orderNo = orderNo;
        this.customer = customer;
        this.orderItems = orderItems;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    /**
     * Gets order no.
     *
     * @return the order no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets order items.
     *
     * @return the order items
     */
    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
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
        return "Order{" +
                "orderNo='" + orderNo + '\'' +
                ", customer=" + customer +
                ", orderItems=" + orderItems +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
