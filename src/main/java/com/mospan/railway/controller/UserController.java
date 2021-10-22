package com.mospan.railway.controller;

import com.mospan.railway.model.*;
import com.mospan.railway.service.StationService;
import com.mospan.railway.service.TicketService;
import com.mospan.railway.service.UserService;
import com.mospan.railway.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet (value = "/cabinet/*")
public class UserController extends ResourceController{
Validator validator = new Validator();
    @Override
    Entity findModel(String id) {
        try {
            return new UserService().findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * PUT /stations/{id}
     * Updates specified station
     */
    @Override
    protected void update(Entity user, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        if (null != req.getParameter("password")) {
            if (req.getParameter("old_password").equals(new UserService().find(((User) user).getLogin()).getPassword())
            && req.getParameter("password").equals(req.getParameter("re_password"))) {
                ((User) user).setPassword(req.getParameter("password"));
                new UserService().update((User) user);
                String message = "Password updated successfully";
                req.getSession().setAttribute("message", message);
            } else if (!req.getParameter("old_password").equals(new UserService().find(((User) user).getLogin()).getPassword())) {
                String message = "Wrong old password";
                req.getSession().setAttribute("errorMessage", message);
            } else if (!req.getParameter("password").equals(req.getParameter("re_password"))) {
                String message = "Passwords are not the same";
                req.getSession().setAttribute("errorMessage", message);
            } else {
                String message = "You typed something wrong";
                req.getSession().setAttribute("errorMessage", message);
            }
            resp.sendRedirect(req.getContextPath() + "/cabinet/" + user.getId() +"/change_password");
            return;
        }

        if (!validator.validateUser((User) user)) {

            req.getSession().setAttribute("errorMessage", "User with such login or email already exists");
            resp.sendRedirect(req.getContextPath() + "/cabinet/" + user.getId() + "/edit");
            return;
        }

        String message = "Your profile was updated successfully";
        req.getSession().setAttribute("message", message);

        Detail detail = ((User) user).getDetails();

        detail.setFirstName(req.getParameter("first_name"));
        detail.setLastName(req.getParameter("last_name"));
        detail.setEmail(req.getParameter("email"));
        ((User) user).setDetails(detail);
        ((User) user).setLogin(req.getParameter("login"));

        new UserService().update((User) user);
        resp.sendRedirect(req.getContextPath() + "/cabinet");
    }

    @Override
    protected void edit(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        req.getRequestDispatcher("/view/cabinet/edit.jsp").forward(req, resp);
    }



    @Override
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = ((User)req.getSession().getAttribute("user")).getId();
        req.getSession().setAttribute("user", new UserService().findById(id));

        List<Ticket> tickets = (List<Ticket>) new TicketService().findAllForUser(((User)req.getSession().getAttribute("user")).getId());
        req.setAttribute("tickets", tickets);

        req.getRequestDispatcher("/view/cabinet/cabinet.jsp").forward(req, resp);
    }


    @Override
    protected void delete(Entity user, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != new TicketService().findAllForUser(user.getId())) {
            String message = "You should first cancel your trips";
            req.getSession().setAttribute("errorMessage", message);
            resp.sendRedirect(req.getContextPath() + "/cabinet");
            return;
        }
        new UserService().delete((User) user);
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/auth/login");
    }

    @Override
    protected void changePassword(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/cabinet/change_password.jsp").forward(req, resp);
    }
}
