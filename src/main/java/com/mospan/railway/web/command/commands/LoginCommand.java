package com.mospan.railway.web.command.commands;

import com.mospan.railway.model.Role;
import com.mospan.railway.model.User;
import com.mospan.railway.service.UserService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (null == request.getParameter("login")) {
            return "login.jsp";
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserService userService = new UserService();
        User user = userService.find(login);

        if (!user.getPassword().equals(password)) {
            // logic for wrong password
        }

        else if (user.getRole().equals(Role.ADMIN)) {
            request.getSession().setAttribute("user", user);
            return (new AdminStationCommand()).execute(request, response);
        }
        else if (user.getRole().equals(Role.CLIENT)) {
            request.getSession().setAttribute("user", user);
            return (new ClientMainCommand()).execute(request, response);
        }
        return "login.jsp";
    }
}
