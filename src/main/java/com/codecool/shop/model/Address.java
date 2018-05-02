package com.codecool.shop.model;

public class Address {

    enum addressType {
        BILLING,
        SHIPPING
    }

    private int id;
    private addressType adType;
    private String name;
    private String email;
    private String country;
    private int zip;
    private String city;
    private String address;

    private Address(addressType t) {
        this.adType = t;
    }

    public static Address getShippingAddress() {
        return new Address(addressType.SHIPPING);
    }

    public static Address getBillingAddress() {
        return new Address(addressType.BILLING);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public addressType getAdType() {
        return adType;
    }
}
