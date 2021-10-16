package com.mospan.railway.web.command.commands;

import com.mospan.railway.model.Route;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.StationService;
import com.mospan.railway.service.TripService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public class FindTrainsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        if (null == request.getParameter("depart_date")) {
            System.out.println("i am executed at least once");
            StationService stationService = new StationService();
            request.getSession().setAttribute("stations", stationService.findAll());
            request.getSession().setAttribute("routes", null);
            return "list.jsp";
        }

        RouteService routeService = new RouteService();
        TripService tripService = new TripService();

        Collection<Route> routes = routeService.findByStations(request.getParameter("start_station"), request.getParameter("end_station"));

      /*  for (Route route : routes) {
            route.setDepartDate(LocalDate.parse(request.getParameter("depart_date")));
            int p = tripService.getTripForDate(route.getId(), LocalDate.parse(request.getParameter("depart_date"))).getAvailablePlaces();
            route.setPlaces(p);
            LocalTime d = route.getDepartTime();
            LocalTime a = route.getArrivalTime();
            long t = Duration.between(d, a).toMinutes();*/

           /* int hours = Math.abs((int) (t / 60));
            int minutes = Math.abs((int) (t % 60));


            String time = hours + " hours " + minutes + " minutes";
            route.setTime(time);*/
/*
            if (t < 0) {
                route.setArrivalDate(LocalDate.parse(request.getParameter("depart_date")).plusDays(1));
            }
            else {
                route.setArrivalDate(LocalDate.parse(request.getParameter("depart_date")));
            }
        }


        request.getSession().setAttribute("routes", routes);
        request.getSession().setAttribute("depart_date", null);*/
        return "list.jsp";
    }
}
