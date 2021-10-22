package com.mospan.railway.controller;

import com.mospan.railway.model.*;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.StationService;
import com.mospan.railway.service.TicketService;
import com.mospan.railway.service.TripService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

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

    @Override
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Trip> trips = new ArrayList<>();

        if (null != req.getParameter("depart_station")) {
            Collection<Route> routes = new RouteService().findByStations(req.getParameter("depart_station"), req.getParameter("arrival_station"));

            if (routes != null) {
                for (Route route : routes) {
                    Collection<Trip> tripsForRoute = new TripService().findTrips(route, LocalDate.parse(req.getParameter("depart_date")));
                    if (tripsForRoute != null) {
                        trips.addAll(tripsForRoute);
                        req.getSession().setAttribute("trips", trips);
                    }
                }
                if (trips.isEmpty()) {
                    req.getSession().removeAttribute("trips");
                    req.getSession().setAttribute("errorMessage", "We could not find any trains for your request");
                }
            } else {
                req.getSession().removeAttribute("trips");
                req.getSession().setAttribute("errorMessage", "We do not have such a route");
            }

            req.getSession().setAttribute("date", req.getParameter("depart_date"));
            req.getSession().setAttribute("depart_station", (new StationService().find(req.getParameter("depart_station"))).getId());
            req.getSession().setAttribute("arrival_station", (new StationService().find(req.getParameter("arrival_station"))).getId());

        } else if (null != req.getSession().getAttribute("trips")) {
            long depId = (long) req.getSession().getAttribute("depart_station");
            long arrId = (long) req.getSession().getAttribute("arrival_station");

            Collection<Route> routes = new RouteService().findByStations(new StationService().findById(depId).getName(), new StationService().findById(arrId).getName());

            for (Route route : routes) {
                Collection<Trip> tripsForRoute = new TripService().findTrips(route, LocalDate.parse(req.getSession().getAttribute("date").toString()));
                if (tripsForRoute != null) {
                    trips.addAll(tripsForRoute);
                    req.getSession().setAttribute("trips", trips);
                }
            }
        }

        else {
            req.getSession().setAttribute("date", LocalDate.now());
        }
        req.setAttribute("stations", new StationService().findAll());
        req.setAttribute("min_date", LocalDate.now());
        req.setAttribute("max_date", LocalDate.now().plusDays(34));

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

    @Override
    protected void routeInfo(Entity trip, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("route", ((Trip)trip).getRoute());
        req.getRequestDispatcher("/view/routes/route_info.jsp").forward(req, resp);
    }

}