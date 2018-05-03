package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemJdbcTest {

    private ProductCategoryDaoMemJdbc productCategories;

    @BeforeEach
    void initProductCategories() {
        productCategories = ProductCategoryDaoMemJdbc.getInstance();
    }

    @Test
    void testAddingProductCategory() {
        int rowsBefore = productCategories.getAll().size();
        productCategories.add(new ProductCategory("Test", "Test", "Testing purpose"));
        int rowsAfter = productCategories.getAll().size();
        assertEquals(1, rowsAfter - rowsBefore);
    }

    @Test
    void testRemovingProductCategory() {
        productCategories.add(new ProductCategory("Test", "Test", "Test"));
        int rowsBefore = productCategories.getAll().size();
        int lastID = productCategories.getAll().get(rowsBefore - 1).getId();
        productCategories.remove(lastID);
        int rowsAfter = productCategories.getAll().size();

        assertEquals(1, rowsBefore - rowsAfter);
    }
}
