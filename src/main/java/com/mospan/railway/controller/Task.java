package com.mospan.railway.controller;

import com.mospan.railway.model.Route;
import com.mospan.railway.model.Trip;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.TripService;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Collection;
import java.util.TimerTask;

public class Task extends TimerTask {
    private static final Logger logger = Logger.getLogger(Task.class);
    @Override
    public void run() {
        logger.info("Creating trips for each route");

        Collection<Route> routes = new RouteService().findAll();
        for (Route route : routes) {
            Trip trip = new Trip();
            trip.setDepartDate(LocalDate.now().plusDays(34));

            LocalDateTime dt = trip.getDepartDate().atTime(route.getDepartTime());
            trip.setArrivalDate((dt.plusSeconds(route.getTime())).toLocalDate());
            trip.setAvailablePlaces(36);
            trip.setRoute(route);
            new TripService().insert(trip);
        }
    }
}
