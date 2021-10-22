package com.mospan.railway.controller;


import com.mospan.railway.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (value = "/")
public class IndexController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //functional for recurrent creating of trips
      /*  Timer timer = new Timer();
        Task task = new Task();
        timer.schedule(task, 0,86400000);*/


        User user = (User) req.getSession().getAttribute("user");

        if (user != null && user.isAdmin()){
            resp.sendRedirect(req.getContextPath() + "/stations");
        } else {
            resp.sendRedirect(req.getContextPath() + "/trips");
        }
    }
}
