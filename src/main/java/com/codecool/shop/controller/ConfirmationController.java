package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.google.common.collect.Multiset;
import org.json.simple.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.FileWriter;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/confirmation"})
public class ConfirmationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();
        Order order = orderDataStore.find(orderDataStore.getAll().size());

        context.setVariable("name", order.getName());
        context.setVariable("email", order.getEmail());
        context.setVariable("country", order.getCountryBilling());
        context.setVariable("zip", order.getZipBilling());
        context.setVariable("city", order.getCityBilling());
        context.setVariable("address", order.getAddressBilling());
        context.setVariable("pageName", "Confirmation");

        engine.process("product/confirmation.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();
        Order order = orderDataStore.find(orderDataStore.getAll().size());

        String name = order.getName();
        String email = order.getEmail();
        String countryBilling = order.getCountryBilling();
        String addressBilling = order.getAddressBilling();
        String cityBilling = order.getCityBilling();
        int zipBilling = order.getZipBilling();
        String countryShipping = order.getCountryShipping();
        String addressShipping = order.getAddressShipping();
        int zipShipping = order.getZipShipping();
        String cityShipping = order.getCityShipping();

        Multiset<Product> list = order.getProducts();

        JSONObject orderJSON = new JSONObject();

        orderJSON.put("name", name);
        orderJSON.put("email", email);
        orderJSON.put("Billing country", countryBilling);
        orderJSON.put("Billing address", addressBilling);
        orderJSON.put("Billing city", cityBilling);
        orderJSON.put("Billing zip", zipBilling);
        orderJSON.put("Shipping country", countryShipping);
        orderJSON.put("Shipping address", addressShipping);
        orderJSON.put("Shipping zip", zipShipping);
        orderJSON.put("Shipping city", cityShipping);
        orderJSON.put("Product list", list);

        try (FileWriter file = new FileWriter("/home/peter/Desktop/order_json.txt")) {
            file.write(orderJSON.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + orderJSON);
        }

        String from = "javasaki.webshop@gmail.com";
        String pw = "Paugcho1969";
        String to = email;
        String subject = "Your order";
        String msg = list.toString();
        try {
            send(from, pw, to, subject, msg);
        } catch (RuntimeException e) {
            System.out.println("Ran out of internet :(");
        }

        resp.sendRedirect("/shop");
    }

    public static void send(String from, String password, String to, String sub, String msg) {
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


}
