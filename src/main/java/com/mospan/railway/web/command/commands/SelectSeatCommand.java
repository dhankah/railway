package com.mospan.railway.web.command.commands;

import com.mospan.railway.dao.TicketDao;
import com.mospan.railway.model.Route;
import com.mospan.railway.model.Seat;
import com.mospan.railway.model.Trip;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.TicketService;
import com.mospan.railway.service.TripService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SelectSeatCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {


        TripService tripService = new TripService();

        RouteService routeService = new RouteService();

        Trip trip = tripService.getTripForDate(Long.parseLong(request.getParameter("route")), LocalDate.parse(request.getParameter("departDate")));

        if (null == trip.getDepartDate()) {
            trip.setDepartDate(LocalDate.parse(request.getParameter("departDate")));
            trip.setArrivalDate(LocalDate.parse(request.getParameter("arrivalDate")));
            trip.setRoute(routeService.findById(Long.parseLong(request.getParameter("route"))));
            trip.setAvailablePlaces(36);
            tripService.insert(trip);

            trip = tripService.getTripForDate(trip.getRoute().getId(), trip.getDepartDate());
        }

        List<Seat> seats = new ArrayList<>();

        TicketService ticketService = new TicketService();

        Collection<Integer> occupied = ticketService.findSeats(trip);
        occupied.add(3);
        for (int i = 1; i < 37; i++) {
            Seat seat = new Seat();
            seat.setNumber(i);
            if(occupied.contains(i)) {
                seat.setOccupied(true);
            }
            seats.add(seat);
        }
        request.getSession().setAttribute("trip", trip);
        request.getSession().setAttribute("seats", seats);
        return "select_seat.jsp";
    }


}
