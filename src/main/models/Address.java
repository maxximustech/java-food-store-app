/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.models;

/**
 * The Address model
 * @see main.models.Customer
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class Address {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String country;
    private String postCode;

    /**
     * Creates a new {@link main.models.Address} instance.
     *
     * @param address1 the address 1
     * @param address2 the address 2
     * @param address3 the address 3
     * @param country  the country
     * @param pc       the post code
     */
    public Address(final String address1, final String address2, final String address3, final String country, final String pc){
        this.addressLine1 = address1;
        this.addressLine2 = address2;
        this.addressLine3 = address3;
        this.country = country;
        this.postCode=pc;
    }

    /**
     * Sets post code.
     *
     * @param value new post code to be updated as
     */
    public void setPostCode(String value){
        this.postCode = value;
    }

    /**
     * Sets country.
     *
     * @param value new country to be updated as
     */
    public void setCountry(String value){
        this.country = value;
    }

    /**
     * Sets address line 3.
     *
     * @param value new address line 3 to be updated as
     */
    public void setAddressLine3(String value){
        this.addressLine3 = value;
    }

    /**
     * Sets address line 2.
     *
     * @param value new address line 2 to be updated as
     */
    public void setAddressLine2(String value){
        this.addressLine2 = value;
    }

    /**
     * Sets address line 1.
     *
     * @param value new address line 1 to be updated as
     */
    public void setAddressLine1(String value){
        this.addressLine1 = value;
    }

    /**
     * Gets address post code.
     *
     * @return {@link String}
     */
    public String getPostCode(){
        return postCode;
    }

    /**
     * Gets address country.
     *
     * @return {@link String}
     */
    public String getCountry(){
        return country;
    }

    /**
     * Gets address line 3.
     *
     * @return {@link String}
     */
    public String getAddressLine3(){
        return addressLine3;
    }

    /**
     * Get address line 2.
     *
     * @return {@link String}
     */
    public String getAddressLine2(){
        return addressLine2;
    }

    /**
     * Get address line 1.
     *
     * @return {@link String}
     */
    public String getAddressLine1(){
        return addressLine1;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", addressLine3='" + addressLine3 + '\'' +
                ", country='" + country + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
