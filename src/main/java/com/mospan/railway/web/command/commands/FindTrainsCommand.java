package com.mospan.railway.web.command.commands;

import com.mospan.railway.dao.TripDao;
import com.mospan.railway.model.Route;
import com.mospan.railway.model.Trip;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.TripService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public class FindTrainsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        RouteService routeService = new RouteService();
        TripService tripService = new TripService();

        Collection<Route> routes = routeService.findByStations(request.getParameter("start_station"), request.getParameter("end_station"));
        for (Route route : routes) {

        }
        request.getSession().setAttribute("routes", routes);

        return null;
    }
}
