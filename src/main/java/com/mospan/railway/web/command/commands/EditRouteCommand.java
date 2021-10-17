package com.mospan.railway.web.command.commands;

import com.mospan.railway.model.Route;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.StationService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

public class EditRouteCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        StationService stationService = new StationService();
        RouteService routeService = new RouteService();

        if ( null != request.getParameter("add")) {
            Route route = new Route();

            route.setStartStation(stationService.find(request.getParameter("start_station")));
            route.setEndStation(stationService.find(request.getParameter("end_station")));

            route.setPrice(Double.parseDouble(request.getParameter("price")));
//            route.setArrivalTime(LocalTime.parse(request.getParameter("arrival_time")));
            route.setDepartTime(LocalTime.parse(request.getParameter("depart_time")));
            routeService.insert(route);
        }
        else if (null != request.getParameter("id")) {
            Route route = routeService.findById(Long.parseLong(request.getParameter("id")));
            request.getSession().setAttribute("route", route);

            return "edit.jsp";
        }
        else if (null != request.getParameter("idToDelete")) {
            Route route = routeService.findById(Long.parseLong(request.getParameter("idToDelete")));
            routeService.delete(route);
        }
        else if (null != request.getParameter("editId")) {
            Route route = routeService.findById(Long.parseLong(request.getParameter("editId")));

            route.setStartStation(stationService.find(request.getParameter("start_station")));
            route.setEndStation(stationService.find(request.getParameter("end_station")));

            route.setPrice(Double.parseDouble(request.getParameter("price")));
//            route.setArrivalTime(LocalTime.parse(request.getParameter("arrival_time")));
            route.setDepartTime(LocalTime.parse(request.getParameter("depart_time")));

            routeService.update(route);
        }
        return new AdminRouteCommand().execute(request, response);
    }
}
