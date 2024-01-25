/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package test.models;

import test.models.Address;

import java.sql.Date;

/**
 * The Customer model.
 * @see test.models.Address
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class Customer {
    private int customerID;
    private String customerName;
    private test.models.Address address;
    private String telephoneNumber;
    private Date createdOn;
    private Date updatedOn;

    /**
     * Creates a new {@link test.models.Customer} instance.
     *
     * @param id   the customer id
     * @param name the customer name
     * @param ad   the customer address
     * @param tel  the customer telephone
     */
    public Customer(final int id, final String name, final test.models.Address ad, final String tel){
        this.customerID = id;
        this.customerName = name;
        this.address = ad;
        this.telephoneNumber = tel;
    }

    /**
     * Creates a new {@link test.models.Customer} instance.
     *
     * @param name the customer name
     * @param ad   the customer address
     * @param tel  the customer telephone
     */
    public Customer(final String name, final test.models.Address ad, final String tel){
        this.customerName = name;
        this.address = ad;
        this.telephoneNumber = tel;
    }

    /**
     * Creates a new {@link test.models.Customer} instance.
     *
     * @param customerID      the customer id
     * @param customerName    the customer name
     * @param address         the customer address
     * @param telephoneNumber the customer telephone number
     * @param createdOn       the customer creation date
     * @param updatedOn       the customer's last updated date
     */
    public Customer(int customerID, String customerName, test.models.Address address, String telephoneNumber, Date createdOn, Date updatedOn) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    /**
     * Gets customer id.
     *
     * @return {@link Integer}
     */
    public int getCustomerID(){
        return customerID;
    }

    /**
     * Gets customer's name.
     *
     * @return {@link String}
     */
    public String getCustomerName(){
        return customerName;
    }

    /**
     * Gets customer address.
     *
     * @return {@link test.models.Address}
     */
    public test.models.Address getAddress(){
        return address;
    }

    /**
     * Gets customer's telephone number.
     *
     * @return {@link String}
     */
    public String getTelephoneNumber(){
        return telephoneNumber;
    }

    /**
     * Sets customer's name.
     *
     * @param value new value
     */
    public void setCustomerName(String value){
        this.customerName = value;
    }

    /**
     * Sets customer's address.
     *
     * @param value new value
     */
    public void setAddress(Address value){
        this.address = value;
    }

    /**
     * Sets customer's telephone number.
     *
     * @param value new value
     */
    public void setTelephoneNumber(String value){
        this.telephoneNumber =value;
    }

    /**
     * Gets customer's creation date.
     *
     * @return {@link Date}
     */
    public Date getCreatedOn() {
        return createdOn;
    }

    /**
     * Gets customer's last updated date.
     *
     * @return {@link Date}
     */
    public Date getUpdatedOn() {
        return updatedOn;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", customerName='" + customerName + '\'' +
                ", address=" + address +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}