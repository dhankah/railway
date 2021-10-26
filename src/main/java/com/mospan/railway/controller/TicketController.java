package com.mospan.railway.controller;

import com.mospan.railway.model.*;
import com.mospan.railway.service.StationService;
import com.mospan.railway.service.TicketService;
import com.mospan.railway.service.TripService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet (value = "/tickets/*")
public class TicketController extends ResourceController{

    @Override
    Entity findModel(String id) {
        try {
            return new TicketService().findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    protected void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResourceBundle ua = ResourceBundle.getBundle("i18n.resources", new Locale("ua"));
        ResourceBundle en = ResourceBundle.getBundle("i18n.resources", new Locale("en"));

        User user = (User) req.getSession().getAttribute("user");
        Collection<Ticket> tickets = new TicketService().findAllForUser(user.getId());
        if (null != tickets) {

            for (Ticket ticket : tickets) {
                Trip trip = new TripService().findById(Long.parseLong(req.getParameter("trip")));
                if (ticket.getTrip().getId() == trip.getId()) {
                    if (req.getSession().getAttribute("defaultLocale").equals("ua")) {
                        req.getSession().setAttribute("errorMessage", ua.getString("ticket_error"));
                    } else {
                        req.getSession().setAttribute("errorMessage", en.getString("ticket_error"));
                    }
                    resp.sendRedirect(req.getContextPath() + "/trips/" + trip.getId() + "/choose");
                    return;
                }
            }
        }
        Ticket ticket = new Ticket();
        ticket.setUser((User) req.getSession().getAttribute("user"));
        Trip trip = new TripService().findById(Long.parseLong(req.getParameter("trip")));
        trip.setAvailablePlaces(trip.getAvailablePlaces()-1);
        new TripService().update(trip);
        ticket.setTrip(trip);
        ticket.setSeat(Integer.parseInt(req.getParameter("number")));
        new TicketService().insert(ticket);
        resp.sendRedirect(req.getContextPath() + "/cabinet");
    }

    @Override
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Ticket> tickets = (List<Ticket>) new TicketService().findAllForUser(((User)req.getSession().getAttribute("user")).getId());
        req.setAttribute("tickets", tickets);
        req.getRequestDispatcher("/view/cabinet.jsp").forward(req, resp);
    }

    @Override
    protected void delete(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new TicketService().delete((Ticket) entity);
        resp.sendRedirect(req.getContextPath() + "/cabinet");
    }




}
