package com.mospan.railway.dao;

import com.mospan.railway.model.Route;
import com.mospan.railway.model.Train;
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
            st = con.prepareStatement("INSERT INTO route (start_station_id, end_station_id, depart_time, arrival_time)" +
                    " VALUES (?, ?, ?, ?)");

            st.setLong(1, route.getStartStationId());
            st.setLong(2, route.getEndStationId());
            st.setTime(3, Time.valueOf(route.getDepartTime()));
            st.setTime(4, Time.valueOf(route.getArrivalTime()));

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Route route) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE route SET (start_station_id, end_station_id, depart_time, arrival_time)" +
                    " VALUES (?, ?, ?, ?) WHERE id = ?");

            st.setLong(1, route.getStartStationId());
            st.setLong(2, route.getEndStationId());
            st.setTime(3, Time.valueOf(route.getDepartTime()));
            st.setTime(4, Time.valueOf(route.getArrivalTime()));
            st.setLong(5, route.getId());

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

            route.setStartStationId(rs.getLong("start_station_id"));
            route.setEndStationId(rs.getLong("end_station_id"));
            route.setArrivalTime(rs.getTime("arrival_time").toLocalTime());
            route.setDepartTime(rs.getTime("depart_time").toLocalTime());
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
            long id = 1;
            while (rs.next()) {
                Route route = findById(id);
                routes.add(route);
                id++;
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

            route.setStartStationId(rs.getLong("start_station_id"));
            route.setEndStationId(rs.getLong("end_station_id"));
            route.setArrivalTime(rs.getTime("arrival_time").toLocalTime());
            route.setDepartTime(rs.getTime("depart_time").toLocalTime());
            route.setId(rs.getLong("id"));


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return route;
    }

}
