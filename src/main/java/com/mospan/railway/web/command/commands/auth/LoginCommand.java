package com.mospan.railway.web.command.commands.auth;

import com.mospan.railway.model.User;
import com.mospan.railway.service.UserService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserService userService = new UserService();
        User user = userService.find(login);

        if (user == null || !user.getPassword().equals(password)) {
            String message = "Wrong login or password";
            request.getSession().setAttribute("errorMessage", message);

            return request.getContextPath() + "/auth/login";
        } else {
            request.getSession().setAttribute("user", user);
            return request.getContextPath();
        }
    }
}
