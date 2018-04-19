package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp, 1 , "category");

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp, int filter_id, String filterBy) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        HttpSession session = req.getSession();

//        Map params = new HashMap<>();
//        params.put("category", productCategoryDataStore.find(1));
//        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        // add product to order object's cart multiset
        if (req.getParameter("idToAdd") != null) {
            Product productToAdd = productDataStore.find(Integer.parseInt(req.getParameter("idToAdd")));

            Multiset<Product> list= (HashMultiset<Product>) session.getAttribute("list");

            if(list==null){
                list = HashMultiset.create();
            }
            list.add(productToAdd);

            session.setAttribute("list",list);

        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariables(params);
        context.setVariable("recipient", "World");
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());
        if(filterBy.equals("category")){
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(filter_id)));
        }else if(filterBy.equals("supplier")){
            context.setVariable("products", productDataStore.getBy(supplierDataStore.find(filter_id)));
        }


        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productID = req.getParameter("id");
        if (productID != null) {
            ProductDao productDataStore = ProductDaoMem.getInstance();
            Product product = productDataStore.find(Integer.parseInt(productID));

            JSONObject productJSON = new JSONObject();
            productJSON.put("name", product.getName());
            productJSON.put("supplier", product.getSupplier().getName());

            resp.setContentType("application/json");
            resp.getWriter().print(productJSON);
        } else {
            if(!req.getParameter("filter_category").equals("")){
                doGet(req, resp, Integer.parseInt(req.getParameter("filter_category")), "category");
            }else{
                doGet(req, resp, Integer.parseInt(req.getParameter("filter_supplier")), "supplier");
            }
        }

    }
}
