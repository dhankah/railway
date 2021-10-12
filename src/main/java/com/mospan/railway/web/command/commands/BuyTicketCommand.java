package com.mospan.railway.web.command.commands;

import com.mospan.railway.model.Ticket;
import com.mospan.railway.model.Trip;
import com.mospan.railway.model.User;
import com.mospan.railway.service.TicketService;
import com.mospan.railway.service.TripService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BuyTicketCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        TicketService ticketService = new TicketService();
        TripService tripService = new TripService();
        Ticket ticket = new Ticket();
        ticket.setSeat(Integer.parseInt(request.getParameter("number")));
        ticket.setUser((User) request.getSession().getAttribute("user"));
        ticket.setTrip((Trip) request.getSession().getAttribute("trip"));
        ticketService.insert(ticket);
        Trip trip = (Trip) request.getSession().getAttribute("trip");
        trip.setAvailablePlaces(trip.getAvailablePlaces()-1);
        tripService.update(trip);
        return new FindTrainsCommand().execute(request, response);
    }
}
