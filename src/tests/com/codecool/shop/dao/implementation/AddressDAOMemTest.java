package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Address;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressDAOMemTest {

    @Test
    void testForMultipleInstances() {
        AddressDAOMem addresses = AddressDAOMem.getInstance();
        assertEquals(addresses, AddressDAOMem.getInstance());
    }

    @RepeatedTest(5)
    void testAdding() {
        Address Badd = Address.getBillingAddress();
        AddressDAOMem addresses = AddressDAOMem.getInstance();
        int ln = addresses.getAll().size();
        addresses.add(Badd);
        assertEquals(ln + 1, addresses.getAll().size());
    }

    @Test
    void testLengthForRemoving() {
        Address Badd = Address.getBillingAddress();
        AddressDAOMem addresses = AddressDAOMem.getInstance();
        addresses.add(Badd);
        int ln = addresses.getAll().size();
        addresses.remove(ln);
        assertEquals(ln - 1, addresses.getAll().size());
    }

    @Test
    void testRemoving() {
        Address Badd = Address.getBillingAddress();
        AddressDAOMem addresses = AddressDAOMem.getInstance();
        addresses.getAll().clear();
        for (int i = 0; i < 5; i++) {
            addresses.add(Address.getShippingAddress());
        }
        addresses.add(Badd);
        addresses.remove(6);
        assertNull(addresses.find(6));
    }

    @Test
    void testFinding() {
        Address Badd = Address.getBillingAddress();
        Badd.setCountry("Hungary");
        AddressDAOMem addresses = AddressDAOMem.getInstance();
        addresses.getAll().clear();

        for (int i = 0; i < 10; i++) {
            if (i == 1) {
                addresses.add(Badd);
            } else {
                addresses.add(Address.getShippingAddress());
            }
        }

        assertEquals(Badd, addresses.find(2));
    }
}
