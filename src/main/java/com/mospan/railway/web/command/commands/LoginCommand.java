package com.mospan.railway.web.command.commands;

import com.mospan.railway.dao.Dao;
import com.mospan.railway.dao.UserDao;
import com.mospan.railway.model.Role;
import com.mospan.railway.model.User;
import com.mospan.railway.service.UserService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            System.out.println("dont even try");
        }
        else if (user.getRole().equals(Role.ADMIN)) {
            return (new AdminMainCommand()).execute(request, response);
        }
        else if (user.getRole().equals(Role.CLIENT)) {
            return (new ClientMainCommand()).execute(request, response);
        }
        return "login.jsp";
    }
}
