package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Override
    public Supplier find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        return null;
    }
}
