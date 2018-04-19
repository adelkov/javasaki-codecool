package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.google.common.collect.Multiset;
import org.json.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

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
        HttpSession session = req.getSession();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("data", "Ok");

        engine.process("product/confirmation.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String from = "javasaki.webshop@gmail.com";
        String pw = "Paugcho1969";
        String to = "barazutti.peter@gmail.com";
        String subject = "heló";
        String msg = "Heló.";
        send(from, pw, to, subject, msg);

        /*HttpSession session = req.getSession();
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();
        Order order = orderDataStore.find(1);

        String name =  order.getName();
        String email =  order.getEmail();
        String countryBilling =  order.getCountryBilling();
        String addressBilling =  order.getAddressBilling();
        String cityBilling =  order.getCityBilling();
        String zipBilling =  order.getZipBilling();
        String countryShipping =  order.getCountryShipping();
        String addressShipping =  order.getAddressShipping();
        String zipShipping =  order.getZipShipping();
        String cityShipping =  order.getCityShipping();

        Multiset<Product> list = order.getProducts();

        JSONObject obj = new JSONObject();*/



    }

    public static void send(String from,String password,String to,String sub,String msg){
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
                        return new PasswordAuthentication(from,password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}

    }
}
