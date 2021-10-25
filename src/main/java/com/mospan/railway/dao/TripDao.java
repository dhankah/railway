package com.mospan.railway.dao;


import com.mospan.railway.model.Route;
import com.mospan.railway.model.Trip;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.StationService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TripDao implements Dao<Trip>{
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con;

    RouteService routeService = new RouteService();

    @Override
    public void insert(Trip trip) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO trip (route_id, depart_date, arrival_date, available_places)" +
                    " VALUES (?, ?, ?, ?)");
            st.setLong(1, trip.getRoute().getId());
            st.setDate(2, Date.valueOf(trip.getDepartDate()));
            st.setDate(3, Date.valueOf(trip.getArrivalDate()));
            st.setInt(4, trip.getAvailablePlaces());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public void update(Trip trip) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE trip SET route_id = ?, depart_date = ?, arrival_date = ?, available_places = ?" +
                    " WHERE id = ?");
            st.setLong(1, trip.getRoute().getId());
            st.setDate(2, Date.valueOf(trip.getDepartDate()));
            st.setDate(3, Date.valueOf(trip.getArrivalDate()));
            st.setInt(4, trip.getAvailablePlaces());
            st.setLong(5, trip.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public Trip find(String param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Trip findById(long id) {
        con = cp.getConnection();
        Trip trip = new Trip();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("SELECT * FROM trip WHERE id = ?");
            st.setLong(1,id);

            ResultSet rs = st.executeQuery();
            rs.next();

            trip.setId(rs.getLong("id"));
            trip.setRoute(routeService.findById(rs.getLong("route_id")));
            trip.setDepartDate(rs.getDate("depart_date").toLocalDate());
            trip.setArrivalDate(rs.getDate("arrival_date").toLocalDate());
            trip.setAvailablePlaces(rs.getInt("available_places"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
        return trip;
    }

    @Override
    public void delete(Trip trip) {
        con = cp.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM trip WHERE id = ?");
            st.setLong(1, trip.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public Collection<Trip> findAll() {
        con = cp.getConnection();
        List<Trip> trips = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM trip");

            ResultSet rs = st.executeQuery();
            long id = 1;
            while (rs.next()) {
                Trip trip = findById(id);
                trips.add(trip);
                id++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
        return trips;
    }

    public Trip getTripForDate(long routeId, LocalDate date) {
        con = cp.getConnection();
        Trip trip = new Trip();

        PreparedStatement st = null;
        try {
            st = con.prepareStatement("SELECT * FROM trip WHERE route_id = ? AND depart_date = ?");
            st.setLong(1,routeId);
            st.setDate(2, Date.valueOf(date));
            ResultSet rs = st.executeQuery();
            rs.next();
            trip.setId(rs.getLong("id"));
            trip.setDepartDate(rs.getDate("depart_date").toLocalDate());
            trip.setArrivalDate(rs.getDate("arrival_date").toLocalDate());
            trip.setAvailablePlaces(rs.getInt("available_places"));
            trip.setRoute(routeService.findById(rs.getLong("route_id")));

        } catch (SQLException e) {
            e.printStackTrace();
            trip.setAvailablePlaces(36);
        } finally {
            cp.closeConnection(con);
        }
        return trip;
    }

    public Collection<Trip> findTrips(Route route, LocalDate date) {
        con = cp.getConnection();
        PreparedStatement st = null;
        List<Trip> trips = new ArrayList<>();
        try {
            st = con.prepareStatement("SELECT * FROM trip WHERE route_id = ? AND depart_date = ?");
            st.setLong(1, route.getId());
            st.setDate(2, Date.valueOf(date));
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Trip trip = new Trip();
                trip.setId(rs.getLong("id"));
                trip.setRoute(route);
                trip.setDepartDate(rs.getDate("depart_date").toLocalDate());
                trip.setArrivalDate(rs.getDate("arrival_date").toLocalDate());
                trip.setAvailablePlaces(rs.getInt("available_places"));
                trips.add(trip);
            }
            if (trips.isEmpty()) {
                trips = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            trips = null;
        } finally {
            cp.closeConnection(con);
        }
        return trips;
    }

    public Collection<Trip> findRecords(Route route, LocalDate date, long id) {
        con = cp.getConnection();
        List<Trip> trips = new ArrayList<>();

        if (id != 1){
            id = id - 1;
            id = id * 10 + 1;
        }


        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM trip WHERE route_id = ? AND depart_date = ? LIMIT ?, 10");
            st.setLong(1, route.getId());
            st.setDate(2, Date.valueOf(date));
            st.setLong(3, id - 1);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Trip trip = new Trip();
                trip.setId(rs.getLong("id"));
                trip.setDepartDate(rs.getDate("depart_date").toLocalDate());
                trip.setArrivalDate(rs.getDate("arrival_date").toLocalDate());
                trip.setRoute(new RouteService().findById(rs.getLong("route_id")));
                trip.setAvailablePlaces(rs.getInt("available_places"));
                trips.add(trip);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

        return trips;
    }
}
