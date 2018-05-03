package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;
import java.util.Map;

public interface CartItemDAO {

    void add(Product item, int orderId);
    void remove(Product item, int orderId);

    Map<Integer, Integer> getAllByOrder(Integer orderId);
}
