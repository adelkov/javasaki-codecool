package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    enum productCategories {
        ENGINE(new ProductCategory("Engine block", "Drive", "Engine is the part which makes your motorcycle move")),
        WHEEL(new ProductCategory("Wheel", "Drive", "Wheels, the rubber toruses make contact with the road surface.")),
        EXHAUST(new ProductCategory("Exhaust", "Drive", "Gases from the engine makes its way out through the exhaust.")),
        CHASSIS(new ProductCategory("Chassis", "Body frame", "The body of the motorcycle is mounted on the chassis")),
        SAFETYGEAR(new ProductCategory("Safety gear", "Safety apparel", "These clothes save you in collision"));

        ProductCategory category;

        productCategories(ProductCategory pCat) {
            this.category = pCat;
        }

        public ProductCategory getCategory(){
            return this.category;
        }
    }

    enum suppliers {
        SSCYCLE(new Supplier("S&S Cycle", "Leader in performance motorcycle parts for Harley-Davidson")),
        HELIX(new Supplier("Helix Racing Products", "Wholesale distributor of premium powersports equipment and tools")),
        RINEHART(new Supplier("RINEHART", "Performance aftermarket exhaust systems")),
        PMACHINES(new Supplier("Performance Machines", "Premium Chrome and Black Anodized Custom Motorcycle Forged Wheels")),
        DRAGS(new Supplier("Drag Specialties", "High-quality parts for Harleys and V-Twins since 1968")),
        PAUGHCO(new Supplier("Paugcho", "Finest int american-made products since 1969")),
        KRAFT(new Supplier("Kraft Tech", "Quality motorcycle parts and accessories since 1990")),
        ALPINES(new Supplier("Alpinestars", "World-leading manufacturer of professional racing products, motorcycling airbag protection, high-performance apparel, and technical footwear")),
        CORTECH(new Supplier("Cortech", "Enhanced performance within the most demanding categories of riding"));

        Supplier supplier;

        suppliers(Supplier sup){
            this.supplier = sup;
        }

        public Supplier getSupplier() {
            return this.supplier;
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        for (suppliers supp : suppliers.values()){
            supplierDataStore.add(supp.getSupplier());
        }

        //setting up a new product category
        for (productCategories pCat : productCategories.values()){
            productCategoryDataStore.add(pCat.getCategory());
        }

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
    }
}
