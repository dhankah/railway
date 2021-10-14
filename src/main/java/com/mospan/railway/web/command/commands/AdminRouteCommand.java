package com.mospan.railway.web.command.commands;

import com.mospan.railway.model.Route;
import com.mospan.railway.model.Station;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.StationService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public class AdminRouteCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        RouteService routeService = new RouteService();
        StationService stationService = new StationService();

        Collection<Route> routes = routeService.findAll();
        request.getSession().setAttribute("routes", routes);

        Collection<Station> stations = stationService.findAll();
        request.getSession().setAttribute("stations", stations);

        return "list.jsp";
    }
}
