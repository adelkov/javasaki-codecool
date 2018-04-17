package com.codecool.shop.model;


import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Order {
    private Multiset<Product> cartProducts = HashMultiset.create();
    private List<Order> orders = new ArrayList<>();
    private int id;

    public Order(){
        this.id = orders.size() + 1;
        orders.add(this);
    }

    public void addOneProduct(Product product){
        cartProducts.add(product);
    }

    public void removeOneProduct(Product product){
        cartProducts.remove(product);
    }

    public void removeProduct(Product product){
        cartProducts.remove(product, cartProducts.count(product));
    }

    public int getProductCount(Product product){
        return cartProducts.count(product);
    }

    public String getTotalPrice(){
        float totalPrice = 0;
        for (Product product:cartProducts.elementSet()){
            totalPrice += product.getDefaultPrice() * cartProducts.count(product);
        }
        return totalPrice + " USD";
    }

    public Set<Product> getProducts(){
        return cartProducts.elementSet();
    }
}
