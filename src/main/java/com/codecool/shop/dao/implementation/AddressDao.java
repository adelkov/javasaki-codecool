package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Address;

import java.util.List;

public interface AddressDao {

    void add(Address order);
    Address find(int id);
    void remove(int id);

    List<Address> getAll();
}
