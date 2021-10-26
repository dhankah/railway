package com.mospan.railway.web.command.commands.auth;

import com.mospan.railway.model.User;
import com.mospan.railway.service.UserService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        ResourceBundle ua = ResourceBundle.getBundle("i18n.resources", new Locale("ua"));
        ResourceBundle en = ResourceBundle.getBundle("i18n.resources", new Locale("en"));

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserService userService = new UserService();
        User user = userService.find(login);

        if (user == null || !user.getPassword().equals(password)) {

            if (request.getSession().getAttribute("defaultLocale").equals("ua")) {
                request.getSession().setAttribute("errorMessage", ua.getString("wrong_login_password"));
            } else {
                request.getSession().setAttribute("errorMessage", en.getString("wrong_login_password"));
            }


            return request.getContextPath() + "/auth/login";
        } else {
            request.getSession().setAttribute("user", user);
            return request.getContextPath();
        }
    }
}
