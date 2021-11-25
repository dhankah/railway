package com.mospan.railway.dao.interfaces;

import com.mospan.railway.model.Route;
import com.mospan.railway.model.Trip;

import java.time.LocalDate;
import java.util.Collection;

public interface TripDao {
    void insert(Trip trip);
    void update(Trip trip);
    Trip findById(long id);
    void delete(Trip trip);
    Collection<Trip> findAll();
    Collection<Trip> findTripsForRoute(Route route);
    Collection<Trip> findRecords(Route route, LocalDate date);
}
