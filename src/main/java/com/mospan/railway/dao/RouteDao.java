package com.mospan.railway.dao;

import com.mospan.railway.model.Route;
import com.mospan.railway.service.StationService;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RouteDao implements Dao<Route>{

    Connection con = ConnectionPool.getInstance().getConnection();
    StationService stationService = new StationService();

    @Override
    public void insert(Route route) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO route (start_station_id, end_station_id, depart_time, arrival_time, price)" +
                    " VALUES (?, ?, ?, ?, ?)");

            st.setLong(1, route.getStartStation().getId());
            st.setLong(2, route.getEndStation().getId());
            st.setTime(3, Time.valueOf(route.getDepartTime()));
            st.setTime(4, Time.valueOf(route.getArrivalTime()));
            st.setDouble(5, route.getPrice());

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Route route) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE route SET start_station_id = ?, end_station_id = ?, depart_time = ?, " +
                    "arrival_time = ?, price = ? WHERE id = ?");

            st.setLong(1, route.getStartStation().getId());
            st.setLong(2, route.getEndStation().getId());
            st.setTime(3, Time.valueOf(route.getDepartTime()));
            st.setTime(4, Time.valueOf(route.getArrivalTime()));
            st.setDouble(5, route.getPrice());
            st.setLong(6, route.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Route find(String param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Route findById(long id) {

        Route route = new Route();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route WHERE id = ?");

            st.setLong(1, id);

            ResultSet rs = st.executeQuery();
            rs.next();


            route.setStartStation(stationService.findById(rs.getLong("start_station_id")));
            route.setEndStation(stationService.findById(rs.getLong("end_station_id")));
            route.setArrivalTime(rs.getTime("arrival_time").toLocalTime());
            route.setDepartTime(rs.getTime("depart_time").toLocalTime());
            route.setPrice(rs.getDouble("price"));
            route.setId(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return route;
    }

    @Override
    public void delete(Route route) {
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM route WHERE id = ?");
            st.setLong(1, route.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Route> findAll() {
        List<Route> routes = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Route route = new Route();

                route.setStartStation(stationService.findById(rs.getLong("start_station_id")));
                route.setEndStation(stationService.findById(rs.getLong("end_station_id")));
                route.setArrivalTime(rs.getTime("arrival_time").toLocalTime());
                route.setDepartTime(rs.getTime("depart_time").toLocalTime());
                route.setPrice(rs.getDouble("price"));
                route.setId(rs.getLong("id"));

                routes.add(route);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return routes;
    }

    public Route findByStations(String startStation, String endStation) {
        Route route = new Route();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route WHERE start_station_id = ? AND end_station_id = ?");

            st.setLong(1, stationService.find(startStation).getId());
            st.setLong(2, stationService.find(endStation).getId());

            ResultSet rs = st.executeQuery();

            rs.next();

            route.setStartStation(stationService.findById(rs.getLong("start_station_id")));
            route.setEndStation(stationService.findById(rs.getLong("end_station_id")));
            route.setArrivalTime(rs.getTime("arrival_time").toLocalTime());
            route.setDepartTime(rs.getTime("depart_time").toLocalTime());
            route.setId(rs.getLong("id"));
            route.setPrice(rs.getDouble("price"));


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return route;
    }

}
