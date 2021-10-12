package com.mospan.railway.service;

import com.mospan.railway.dao.Dao;
import com.mospan.railway.dao.TripDao;
import com.mospan.railway.model.Trip;

import java.time.LocalDate;
import java.util.Collection;

public class TripService {

    TripDao dao = new TripDao();

    public void insert(Trip trip) {
        dao.insert(trip);
    }
    public void update(Trip trip) {
        dao.update(trip);
    }
    public Trip find(String param) {
        return dao.find(param);
    }
    public Trip findById(long id) {
        return dao.findById(id);
    }
    public void delete(Trip trip) {
        dao.delete(trip);
    }
    public Collection<Trip> findAll() {
        return dao.findAll();
    }
    public Trip getTripForDate(long routeId, LocalDate date) {
        return dao.getTripForDate(routeId, date);
    }
}
