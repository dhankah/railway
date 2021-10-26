package com.mospan.railway.controller;

import com.mospan.railway.web.command.commands.auth.LoginCommand;
import com.mospan.railway.web.command.commands.auth.LogoutCommand;
import com.mospan.railway.web.command.commands.auth.RegisterCommand;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {"/auth/*"})
public class AuthController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();

        if (path.equals("/login")) {
            req.getRequestDispatcher("/view/auth/login.jsp").forward(req, resp);
        }
        else if (path.equals("/register")) {
            req.getRequestDispatcher("/view/auth/register.jsp").forward(req, resp);
        } else {
            resp.sendError(404, "Not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        switch (path) {
            case "/login":
                resp.sendRedirect(new LoginCommand().execute(req, resp));
                break;
            case "/register":
                resp.sendRedirect(new RegisterCommand().execute(req, resp));
                break;
            case "/logout":
                resp.sendRedirect(new LogoutCommand().execute(req, resp));
                break;
            default:
                resp.sendError(404, "Not found");
                break;
        }
    }
}
