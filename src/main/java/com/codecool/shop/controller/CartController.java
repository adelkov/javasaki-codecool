package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.google.common.collect.Multiset;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        HttpSession session = req.getSession();
        Multiset<Product> list= (Multiset<Product>) session.getAttribute("list");

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());


        if (req.getParameter("idToAdd") != null) {
            Product prod = productDataStore.find(Integer.parseInt(req.getParameter("idToAdd")));
            list.add(prod);
        }
        if (req.getParameter("idToRemove") != null) {
            Product prod = productDataStore.find(Integer.parseInt(req.getParameter("idToRemove")));
            list.remove(prod);
        }
        if (req.getParameter("idToDelete") != null) {
            Product prod = productDataStore.find(Integer.parseInt(req.getParameter("idToDelete")));
            list.remove(prod, list.count(prod));
        }


        context.setVariable("cartList", list);
        context.setVariable("total",getSum(list));
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.find(1));
        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        engine.process("product/cart.html", context, resp.getWriter());
    }

    @org.jetbrains.annotations.NotNull
    private String getSum(Multiset<Product> list){
        float total = 0;
        for(Product product:list){
            total += product.getDefaultPrice();
        }
        return total + " USD";
    }

}
