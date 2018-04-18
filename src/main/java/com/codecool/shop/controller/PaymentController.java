package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("payment.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       if(req.getParameter("cardButton") != null){
           String cardHolder = req.getParameter("card-holder-name");
           String cardNumber = req.getParameter("card-number");
           int cardExpMonth = Integer.parseInt(req.getParameter("expiry-month"));
           int cardExpYear = Integer.parseInt(req.getParameter("expiry-year"));
           String cardCVV = req.getParameter("cvv");
       }else{
           String payPalUser = req.getParameter("paypal-username");
           String payPalPassword = req.getParameter("paypal-password");
       }
       resp.sendRedirect("/");
    }

}
