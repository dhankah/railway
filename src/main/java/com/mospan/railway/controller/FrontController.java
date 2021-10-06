package com.mospan.railway.controller;

import com.mospan.railway.dao.ConnectionPool;
import com.mospan.railway.web.command.Command;
import com.mospan.railway.web.command.CommandFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (value = "/controller")
public class FrontController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        CommandFactory commandFactory = CommandFactory.commandFactory();

        String action = request.getParameter("action");
        Command command = commandFactory.getCommand(action);
        System.out.println(action);

        String page = command.execute(request, response);
        try {
            System.out.println("page " + page);
            response.sendRedirect(page);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
