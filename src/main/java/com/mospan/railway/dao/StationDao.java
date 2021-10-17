package com.mospan.railway.dao;

import com.mospan.railway.model.Station;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StationDao implements Dao<Station>{

    Connection con;

    @Override
    public Station findById(long id) {
        con = ConnectionPool.getInstance().getConnection();
        Station station = new Station();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM station WHERE id = ?");
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();
            rs.next();

            station.setName(rs.getString("name"));
            station.setId(id);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return station;
    }

    @Override
    public void insert(Station station) {
        con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO station (name)" +
                    " VALUES (?)");
            st.setString(1, station.getName());
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Station station) {
        con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement st = con.prepareStatement("UPDATE station SET name = ? WHERE id = ?");
            st.setString(1, station.getName());
            st.setLong(2, station.getId());
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Station find(String name) {
        con = ConnectionPool.getInstance().getConnection();
        Station station = new Station();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM station WHERE name = ?");

            st.setString(1, name);

            ResultSet rs = st.executeQuery();
            rs.next();
            station.setName(name);
            station.setId(rs.getLong("id"));
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return station;
    }

    @Override
    public void delete(Station station) {
        con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM station WHERE id = ?");
            st.setLong(1, station.getId());
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Station> findAll() {
        con = ConnectionPool.getInstance().getConnection();
        List<Station> stations = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM station");

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Station station = new Station();
                station.setName(rs.getString("name"));
                station.setId(rs.getLong("id"));

                stations.add(station);
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stations;
    }
}
