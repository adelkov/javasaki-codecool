package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;

public interface CartItemDAO {

    void add(Product item);
    void remove(Product item);

    List<Product> getAllByOrder(Integer orderId);
}
