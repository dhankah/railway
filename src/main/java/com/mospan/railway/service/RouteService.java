package com.mospan.railway.service;

import com.mospan.railway.dao.impl.RouteDaoImpl;
import com.mospan.railway.dao.interfaces.RouteDao;
import com.mospan.railway.model.Route;
import com.mospan.railway.model.Station;
import com.mospan.railway.model.Trip;

import java.util.Collection;

public class RouteService {
    RouteDao dao = new RouteDaoImpl();

    public void insert(Route route) {
        dao.insert(route);
    }
    public void update(Route route) {
        dao.update(route);
    }
    public Route findById(long id) {
        return dao.findById(id);
    }
    public void delete(Route route) {

        Collection<Trip> trips = new TripService().findTripsForRoute(route);
        if (null != trips) {
        for (Trip trip : trips) {
            new TripService().delete(trip);
        }
        }
        dao.delete(route);
    }
    public Collection<Route> findAll() {
        return dao.findAll();
    }
    public Collection<Route> findByStations(String startStation, String endStation){
        return dao.findByStations(startStation, endStation);
    }
    public Collection<Route> findRecords(long id) {
        return dao.findRecords(id);
    }
    public Collection<Route> findByStation(Station station) {
        return dao.findByStation(station);
    }
}
