package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartItemDAO;
import com.codecool.shop.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CartItemDaoJdbc implements CartItemDAO {

    @Override
    public void add(Product item, int orderId) {
        orderId = 1;
        int productId = item.getId();
        try (Connection connection = DBConnector.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO cart_items (product_id, order_id, quantity)" +
                            "VALUES (?,?,?)" +
                            "ON CONFLICT (product_id, order_id) DO UPDATE" +
                            "SET quantitiy = cart_items.quantitiy + 1;"
            );
            statement.setInt(1, productId);
            statement.setInt(2, orderId);
            statement.setInt(3, 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(Product item, int orderId) {
    }

    @Override
    public List<Product> getAllByOrder(Integer orderId) {
        return null;
    }
}
