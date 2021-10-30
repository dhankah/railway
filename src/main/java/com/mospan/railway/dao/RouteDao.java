package com.mospan.railway.dao;

import com.mospan.railway.model.Route;
import com.mospan.railway.model.Station;
import com.mospan.railway.model.Trip;
import com.mospan.railway.service.StationService;
import com.mospan.railway.service.TripService;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RouteDao implements Dao<Route>{
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con;

    StationService stationService = new StationService();

    @Override
    public void insert(Route route) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO route (start_station_id, end_station_id, depart_time, time, price)" +
                    " VALUES (?, ?, ?, ?, ?)");

            st.setLong(1, route.getStartStation().getId());
            st.setLong(2, route.getEndStation().getId());
            st.setTime(3, Time.valueOf(route.getDepartTime()));
            st.setLong(4, route.getTime());
            st.setDouble(5, route.getPrice());


            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cp.closeConnection(con);
        }

    }

    @Override
    public void update(Route route) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE route SET start_station_id = ?, end_station_id = ?, depart_time = ?, " +
                    "time = ?, price = ? WHERE id = ?");

            st.setLong(1, route.getStartStation().getId());
            st.setLong(2, route.getEndStation().getId());
            st.setTime(3, Time.valueOf(route.getDepartTime()));
            st.setLong(4, route.getTime());
            st.setDouble(5, route.getPrice());
            st.setLong(6, route.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public Route find(String param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Route findById(long id) {
        con = cp.getConnection();
        Route route = new Route();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route WHERE id = ?");

            st.setLong(1, id);

            ResultSet rs = st.executeQuery();
            rs.next();


            route.setStartStation(stationService.findById(rs.getLong("start_station_id")));
            route.setEndStation(stationService.findById(rs.getLong("end_station_id")));
            route.setTime(rs.getLong("time"));
            route.setDepartTime(rs.getTime("depart_time").toLocalTime());
            route.setPrice(rs.getDouble("price"));


            route.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cp.closeConnection(con);
        }
        return route;
    }

    @Override
    public void delete(Route route) {
        con = cp.getConnection();
        Collection<Trip> trips = new TripService().findTripsForRoute(route);
        for (Trip trip : trips) {
            new TripService().delete(trip);
        }
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM route WHERE id = ?");
            st.setLong(1, route.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public Collection<Route> findAll() {
        con = cp.getConnection();
        List<Route> routes = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Route route = new Route();

                route.setStartStation(stationService.findById(rs.getLong("start_station_id")));
                route.setEndStation(stationService.findById(rs.getLong("end_station_id")));
                route.setTime(rs.getLong("time"));
                route.setDepartTime(rs.getTime("depart_time").toLocalTime());
                route.setPrice(rs.getDouble("price"));
                route.setId(rs.getLong("id"));

                routes.add(route);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cp.closeConnection(con);
        }

        return routes;
    }

    public Collection<Route> findByStations(String startStation, String endStation) {
        con = cp.getConnection();
        List<Route> routes = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route WHERE start_station_id = ? AND end_station_id = ?");

            st.setLong(1, stationService.find(startStation).getId());
            st.setLong(2, stationService.find(endStation).getId());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Route route = new Route();
                route.setStartStation(stationService.findById(rs.getLong("start_station_id")));
                route.setEndStation(stationService.findById(rs.getLong("end_station_id")));
                route.setTime(rs.getLong("time"));
                route.setDepartTime(rs.getTime("depart_time").toLocalTime());
                route.setId(rs.getLong("id"));
                route.setPrice(rs.getDouble("price"));
                routes.add(route);
            }
            if (routes.isEmpty()) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cp.closeConnection(con);
        }

        return routes;
    }

    public Collection<Route> findRecords(long id) {
        con = cp.getConnection();
        List<Route> routes = new ArrayList<>();

        if (id != 1){
            id = id - 1;
            id = id * 10 + 1;
        }


        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route LIMIT ?, 10");
            st.setLong(1, id - 1);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Route route = new Route();
                route.setId(rs.getLong("id"));
                route.setStartStation(new StationService().findById(rs.getLong("start_station_id")));
                route.setEndStation(new StationService().findById(rs.getLong("end_station_id")));
                route.setDepartTime(rs.getTime("depart_time").toLocalTime());
                route.setTime(rs.getLong("time"));
                route.setPrice(rs.getDouble("price"));
                routes.add(route);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

        return routes;
    }

    public Collection<Route> findByStation(Station station) {
        con = cp.getConnection();
        List<Route> routes = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM route WHERE start_station_id = ? UNION SELECT * FROM route WHERE end_station_id = ?");

            st.setLong(1, station.getId());
            st.setLong(2, station.getId());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Route route = new Route();
                route.setStartStation(stationService.findById(rs.getLong("start_station_id")));
                route.setEndStation(stationService.findById(rs.getLong("end_station_id")));
                route.setTime(rs.getLong("time"));
                route.setDepartTime(rs.getTime("depart_time").toLocalTime());
                route.setId(rs.getLong("id"));
                route.setPrice(rs.getDouble("price"));
                routes.add(route);
            }
            if (routes.isEmpty()) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            cp.closeConnection(con);
        }
        return routes;
    }


}
