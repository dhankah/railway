package com.mospan.railway.web.command.commands.auth;

import com.mospan.railway.controller.IndexController;
import com.mospan.railway.model.User;
import com.mospan.railway.service.UserService;
import com.mospan.railway.util.PasswordEncryptor;
import com.mospan.railway.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand implements Command {

    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) request.getSession().getAttribute("defaultLocale")));


        String login = request.getParameter("login");
        String password = PasswordEncryptor.hashPassword(request.getParameter("password"));

        UserService userService = new UserService();
        User user = userService.find(login);

        if (user == null || !user.getPassword().equals(password)) {
            logger.info("logging in failure: wrong login or password");

            request.getSession().setAttribute("errorMessage", rb.getString("wrong_login_password"));



            return request.getContextPath() + "/auth/login";
        } else {
            logger.info("logging in success for user " + user.getLogin());
            request.getSession().setAttribute("user", user);
            return request.getContextPath();
        }
    }
}
