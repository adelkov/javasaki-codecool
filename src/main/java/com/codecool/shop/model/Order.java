package com.codecool.shop.model;


import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Order {
    private int id;

    private String name;
    private String email;
    private String countryBilling;
    private int zipBilling;
    private String cityBilling;
    private String addressBilling;

    private String countryShipping;
    private int zipShipping;
    private String cityShipping;

    private Multiset<Product> productSet = HashMultiset.create();

    public Multiset<Product> getProducts() {
        return productSet;
    }

    public void setProducts(Multiset<Product> productSet) {
        this.productSet = productSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCountryBilling() {
        return countryBilling;
    }

    public void setCountryBilling(String countryBilling) {
        this.countryBilling = countryBilling;
    }

    public int getZipBilling() {
        return zipBilling;
    }

    public void setZipBilling(int zipBilling) {
        this.zipBilling = zipBilling;
    }

    public String getCityBilling() {
        return cityBilling;
    }

    public void setCityBilling(String cityBilling) {
        this.cityBilling = cityBilling;
    }

    public String getAddressBilling() {
        return addressBilling;
    }

    public void setAddressBilling(String addressBilling) {
        this.addressBilling = addressBilling;
    }

    public String getCountryShipping() {
        return countryShipping;
    }

    public void setCountryShipping(String countryShipping) {
        this.countryShipping = countryShipping;
    }

    public int getZipShipping() {
        return zipShipping;
    }

    public void setZipShipping(int zipShipping) {
        this.zipShipping = zipShipping;
    }

    public String getCityShipping() {
        return cityShipping;
    }

    public void setCityShipping(String cityShipping) {
        this.cityShipping = cityShipping;
    }

    public String getAddressShipping() {
        return addressShipping;
    }

    public void setAddressShipping(String addressShipping) {
        this.addressShipping = addressShipping;
    }

    private String addressShipping;
}
