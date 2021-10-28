package com.mospan.railway.controller;

import com.mospan.railway.model.*;

import com.mospan.railway.service.TicketService;
import com.mospan.railway.service.UserService;
import com.mospan.railway.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


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


        ResourceBundle ua = ResourceBundle.getBundle("i18n.resources", new Locale("ua"));
        ResourceBundle en = ResourceBundle.getBundle("i18n.resources", new Locale("en"));

        req.setCharacterEncoding("UTF-8");


        //update password
        if (null != req.getParameter("password")) {
            if (req.getParameter("old_password").equals(new UserService().find(((User) user).getLogin()).getPassword())
                    && req.getParameter("password").equals(req.getParameter("re_password"))) {
                ((User) user).setPassword(req.getParameter("password"));
                new UserService().update((User) user);

                if (req.getSession().getAttribute("defaultLocale").equals("ua")) {
                    req.getSession().setAttribute("message", ua.getString("password_updated"));
                } else {
                    req.getSession().setAttribute("message", en.getString("password_updated"));
                }
            } else if (!req.getParameter("old_password").equals(new UserService().find(((User) user).getLogin()).getPassword())) {

                if (req.getSession().getAttribute("defaultLocale").equals("ua")) {
                    req.getSession().setAttribute("errorMessage", ua.getString("wrong_old_pass"));
                } else {
                    req.getSession().setAttribute("errorMessage", en.getString("wrong_old_pass"));
                }

            } else if (!req.getParameter("password").equals(req.getParameter("re_password"))) {

                if (req.getSession().getAttribute("defaultLocale").equals("ua")) {
                    req.getSession().setAttribute("errorMessage", ua.getString("not_same+passwords"));
                } else {
                    req.getSession().setAttribute("errorMessage", en.getString("not_same+passwords"));
                }

            } else {

                if (req.getSession().getAttribute("defaultLocale").equals("ua")) {
                    req.getSession().setAttribute("errorMessage", ua.getString("sth_wrong"));
                } else {
                    req.getSession().setAttribute("errorMessage", en.getString("sth_wrong"));
                }

            }
            resp.sendRedirect(req.getContextPath() + "/cabinet/" + user.getId() +"/change_password");
            return;
        }

        //update user info
        User userUpd = new User();
        userUpd.setId(user.getId());
        userUpd.setPassword(((User)user).getPassword());
        userUpd.setLogin(req.getParameter("login"));
        Detail detailUpd = new Detail();
        detailUpd.setId(user.getId());
        detailUpd.setFirstName(req.getParameter("first_name"));

        detailUpd.setLastName(req.getParameter("last_name"));
        detailUpd.setEmail(req.getParameter("email"));
        userUpd.setDetails(detailUpd);


        if (validator.validateUser((User) user, userUpd).equals("false")) {

            if (req.getSession().getAttribute("defaultLocale").equals("ua")) {
                req.getSession().setAttribute("errorMessage", ua.getString("login_email_exists"));
            } else {
                req.getSession().setAttribute("errorMessage", en.getString("login_email_exists"));
            }
            resp.sendRedirect(req.getContextPath() + "/cabinet/" + user.getId() + "/edit");
            return;
        } else if (validator.validateUser((User) user, userUpd).equals("no_change")) {

            resp.sendRedirect(req.getContextPath() + "/cabinet");
            return;
        }

        if (req.getSession().getAttribute("defaultLocale").equals("ua")) {
            req.getSession().setAttribute("message", ua.getString("profile_updated"));
        } else {
            req.getSession().setAttribute("message", en.getString("profile_updated"));
        }

        /*Detail detail = ((User) user).getDetails();

        detail.setFirstName(req.getParameter("first_name"));
        detail.setLastName(req.getParameter("last_name"));
        detail.setEmail(req.getParameter("email"));
        ((User) user).setDetails(detail);
        ((User) user).setLogin(req.getParameter("login"));*/

        new UserService().update(userUpd);
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

            ResourceBundle ua = ResourceBundle.getBundle("i18n.resources", new Locale("ua"));
            ResourceBundle en = ResourceBundle.getBundle("i18n.resources", new Locale("en"));

            if (req.getSession().getAttribute("defaultLocale").equals("ua")) {
                req.getSession().setAttribute("errorMessage", ua.getString("should_cancel_trip"));
            } else {
                req.getSession().setAttribute("errorMessage", en.getString("should_cancel_trip"));
            }

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