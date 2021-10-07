package com.mospan.railway.dao;


import com.mospan.railway.model.Train;
import com.mospan.railway.service.RouteService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrainDao implements Dao<Train> {

    Connection con = ConnectionPool.getInstance().getConnection();
    RouteService routeService = new RouteService();


    @Override
    public void insert(Train train) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO train (price, number, route_id) VALUES (?, ?, ?)");


            st.setDouble(1, train.getPrice());
            st.setString(2, train.getNumber());
            st.setLong(3, train.getRoute().getId());

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Train train) {
        try {
            PreparedStatement st = con.prepareStatement("UPDATE station SET (price, number, route_id) VALUES (?, ?, ?) WHERE id = ?");

            st.setDouble(1, train.getPrice());
            st.setString(2, train.getNumber());
            st.setLong(3, train.getRoute().getId());
            st.setLong(4, train.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Train find(String number) {

        throw new UnsupportedOperationException();

       /* Train train = new Train();

        try {

            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM train WHERE number = ?");

            st.setString(1, number);

            ResultSet rs = st.executeQuery();
            rs.next();

            train.setId(rs.getLong("id"));
            train.setRoute(routeService.findById(rs.getLong("route_id")));
            train.setArrivalDate(rs.getDate("arrival_date").toLocalDate());
            train.setDepartDate(rs.getDate("depart_date").toLocalDate());
            train.setNumber(rs.getString("number"));
            train.setAvailablePlaces(rs.getInt("available_places"));
            train.setPrice(rs.getDouble("price"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return train;*/
    }

    @Override
    public Train findById(long id) {
        Train train = new Train();

        try {

            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM train WHERE id = ?");

            st.setLong(1, id);

            ResultSet rs = st.executeQuery();
            rs.next();

            train.setId(rs.getLong("id"));
            train.setRoute(routeService.findById(rs.getLong("route_id")));
            train.setNumber(rs.getString("number"));
            train.setPrice(rs.getDouble("price"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return train;
    }

    @Override
    public void delete(Train train) {
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM user WHERE id = ?");
            st.setLong(1, train.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Train> findAll() {
        List<Train> trains = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM train");

            ResultSet rs = st.executeQuery();
            int id = 1;

            while (rs.next()) {
                Train train = findById(id);
                trains.add(train);
                id++;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trains;
    }

    public Collection<Train> findByRouteId(long routeId) {
        List<Train> trains = new ArrayList<>();
        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM train WHERE route_id = ?");
            st.setLong(1, routeId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Train train = new Train();

                train.setId(rs.getLong("id"));
                train.setRoute(routeService.findById(rs.getLong("route_id")));
                train.setNumber(rs.getString("number"));
                train.setPrice(rs.getDouble("price"));

                trains.add(train);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trains;
    }
}
