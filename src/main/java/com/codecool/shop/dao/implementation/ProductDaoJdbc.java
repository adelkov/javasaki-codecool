package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Address;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private static ProductDaoJdbc instance = null;

    private ProductDaoJdbc() {
    }

    public static ProductDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        try (Connection connection = DBConnector.getConnection();) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "INSERT INTO products (name, default_price, default_currency," +
                            " product_category_id, supplier_id)" +
                            "VALUES (?, ?, ?, ?, ?)"
            );
            stmnt.setString(1, product.getName());
            stmnt.setFloat(2, product.getDefaultPrice());
            stmnt.setString(3, product.getDefaultCurrency().toString());
            stmnt.setInt(4, product.getId());
            stmnt.setInt(5, product.getSupplier().getId());

            stmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
       /* Product row = null;
        try(Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM products WHERE id = ?;"
            );
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                row = getProduct(rs);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return row;*/
       return null;
    }

/*    private Product getProduct(ResultSet rs) throws SQLException {
        Product newProduct = new Product(
                rs.getString("name"), rs.getFloat("default_price"), rs.getString("default_currency"),"none", rs.getInt(" ")
        );
    }*/

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }
}
