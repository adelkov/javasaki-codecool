package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;

public interface CartItemDAO {

    void add(Product item, int orderId);
    void remove(Product item, int orderId);

    List<Product> getAllByOrder(Integer orderId);
}
