package com.mospan.railway.controller;

import com.mospan.railway.model.*;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.StationService;
import com.mospan.railway.service.TicketService;
import com.mospan.railway.service.TripService;
import com.mospan.railway.web.command.commands.auth.LoginCommand;
import com.mospan.railway.web.command.commands.auth.LogoutCommand;
import com.mospan.railway.web.command.commands.auth.RegisterCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@WebServlet (value = "/trips/*")
public class TripController extends ResourceController {

    @Override
    Entity findModel(String id) {
        try {
            return new TripService().findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * GET /stations
     * Displays list of stations
     */
    @Override
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (null != req.getParameter("depart_station")) {
            Collection<Trip> trips = new ArrayList<>();

            Collection<Route> routes = new RouteService().findByStations(req.getParameter("depart_station"), req.getParameter("arrival_station"));

            for (Route route : routes) {
                System.out.println("looking for em trips");
                Collection<Trip> tripsForRoute = new TripService().findTrips(route, LocalDate.parse(req.getParameter("depart_date")));
                trips.addAll(tripsForRoute);
            }
            req.setAttribute("trips", trips);
        }
        req.setAttribute("stations", new StationService().findAll());
        req.getRequestDispatcher("/view/trips/list.jsp").forward(req, resp);
    }


    @Override
    protected void choose(Entity trip, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Seat> seats = new ArrayList<>();

        TicketService ticketService = new TicketService();

        Collection<Integer> occupied = ticketService.findSeats((Trip) trip);

        for (int i = 1; i < 37; i++) {
            Seat seat = new Seat();
            seat.setNumber(i);
            if(occupied.contains(i)) {
                seat.setOccupied(true);
            }
            seats.add(seat);
        }
        req.setAttribute("trip", trip);
        req.setAttribute("seats", seats);
        req.getRequestDispatcher("/view/trips/select_seat.jsp").forward(req, resp);;
    }
}