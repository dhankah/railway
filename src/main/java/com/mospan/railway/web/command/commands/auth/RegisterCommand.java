package com.mospan.railway.web.command.commands.auth;

import com.mospan.railway.model.Detail;
import com.mospan.railway.model.Role;
import com.mospan.railway.model.User;
import com.mospan.railway.service.UserService;
import com.mospan.railway.validator.Validator;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterCommand implements Command {
    Validator validator = new Validator();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {


        ResourceBundle ua = ResourceBundle.getBundle("i18n.resources", new Locale("ua"));
        ResourceBundle en = ResourceBundle.getBundle("i18n.resources", new Locale("en"));

        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));

        Detail detail = new Detail();
        detail.setFirstName(request.getParameter("first_name"));
        detail.setLastName(request.getParameter("last_name"));
        detail.setEmail(request.getParameter("email"));

        user.setDetails(detail);
        user.setRole(Role.CLIENT);

        if (!validator.validateUser(user)) {

            if (request.getSession().getAttribute("defaultLocale").equals("ua")) {
                request.getSession().setAttribute("errorMessage", ua.getString("login_email_exists"));
            } else {
                request.getSession().setAttribute("errorMessage", en.getString("login_email_exists"));
            }

            return request.getContextPath() + "/auth/register";
        }
        UserService userService = new UserService();
        userService.insert(user);
        return request.getContextPath() + "/auth/login";
    }
}
