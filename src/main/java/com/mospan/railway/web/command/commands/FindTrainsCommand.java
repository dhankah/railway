package com.mospan.railway.web.command.commands;

import com.mospan.railway.dao.TripDao;
import com.mospan.railway.model.Route;
import com.mospan.railway.model.Trip;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.StationService;
import com.mospan.railway.service.TripService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public class FindTrainsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        if (null == request.getParameter("depart_date")) {
            StationService stationService = new StationService();
            request.getSession().setAttribute("stations", stationService.findAll());
            return "client_trains.jsp";
        }

        RouteService routeService = new RouteService();
        TripService tripService = new TripService();

        Collection<Route> routes = routeService.findByStations(request.getParameter("start_station"), request.getParameter("end_station"));

        for (Route route : routes) {
            int p = tripService.getPlacesForDate(route.getId(), LocalDate.parse(request.getParameter("depart_date")));
            if (p != 0) {
                route.setPlaces(p);
                LocalTime d = route.getDepartTime();
                LocalTime a = route.getArrivalTime();
                long t = Duration.between(d, a).toMinutes();

                int hours = (int) (t / 60);
                int minutes = (int) (t % 60);

                String time = hours + " hours" + minutes + " minutes";
                route.setTime(time);
            }
        }


        request.getSession().setAttribute("routes", routes);
        return "client_trains.jsp";
    }
}
