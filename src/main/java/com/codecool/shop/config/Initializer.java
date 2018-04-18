package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;

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

    enum products {
        EBLOCK1(new Product("S&S Cycle T143 Long Block", 9749.95f, "USD", "Engine block specially machined for HD Dyna models", productCategories.ENGINE.getCategory(), suppliers.SSCYCLE.getSupplier())),
        EBLOCK2(new Product("S&S Cycle V80 Long Block", 4299.95f, "USD", "Replacement engine block for aging 80\" H-D Evolution engines", productCategories.ENGINE.getCategory(), suppliers.SSCYCLE.getSupplier())),
        WHEEL1(new Product("Performance Machine Platinum Cut Supra Front Wheel", 1259.96f, "USD", "21 in. x 3.5 in. for Models w/o ABS (dual disc)", productCategories.WHEEL.getCategory(), suppliers.PMACHINES.getSupplier())),
        WHEEL2(new Product("Drag Specialties Rear Laced Wheel", 359.95f, "USD", "Chrome 16x3 40-Spoke Laced Wheel Assembly", productCategories.WHEEL.getCategory(), suppliers.DRAGS.getSupplier())),
        WHEEl3(new Product("Performance Machine Platinum Cut Front Aluminum Wheel", 1439.96f, "USD", "Platinum Cut 21 x 3.5 Revel One-Piece Chrome-Forged", productCategories.WHEEL.getCategory(), suppliers.PMACHINES.getSupplier())),
        EXH1(new Product("Rinehart Black Slip-On Mufflers", 739.95f, "USD", "4 in. Slip-On Mufflers w/Slot End Caps", productCategories.EXHAUST.getCategory(), suppliers.RINEHART.getSupplier())),
        EXH2(new Product("Rinehart Chrome Slip-On Mufflers", 379.95f, "USD", "3 in. Slip-On Mufflers w/Black Straight End Caps", productCategories.EXHAUST.getCategory(), suppliers.RINEHART.getSupplier())),
        EXH3(new Product("Helix Racing Products Muffler Clamp", 26.95f, "USD", "Stainless Steel 4 in. x 5 in. Oval Muffler Clamp", productCategories.EXHAUST.getCategory(), suppliers.HELIX.getSupplier())),
        FRAME1(new Product("Paughco Frame - S147T", 1099.76f, "USD", "Straight Leg Swingarm Frame", productCategories.CHASSIS.getCategory(), suppliers.PAUGHCO.getSupplier())),
        FRAME2(new Product("Paughco Frame - SLP139S5", 1271.95f, "USD", "Single Loop Rigid Bobber Fork Frame", productCategories.CHASSIS.getCategory(), suppliers.PAUGHCO.getSupplier())),
        FRAME3(new Product("Kraft Tech Frame - K16001", 719.88f, "USD", "Rigid Frame for Big Twin w/130 Tire", productCategories.CHASSIS.getCategory(), suppliers.KRAFT.getSupplier())),
        FRAME4(new Product("Kraft Tech Frame - K15161", 924.88f, "USD", "Rigid Frame for 180 Rear Tire", productCategories.CHASSIS.getCategory(), suppliers.KRAFT.getSupplier())),
        FRAME5(new Product("Paughco Frame Kit - R147FXR", 1524.56f, "USD", "Stock Style FXR Frame Kit", productCategories.CHASSIS.getCategory(), suppliers.PAUGHCO.getSupplier())),
        SUIT1(new Product("Alpinestar GP Plus", 1999.95f, "USD", "1-Piece Leather Racing Suit", productCategories.SAFETYGEAR.getCategory(), suppliers.ALPINES.getSupplier())),
        SUIT2(new Product("Cortech Latigo 2.0", 599.99f, "USD", "Race-ready 1-piece Leather Suit", productCategories.SAFETYGEAR.getCategory(), suppliers.CORTECH.getSupplier())),
        GLOVE1(new Product("Alpinestar Techstar", 44.95f, "USD", "Motorcycle gloves", productCategories.SAFETYGEAR.getCategory(), suppliers.ALPINES.getSupplier()));

        Product product;

        products(Product prod){
            this.product = prod;
        }

        public Product getProduct() {
            return this.product;
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        setupSuppliers(supplierDataStore);

        setupProductCategories(productCategoryDataStore);

        setupProducts(productDataStore);

        setupProductAttributes();
    }

    private void setupSuppliers(SupplierDao supplierDataStore) {
        //setting up a new supplier
        for (suppliers supp : suppliers.values()){
            supplierDataStore.add(supp.getSupplier());
        }
    }

    private void setupProductCategories(ProductCategoryDao productCategoryDataStore) {
        //setting up a new product category
        for (productCategories pCat : productCategories.values()){
            productCategoryDataStore.add(pCat.getCategory());
        }

        productCategories.ENGINE.getCategory().setMandatoryAttribute(AttributeFactory.newInstanceOfEngineAttribute());
        productCategories.WHEEL.getCategory().setMandatoryAttribute(AttributeFactory.newInstanceOfWheelAttribute());
        productCategories.EXHAUST.getCategory().setMandatoryAttribute(AttributeFactory.newInstanceOfExhaustAttribute());
        productCategories.CHASSIS.getCategory().setMandatoryAttribute(AttributeFactory.newInstanceOfChassisAttribute());
        productCategories.SAFETYGEAR.getCategory().setMandatoryAttribute(AttributeFactory.newInstanceOfSafetyGearAttribute());
    }

    private void setupProducts(ProductDao productDataStore) {
        //setting up products and printing it
        for (products product : products.values()) {
            productDataStore.add(product.getProduct());
        }
    }

    private void setupProductAttributes() {
        HashMap<MandatoryAttribute.allAttributes, String> engine1Attrib = new HashMap<>();
        engine1Attrib.put(MandatoryAttribute.allAttributes.HP, "70");
        engine1Attrib.put(MandatoryAttribute.allAttributes.EXHAUST_TUBE_NUMBER, "2");
        engine1Attrib.put(MandatoryAttribute.allAttributes.WEIGHT, "75 lbs");
        engine1Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Black");

        HashMap<MandatoryAttribute.allAttributes, String> engine2Attrib = new HashMap<>();
        engine2Attrib.put(MandatoryAttribute.allAttributes.HP, "65");
        engine2Attrib.put(MandatoryAttribute.allAttributes.EXHAUST_TUBE_NUMBER, "2");
        engine2Attrib.put(MandatoryAttribute.allAttributes.WEIGHT, "77 lbs");
        engine2Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Grey");
        HashMap<MandatoryAttribute.allAttributes, String> wheel1Attrib = new HashMap<>();
        wheel1Attrib.put(MandatoryAttribute.allAttributes.DIAMETER, "21 in.");
        wheel1Attrib.put(MandatoryAttribute.allAttributes.MATERIAL, "Platinum");
        wheel1Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Black");
        wheel1Attrib.put(MandatoryAttribute.allAttributes.TYPE, "1-Piece");
        wheel1Attrib.put(MandatoryAttribute.allAttributes.POSITION, "Front");

        HashMap<MandatoryAttribute.allAttributes, String> wheel2Attrib = new HashMap<>();
        wheel2Attrib.put(MandatoryAttribute.allAttributes.DIAMETER, "16 in.");
        wheel2Attrib.put(MandatoryAttribute.allAttributes.MATERIAL, "Platinum");
        wheel2Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Chrome");
        wheel2Attrib.put(MandatoryAttribute.allAttributes.TYPE, "40-Spoke Laced");
        wheel2Attrib.put(MandatoryAttribute.allAttributes.POSITION, "Front");

        HashMap<MandatoryAttribute.allAttributes, String> wheel3Attrib = new HashMap<>();
        wheel3Attrib.put(MandatoryAttribute.allAttributes.DIAMETER, "21 in.");
        wheel3Attrib.put(MandatoryAttribute.allAttributes.MATERIAL, "Platinum");
        wheel3Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Black");
        wheel3Attrib.put(MandatoryAttribute.allAttributes.TYPE, "1-Piece");
        wheel3Attrib.put(MandatoryAttribute.allAttributes.POSITION, "Front");

        HashMap<MandatoryAttribute.allAttributes, String> exh1Attrib = new HashMap<>();
        exh1Attrib.put(MandatoryAttribute.allAttributes.TYPE, "Slip-on muffler");
        exh1Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Black");
        exh1Attrib.put(MandatoryAttribute.allAttributes.DIAMETER, "4 in.");
        exh1Attrib.put(MandatoryAttribute.allAttributes.LENGTH, "16 in.");

        HashMap<MandatoryAttribute.allAttributes, String> exh2Attrib = new HashMap<>();
        exh2Attrib.put(MandatoryAttribute.allAttributes.TYPE, "Slip-on muffler");
        exh2Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Black");
        exh2Attrib.put(MandatoryAttribute.allAttributes.DIAMETER, "3 in.");
        exh2Attrib.put(MandatoryAttribute.allAttributes.LENGTH, "14 in.");

        HashMap<MandatoryAttribute.allAttributes, String> exh3Attrib = new HashMap<>();
        exh3Attrib.put(MandatoryAttribute.allAttributes.TYPE, "Muffler clamp");
        exh3Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Black");
        exh3Attrib.put(MandatoryAttribute.allAttributes.DIAMETER, "4 in.");
        exh3Attrib.put(MandatoryAttribute.allAttributes.LENGTH, "5 in.");

        HashMap<MandatoryAttribute.allAttributes, String> frame1Attrib = new HashMap<>();
        frame1Attrib.put(MandatoryAttribute.allAttributes.LENGTH, "60 in.");
        frame1Attrib.put(MandatoryAttribute.allAttributes.TYPE, "Straight leg");
        frame1Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Black");

        HashMap<MandatoryAttribute.allAttributes, String> frame2Attrib = new HashMap<>();
        frame2Attrib.put(MandatoryAttribute.allAttributes.LENGTH, "68 in.");
        frame2Attrib.put(MandatoryAttribute.allAttributes.TYPE, "Single loop rigid");
        frame2Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Silver");

        HashMap<MandatoryAttribute.allAttributes, String> frame3Attrib = new HashMap<>();
        frame3Attrib.put(MandatoryAttribute.allAttributes.LENGTH, "65 in.");
        frame3Attrib.put(MandatoryAttribute.allAttributes.TYPE, "Rigid");
        frame3Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Silver");

        HashMap<MandatoryAttribute.allAttributes, String> frame4Attrib = new HashMap<>();
        frame4Attrib.put(MandatoryAttribute.allAttributes.LENGTH, "65 in.");
        frame4Attrib.put(MandatoryAttribute.allAttributes.TYPE, "Rigid");
        frame4Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Silver");

        HashMap<MandatoryAttribute.allAttributes, String> frame5Attrib = new HashMap<>();
        frame5Attrib.put(MandatoryAttribute.allAttributes.LENGTH, "63 in.");
        frame5Attrib.put(MandatoryAttribute.allAttributes.TYPE, "Stock style FXR");
        frame5Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Silver");

        HashMap<MandatoryAttribute.allAttributes, String> gear1Attrib = new HashMap<>();
        gear1Attrib.put(MandatoryAttribute.allAttributes.COLOR, "White/Black/Red");
        gear1Attrib.put(MandatoryAttribute.allAttributes.MATERIAL, "Leather");
        gear1Attrib.put(MandatoryAttribute.allAttributes.TYPE, "1-Piece Suit");

        HashMap<MandatoryAttribute.allAttributes, String> gear2Attrib = new HashMap<>();
        gear2Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Black/White/Red");
        gear2Attrib.put(MandatoryAttribute.allAttributes.MATERIAL, "Leather");
        gear2Attrib.put(MandatoryAttribute.allAttributes.TYPE, "1-Piece Suit");

        HashMap<MandatoryAttribute.allAttributes, String> gear3Attrib = new HashMap<>();
        gear3Attrib.put(MandatoryAttribute.allAttributes.COLOR, "Black/Red");
        gear3Attrib.put(MandatoryAttribute.allAttributes.MATERIAL, "Leather");
        gear3Attrib.put(MandatoryAttribute.allAttributes.TYPE, "Glove");

        try {
            products.EBLOCK1.getProduct().setProductAttributes(engine1Attrib);
            products.EBLOCK2.getProduct().setProductAttributes(engine2Attrib);
            products.WHEEL1.getProduct().setProductAttributes(wheel1Attrib);
            products.WHEEL2.getProduct().setProductAttributes(wheel2Attrib);
            products.WHEEl3.getProduct().setProductAttributes(wheel3Attrib);
            products.EXH1.getProduct().setProductAttributes(exh1Attrib);
            products.EXH2.getProduct().setProductAttributes(exh2Attrib);
            products.EXH3.getProduct().setProductAttributes(exh3Attrib);
            products.FRAME1.getProduct().setProductAttributes(frame1Attrib);
            products.FRAME2.getProduct().setProductAttributes(frame2Attrib);
            products.FRAME3.getProduct().setProductAttributes(frame3Attrib);
            products.FRAME4.getProduct().setProductAttributes(frame4Attrib);
            products.FRAME5.getProduct().setProductAttributes(frame5Attrib);
            products.SUIT1.getProduct().setProductAttributes(gear1Attrib);
            products.SUIT2.getProduct().setProductAttributes(gear2Attrib);
            products.GLOVE1.getProduct().setProductAttributes(gear3Attrib);
        } catch (FailedMandatoryKeys e) {
            System.out.println(e);
            System.exit(1);
        }
    }
}
