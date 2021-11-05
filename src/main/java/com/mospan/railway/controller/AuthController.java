package com.mospan.railway.controller;

import com.mospan.railway.model.User;
import com.mospan.railway.service.DetailService;
import com.mospan.railway.service.UserService;
import com.mospan.railway.util.EmailSender;
import com.mospan.railway.util.PasswordEncryptor;
import com.mospan.railway.web.command.commands.auth.LoginCommand;
import com.mospan.railway.web.command.commands.auth.LogoutCommand;
import com.mospan.railway.web.command.commands.auth.RegisterCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(value = {"/auth/*"})
public class AuthController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AuthController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) req.getSession().getAttribute("defaultLocale")));

        String path = req.getPathInfo();


        if (path.equals("/login")) {
            logger.info("forwarding to the login page");
            req.getRequestDispatcher("/view/auth/login.jsp").forward(req, resp);
        } else if (path.equals("/register")) {
            logger.info("forwarding to the register page");
            req.getRequestDispatcher("/view/auth/register.jsp").forward(req, resp);
        } else if (path.equals("/reset_password_email_form")) {
            req.getRequestDispatcher("/view/password_reset/email.jsp").forward(req, resp);
        } else if (path.equals("/reset_password_email")) {
            if (null == new DetailService().find(req.getParameter("email"))) {
                req.getSession().setAttribute("errorMessage", rb.getString("no_user_with_such_email"));
                req.getRequestDispatcher("/view/password_reset/email.jsp").forward(req, resp);
                return;
            }
            req.getSession().setAttribute("email", req.getParameter("email"));
            EmailSender.sendUserNotification(req.getParameter("email"), "password_reset");
            req.getSession().setAttribute("message", rb.getString("sent_email"));
            req.getRequestDispatcher("/view/auth/login.jsp").forward(req, resp);
        } else if (path.equals("/reset_password")) {
            req.getRequestDispatcher("/view/password_reset/reset_password.jsp").forward(req, resp);
        } else {
            resp.sendError(404, "Not found");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (null != req.getParameter("_method")) {
            doPut(req, resp);
            return;
        }


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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) req.getSession().getAttribute("defaultLocale")));
        User user = new UserService().findByEmail((String) req.getSession().getAttribute(("email")));
        if (PasswordEncryptor.hashPassword(req.getParameter("password1")).equals((PasswordEncryptor.hashPassword(req.getParameter("password2"))))) {
            user.setPassword(PasswordEncryptor.hashPassword(req.getParameter("password1")));
            new UserService().update(user);
            logger.info("password for user successfully reset");
            req.getSession().setAttribute("message", rb.getString("password_reset"));
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        } else {
            logger.info("password reset for user failed");
            req.getSession().setAttribute("errorMessage", rb.getString("password_not_reset"));
            resp.sendRedirect(req.getContextPath() + "/auth/reset_password");
        }
    }
}