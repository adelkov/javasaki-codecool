package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Address;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressDaoJdbcTest {

    @Test
    void testGettingAll() {
        int rowCount = 0;
        try(Connection connection = DBConnector.getConnection())
        {
            PreparedStatement stmnt = connection.prepareStatement("SELECT COUNT(id) FROM addresses;");
            ResultSet rs = stmnt.executeQuery();

            while(rs.next()) {
                rowCount = rs.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        AddressDaoJdbc addresses = AddressDaoJdbc.getInstance();
        assertEquals(rowCount, addresses.getAll().size());
    }

    @Test
    void testAdding(){
        Address shipAdd = Address.getShippingAddress();
        shipAdd.setName("Alex");
        shipAdd.setCity("Békásmegyer");
        shipAdd.setZip(1039);
        shipAdd.setAddress("Noname St. 6");
        shipAdd.setCountry("Hungary");

        AddressDaoJdbc addresses = AddressDaoJdbc.getInstance();
        int ln = addresses.getAll().size();
        addresses.add(1, shipAdd);
        assertEquals(ln + 1, addresses.getAll().size());
    }

    @Test
    void testLengthForDeleting() {
        AddressDaoJdbc addresses = AddressDaoJdbc.getInstance();
        Address shipAdd = Address.getShippingAddress();
        shipAdd.setCity("Budapest");
        shipAdd.setAddress("Street St. 4");

        addresses.add(1, shipAdd);
        int ln = addresses.getAll().size();
        int lastID = addresses.getAll().get(ln-1).getId();
        addresses.remove(lastID);
        assertEquals(ln - 1, addresses.getAll().size());
    }

    @Test
    void testFindForNonExistingRow() {
        AddressDaoJdbc addresses = AddressDaoJdbc.getInstance();
        Address address = addresses.find(-1);
        assertNull(address);
    }

    @Test
    void testFindForExistingRow() {
        AddressDaoJdbc addresses = AddressDaoJdbc.getInstance();
        List<Address> addressList = addresses.getAll();
        int lastID = addressList.get(addressList.size() - 1).getId();

        assertAll(
                () -> assertNotNull(addresses.find(lastID)),
                () -> assertEquals(addressList.get(addressList.size() - 1).getId(), addresses.find(lastID).getId())
        );
    }

    @Test
    void testGetAddressByUserID() {
        AddressDaoJdbc addresses = AddressDaoJdbc.getInstance();
        Address shipAdd = Address.getShippingAddress();
        shipAdd.setCity("Budapest");
        shipAdd.setAddress("Nagymező St.");

        addresses.add(1, shipAdd);
        List<Address> addressList = addresses.getAll();
        int newID = addressList.get(addressList.size() - 1).getId();

        List<Address> filteredRows = addresses.getAddressesByUserID(1);

        boolean correctRows = true;
        int i = 0;
        while (i < filteredRows.size() && correctRows) {
            if (filteredRows.get(i++).getUserID() != 1) correctRows = false;
        }

        assertTrue(correctRows);

        addresses.remove(newID);
    }
}
