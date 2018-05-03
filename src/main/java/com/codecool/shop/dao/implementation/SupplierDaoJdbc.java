package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao{
    @Override
    public void add(Supplier supplier) {
        Connection connection = DBConnector.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO suppliers VALUES(?,?)");
            statement.setString(1,supplier.getName());
            statement.setString(2,supplier.getDescription());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {
        Supplier supplier = null;
        Connection connection = DBConnector.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM suppliers WHERE id = ?");
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                String supplierName = result.getString("name");
                String supplierDesc = result.getString("description");
                supplier = new Supplier(supplierName,supplierDesc);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return supplier;
    }

    @Override
    public void remove(int id) {
        Connection connection = DBConnector.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM suppliers WHERE id = ?");
            statement.setInt(1,id);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Supplier> getAll() {
        return null;
    }
}
