package com.mospan.railway.dao;


import com.mospan.railway.model.Trip;
import com.mospan.railway.service.TrainService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TripDao implements Dao<Trip>{
    Connection con;

    TrainService trainService = new TrainService();

    @Override
    public void insert(Trip trip) {
        con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO trip (train_id, depart_date, arrival_date, available_places)" +
                    " VALUES (?, ?, ?, ?)");
            st.setLong(1, trip.getTrain().getId());
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
            st = con.prepareStatement("UPDATE trip SET (train_id, depart_date, arrival_date, available_places)" +
                    " VALUES (?, ?, ?, ?) WHERE id = ?");
            st.setLong(1, trip.getTrain().getId());
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

            trip.setTrain(trainService.findById(rs.getLong("train_id")));
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
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM trip WHERE id = ?");
            st.setLong(1, trip.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Trip> findAll() {
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

        return trips;
    }
}
