package com.mospan.railway.service;

import com.mospan.railway.dao.Dao;
import com.mospan.railway.dao.RouteDao;
import com.mospan.railway.dao.StationDao;
import com.mospan.railway.model.Route;
import com.mospan.railway.model.Station;

import java.util.Collection;

public class RouteService {
    RouteDao dao = new RouteDao();

    public void insert(Route route) {
        dao.insert(route);
    }
    public void update(Route route) {
        dao.update(route);
    }
    public Route find(String name) {
        return dao.find(name);
    }
    public Route findById(long id) {
        return dao.findById(id);
    }
    public void delete(Route route) {
        dao.delete(route);
    }
    public Collection<Route> findAll() {
        return dao.findAll();
    }
    public Collection<Route> findByStations(String startStation, String endStation){
        return dao.findByStations(startStation, endStation);
    }
}
