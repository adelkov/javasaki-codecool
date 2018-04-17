package com.codecool.shop.model;


import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Multiset<Product> cartProducts = HashMultiset.create();
    private List<Order> orders = new ArrayList<>();
    private int id;

    public Order(){
        this.id = orders.size() + 1;
        orders.add(this);
    }

    public void addOneProduct(Product product){
        cartProducts.add(product, 1);
    }

    public void removeOneProduct(Product product){
        cartProducts.remove(product, 1);
    }

    public void removeProduct(Product product){
        cartProducts.remove(product);
    }

    public int getProductCount(Product product){
        return cartProducts.count(product);
    }

    public float getTotalPrice(){
        float totalPrice = 0;
        for (Product product:cartProducts.elementSet()){
            totalPrice += product.getDefaultPrice() * cartProducts.count(product);
        }
        return totalPrice;
    }
}
