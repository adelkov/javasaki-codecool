package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Address;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDaoJdbc {
    private List<Address> data = new ArrayList<>();
    private static AddressDaoJdbc instance = null;

    private AddressDaoJdbc() {

    }

    public static AddressDaoJdbc getInstance() {
        if (instance == null) {
            instance = new AddressDaoJdbc();
        }
        return instance;
    }

    public void add(int userID, Address address) {
        try(Connection connection = DBConnector.getConnection();) {
            PreparedStatement stmnt = connection.prepareStatement(
            "INSERT INTO addresses (user_id, city, zip_code, address, country) VALUES (?, ?, ?, ?, ?)"
            );
            stmnt.setInt(1, userID);
            stmnt.setString(2, address.getCity());
            stmnt.setInt(3, address.getZip());
            stmnt.setString(4, address.getAddress());
            stmnt.setString(5, address.getCountry());

            stmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Address find(int id) {
        Address row = null;
        try(Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM addresses WHERE id = ?;"
            );
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                row = getAddress(rs);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return row;
    }

    public void remove(int id) {
        try(Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "DELETE FROM addresses WHERE id = ?;"
            );
            stmnt.setInt(1, id);
            stmnt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Address> getAll() {
        List<Address> rows = new ArrayList<>();

        try(Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM addresses;"
            );
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                Address newAddress = getAddress(rs);
                rows.add(newAddress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    @NotNull
    private Address getAddress(ResultSet rs) throws SQLException {
        Address newAddress = Address.getShippingAddress(); // TODO: We might want to check its type
        newAddress.setId(rs.getInt("id"));
        newAddress.setUserID(rs.getInt("user_id"));
        newAddress.setCity(rs.getString("city"));
        newAddress.setZip(rs.getInt("zip_code"));
        newAddress.setAddress(rs.getString("address"));
        newAddress.setCountry(rs.getString("country"));
        return newAddress;
    }
}
