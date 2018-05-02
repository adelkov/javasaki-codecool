package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Address;

import java.util.List;
import java.util.ArrayList;

public class AddressDAOMem implements AddressDao {

    private List<Address> data = new ArrayList<>();
    private static AddressDAOMem instance = null;

    private AddressDAOMem() {
        
    }

    public static AddressDAOMem getInstance() {
        if (instance == null) {
            instance = new AddressDAOMem();
        }
        return instance;
    }

    @Override
    public void add(Address address) {
        address.setId(data.size() + 1);
        data.add(address);
    }

    @Override
    public Address find(int id) {
        return data.stream().filter(add -> add.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Address> getAll() {
        return data;
    }
}
