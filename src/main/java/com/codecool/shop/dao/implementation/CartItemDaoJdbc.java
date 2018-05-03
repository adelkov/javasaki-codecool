package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartItemDAO;
import com.codecool.shop.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartItemDaoJdbc implements CartItemDAO {

    @Override
    public void add(Product item, int orderId) {
        orderId = 1;
        int productId = item.getId();
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO cart_items (product_id, order_id, quantity)" +
                            "VALUES (?,?,?)" +
                            "ON CONFLICT (product_id, order_id) DO UPDATE" +
                            "SET quantitiy = cart_items.quantitiy + 1;"
            );
            statement.setInt(1, productId);
            statement.setInt(2, orderId);
            statement.setInt(3, 1);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(Product item, int orderId) {
        int productId = item.getId();
        orderId = 1;
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM cart_items WHERE product_id = ? AND order_id = ? AND quantity = 1;"
            );
            statement.setInt(1, productId);
            statement.setInt(2, orderId);

            statement.executeUpdate();

            PreparedStatement statement2 = connection.prepareStatement(
                    "UPDATE cart_items SET quantity = cart_items.quantity - 1 " +
                            "WHERE product_id = ? AND order_id = ?;"
            );
            statement2.setInt(1, productId);
            statement2.setInt(2, orderId);

            statement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<Integer, Integer> getAllByOrder(Integer orderId) {
        orderId = 1;
        Map<Integer, Integer> items = new HashMap<>();

        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM cart_items WHERE order_id = ?;"
            );
            statement.setInt(1, orderId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                items.put(rs.getInt("product_id"), rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
