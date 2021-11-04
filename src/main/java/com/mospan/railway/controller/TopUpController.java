package com.mospan.railway.controller;

import com.mospan.railway.model.User;
import com.mospan.railway.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {"/top_up/*"})
public class TopUpController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TopUpController.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/cabinet/top_up.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        user.setBalance(user.getBalance() + Double.parseDouble(req.getParameter("amount_to_add")));
        new UserService().update(user);
        resp.sendRedirect(req.getContextPath() + "/cabinet");
    }
}
