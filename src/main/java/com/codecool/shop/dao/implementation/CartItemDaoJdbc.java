package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartItemDAO;
import com.codecool.shop.model.Product;

import java.util.List;

public class CartItemDaoJdbc implements CartItemDAO {

    @Override
    public void add(Product item, int orderId) {

        String query = "INSERT INTO cart_items (product_id, order_id)" +
                        "Values (?, ?)";
        String[] params = {Integer.toString(item.getId()), "2"};

        DBConnector.getInstance().executeQuery(query, params);

    }

    @Override
    public void remove(Product item, int orderId) {
        String query = "INSERT INTO cart_items (product_id, order_id)" +
                "Values (?, ?)";
        String[] params = {Integer.toString(item.getId()), "2"};

        DBConnector.getInstance().executeQuery(query, params);
    }

    @Override
    public List<Product> getAllByOrder(Integer orderId) {
        return null;
    }
}
