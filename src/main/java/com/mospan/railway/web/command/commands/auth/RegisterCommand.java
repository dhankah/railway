package com.mospan.railway.web.command.commands.auth;

import com.mospan.railway.model.Detail;
import com.mospan.railway.model.Role;
import com.mospan.railway.model.User;
import com.mospan.railway.service.UserService;
import com.mospan.railway.util.PasswordEncryptor;
import com.mospan.railway.util.validator.Validator;
import com.mospan.railway.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterCommand implements Command {
    Validator validator = new Validator();
    private static final Logger logger = Logger.getLogger(RegisterCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("starting registering");

        ResourceBundle rb = ResourceBundle.getBundle("i18n.resources", new Locale((String) request.getSession().getAttribute("defaultLocale")));


        User user = new User();
        user.setLogin(request.getParameter("login"));

        user.setPassword(PasswordEncryptor.hashPassword(request.getParameter("password")));
        Detail detail = new Detail();
        detail.setFirstName(request.getParameter("first_name"));
        detail.setLastName(request.getParameter("last_name"));
        detail.setEmail(request.getParameter("email"));

        user.setDetails(detail);
        user.setRole(Role.CLIENT);

        if (!validator.validateRegisterUser(user)) {
            logger.info("register failed: login or email already exists");
                request.getSession().setAttribute("errorMessage", rb.getString("login_email_exists"));

            return request.getContextPath() + "/auth/register";
        }
        UserService userService = new UserService();
        userService.insert(user);
        logger.info("register success");
        return request.getContextPath() + "/auth/login";
    }
}
