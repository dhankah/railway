package com.mospan.railway.web.command.commands;

import com.mospan.railway.model.Detail;
import com.mospan.railway.model.Role;
import com.mospan.railway.model.User;
import com.mospan.railway.service.UserService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setLogin(request.getParameter("loginSignUp"));
        user.setPassword(request.getParameter("passwordSignUp"));

        Detail detail = new Detail();
        detail.setFirstName(request.getParameter("fname"));
        detail.setLastName(request.getParameter("lname"));
        detail.setEmail(request.getParameter("email"));

        user.setDetails(detail);
        user.setRole(Role.CLIENT);

        UserService userService = new UserService();
        userService.insert(user);

        return new LoginCommand().execute(request, response);
    }
}
