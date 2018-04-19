package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.google.common.collect.HashMultiset;
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

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("pageName", "Checkout");
        engine.process("product/checkout_form.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = new Order();
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();
        HttpSession session = req.getSession();

        String name = req.getParameter("inputName");
        String email = req.getParameter("inputEmail");
        String countryBilling = req.getParameter("inputCountry");
        String addressBilling = req.getParameter("inputAddress");
        String cityBilling = req.getParameter("inputCity");
        String zipBilling = req.getParameter("inputZip");
        String countryShipping;
        String addressShipping;
        String cityShipping;
        String zipShipping;

        //temporary condition
        if (req.getParameter("inputCountry2").equals("")) {
            countryShipping = countryBilling;
            addressShipping = addressBilling;
            cityShipping = cityBilling;
            zipShipping = zipBilling;
        } else {
            countryShipping = req.getParameter("inputCountry2");
            addressShipping = req.getParameter("inputAddress2");
            cityShipping = req.getParameter("inputCity2");
            zipShipping = req.getParameter("inputZip2");
        }

        order.setName(name);
        order.setEmail(email);
        order.setCountryBilling(countryBilling);
        order.setAddressBilling(addressBilling);
        order.setCityBilling(cityBilling);
        order.setZipBilling(Integer.parseInt(zipBilling));
        order.setCountryShipping(countryShipping);
        order.setAddressShipping(addressShipping);
        order.setZipShipping(Integer.parseInt(zipShipping));
        order.setCityShipping(cityShipping);

        Multiset<Product> list = (HashMultiset<Product>) session.getAttribute("list");
        order.setProducts(list);

        if (name.equals("")) {
            resp.sendRedirect("/checkout");
        } else {
            orderDataStore.add(order);
            resp.sendRedirect("/payment");
        }
    }
}
