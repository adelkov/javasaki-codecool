package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

@WebServlet(urlPatterns = {"/compare"})
public class CompareController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("pageName", "Compare");


        Product firstProduct = null;
        Product secondProduct = null;

        String product1ID = req.getParameter("p1ID");
        String product2ID = req.getParameter("p2ID");

        if (product1ID == null || product2ID == null) {
            showError("Cannot compare items with these parameters", engine, context, resp);
        } else {
            String errorMessage = null;

            try {
                firstProduct = productDataStore.find(Integer.parseInt(product1ID));
                secondProduct = productDataStore.find(Integer.parseInt(product2ID));

                if (firstProduct == null || secondProduct == null) {
                    throw new NullPointerException();
                }

                if (firstProduct == secondProduct) {
                    throw new InvalidParameterException();
                }

                context.setVariable("product1", firstProduct);
                context.setVariable("product2", secondProduct);

                if (firstProduct.getProductCategory() == secondProduct.getProductCategory()) {
                    engine.process("product/compare.html", context, resp.getWriter());
                } else {
                    errorMessage = "The products are not from the same category!";
                }
            } catch (NullPointerException e) {
                errorMessage = "There are no such items with these IDs!";
            } catch (NumberFormatException e) {
                errorMessage = "IDs must be integers!";
            } catch (InvalidParameterException e) {
                errorMessage = "Cannot compare same items!";
            }

            if (errorMessage != null) {
                showError(errorMessage, engine, context, resp);
            }
        }
    }

    public void showError(String message, TemplateEngine engine, WebContext context, HttpServletResponse resp) throws IOException{
        context.clearVariables();
        context.setVariable("errorMessage", message);
        engine.process("error.html", context, resp.getWriter());
    }
}
