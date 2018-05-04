package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoMemJdbc implements ProductCategoryDao {

    private List<ProductCategory> data = new ArrayList<>();
    private static ProductCategoryDaoMemJdbc instance = null;

    private ProductCategoryDaoMemJdbc() {
    }

    public static ProductCategoryDaoMemJdbc getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoMemJdbc();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory pCat) {
        try(Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "INSERT INTO product_categories (name, department, description) VALUES (?, ?, ?);"
            );
            stmnt.setString(1, pCat.getName());
            stmnt.setString(2, pCat.getDepartment());
            stmnt.setString(3, pCat.getDescription());

            stmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) {
        ProductCategory row = null;
        try(Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM product_categories WHERE id = ?;"
            );
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();

            row = new ProductCategory(
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getString("description")
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return row;
    }

    @Override
    public void remove(int id) {
        try(Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "DELETE FROM product_categories WHERE id = ?;"
            );
            stmnt.setInt(1, id);
            stmnt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> rows = new ArrayList<>();

        try(Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM product_categories;"
            );
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                ProductCategory currProductCategory = new ProductCategory(
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("description")
                );
                currProductCategory.setId(rs.getInt("id"));
                rows.add(currProductCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }
}
