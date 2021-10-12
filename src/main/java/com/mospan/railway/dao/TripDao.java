package com.mospan.railway.dao;


import com.mospan.railway.model.Trip;
import com.mospan.railway.service.RouteService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TripDao implements Dao<Trip>{
    Connection con;

    RouteService routeService = new RouteService();

    @Override
    public void insert(Trip trip) {
        con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO trip (route_id, depart_date, arrival_date, available_places)" +
                    " VALUES (?, ?, ?, ?)");
            st.setLong(1, trip.getRoute().getId());
            st.setDate(2, Date.valueOf(trip.getDepartDate()));
            st.setDate(3, Date.valueOf(trip.getArrivalDate()));
            st.setInt(4, trip.getAvailablePlaces());
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Trip trip) {
        con = ConnectionPool.getInstance().getConnection();
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
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Trip find(String param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Trip findById(long id) {
        con = ConnectionPool.getInstance().getConnection();
        Trip trip = new Trip();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("SELECT * FROM trip WHERE id = ?");
            st.setLong(1,id);

            ResultSet rs = st.executeQuery();
            rs.next();

            trip.setRoute(routeService.findById(rs.getLong("train_id")));
            trip.setDepartDate(rs.getDate("depart_date").toLocalDate());
            trip.setArrivalDate(rs.getDate("arrival_date").toLocalDate());
            trip.setAvailablePlaces(rs.getInt("available_places"));

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trip;
    }

    @Override
    public void delete(Trip trip) {
        con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM trip WHERE id = ?");
            st.setLong(1, trip.getId());
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Trip> findAll() {
        con = ConnectionPool.getInstance().getConnection();
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
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }

    public Trip getTripForDate(long routeId, LocalDate date) {
        System.out.println("this is where dao begins");

        con = ConnectionPool.getInstance().getConnection();
        System.out.println("now i have a connection");
        Trip trip = new Trip();

        PreparedStatement st = null;
        try {
            System.out.println("i am inside the dao at the very beginning");
            st = con.prepareStatement("SELECT * FROM trip WHERE route_id = ? AND depart_date = ?");
            st.setLong(1,routeId);
            st.setDate(2, Date.valueOf(date));
            ResultSet rs = st.executeQuery();
            rs.next();
            System.out.println("i am inside the dao");
            trip.setId(rs.getLong("id"));
            trip.setDepartDate(rs.getDate("depart_date").toLocalDate());
            trip.setArrivalDate(rs.getDate("arrival_date").toLocalDate());
            trip.setAvailablePlaces(rs.getInt("available_places"));
            trip.setRoute(routeService.findById(rs.getLong("route_id")));
            System.out.println("i am before catch in the dao");

        } catch (SQLException e) {
            e.printStackTrace();
            trip.setAvailablePlaces(36);
            System.out.println("i am like inside the catch of the dao");
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trip;
    }

}
