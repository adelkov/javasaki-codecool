package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Address;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    final Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);

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
            logger.trace("Product with ID {} added to DB", product.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        Product row = null;
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM products WHERE id = ?;"
            );
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();
            row = getProduct(rs);
            logger.trace("Product with name {}, and ID {} found", row.getName(), row.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return row;
    }

    private Product getProduct(ResultSet rs) throws SQLException {
     /*   ProductCategoryDaoMemJdbc pc = ProductCategoryDaoMemJdbc.getInstance();
        SupplierDaoJdbc sup = SupplierDaoJdbc.getInstance();
        Product newProduct = new Product(
                rs.getString("name"), rs.getFloat("default_price"), rs.getString("default_currency"),
                "none", pc.find(rs.getInt("product_category_id")), sup.find(rs.getInt("supplier_id"))
        );
        return newProduct;*/
        return null;
    }

    @Override
    public void remove(int id) {
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "DELETE FROM products WHERE id = ?;"
            );
            stmnt.setInt(1, id);
            stmnt.executeUpdate();
            logger.trace("Product with ID {} removed from to DB", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> allProduct = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM products;"
            );
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                Product product = getProduct(rs);
                allProduct.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.trace("Returned {} products", allProduct.size());
        return allProduct;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM products WHERE supplier_id = ?;"
            );
            stmnt.setInt(1, supplier.getId());
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                Product product = getProduct(rs);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement stmnt = connection.prepareStatement(
                    "SELECT * FROM products WHERE product_category_id = ?;"
            );
            stmnt.setInt(1, productCategory.getId());
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                Product product = getProduct(rs);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
