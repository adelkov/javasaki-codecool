package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductsDaoJdbcTest {

    private ProductDaoJdbc products;

    @BeforeEach
    void initProducts() {
        products = ProductDaoJdbc.getInstance();
    }

    @Test
    void testAddingProduct() {
        ProductCategory pc = new ProductCategory("Engine block", "Drive",
                "Engine is the part which makes your motorcycle move");
        Supplier sup = new Supplier("S&S Cycle",
                "Leader in performance motorcycle parts for Harley-Davidson");
        int rowsBefore = products.getAll().size();
        products.add(new Product("Test", 10, "USD", "none",
                pc, sup));
        int rowsAfter = products.getAll().size();
        assertEquals(1, rowsAfter - rowsBefore);
    }

    @Test
    void testRemovingProduct() {
        ProductCategory pc = new ProductCategory("Engine block", "Drive",
                "Engine is the part which makes your motorcycle move");
        Supplier sup = new Supplier("S&S Cycle",
                "Leader in performance motorcycle parts for Harley-Davidson");
        products.add(new Product("Test", 10, "USD", "none",
                pc, sup));
        int rowsBefore = products.getAll().size();
        int lastID = products.getAll().get(rowsBefore - 1).getId();
        products.remove(lastID);
        int rowsAfter = products.getAll().size();

        assertEquals(1, rowsBefore - rowsAfter);
    }
}
