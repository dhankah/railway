package com.mospan.railway.controller;

import com.mospan.railway.model.Route;
import com.mospan.railway.model.Trip;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.TripService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Collection;
import java.util.TimerTask;

public class Task extends TimerTask {
    @Override
    public void run() {
        Collection<Route> routes = new RouteService().findAll();
        for (Route route : routes) {
            Trip trip = new Trip();
            trip.setDepartDate(LocalDate.now().plusDays(20));

            LocalDateTime dt = trip.getDepartDate().atTime(route.getDepartTime());
            trip.setArrivalDate((dt.plusSeconds(route.getTime())).toLocalDate());
            trip.setAvailablePlaces(36);
            trip.setRoute(route);
            new TripService().insert(trip);
        }
    }
}
