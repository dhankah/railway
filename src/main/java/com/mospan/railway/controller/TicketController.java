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
import java.util.List;

@WebServlet (value = "/ticket/*")
public class TicketController extends ResourceController{

    @Override
    Entity findModel(String id) {
        try {
            return new TicketService().findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }
    /**
     * PUT /stations/{id}
     * Updates specified station
     */

    /**
     * GET /stations/{id}/edit
     * Displays edit form for given station
     */

    /**
     * POST /stations
     * Save new stations
     */
    @Override
    protected void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    /**
     * GET /stations
     * Displays list of stations
     */
    @Override
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Ticket> tickets = (List<Ticket>) new TicketService().findAllForUser(((User)req.getSession().getAttribute("user")).getId());
        req.setAttribute("tickets", tickets);
        req.getRequestDispatcher("/view/cabinet.jsp").forward(req, resp);
    }

    /**
     * DELETE stations/{id}
     * Removes specified station from db
     */
    @Override
    protected void delete(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new TicketService().delete((Ticket) entity);
        resp.sendRedirect(req.getContextPath() + "/cabinet");
    }

}
