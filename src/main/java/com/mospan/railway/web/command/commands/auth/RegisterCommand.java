package com.mospan.railway.web.command.commands.auth;

import com.mospan.railway.model.Detail;
import com.mospan.railway.model.Role;
import com.mospan.railway.model.User;
import com.mospan.railway.service.UserService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));

        Detail detail = new Detail();
        detail.setFirstName(request.getParameter("first_name"));
        detail.setLastName(request.getParameter("last_name"));
        detail.setEmail(request.getParameter("email"));

        user.setDetails(detail);
        user.setRole(Role.CLIENT);

        UserService userService = new UserService();
        userService.insert(user);

        return request.getContextPath() + "/auth/login";
    }
}
