package com.mospan.railway.dao;

import com.mospan.railway.model.Detail;
import com.mospan.railway.model.Role;
import com.mospan.railway.model.Station;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StationDao implements Dao<Station>{

    Connection con = ConnectionPool.getInstance().getConnection();

    @Override
    public Station findById(long id) {
        return null;
    }

    @Override
    public void insert(Station entity) {

    }

    @Override
    public void update(Station entity) {

    }

    @Override
    public Station find(String name) {
        Station station = new Station();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM station WHERE name = ?");

            st.setString(1, name);

            ResultSet rs = st.executeQuery();
            rs.next();

            station.setName(name);
            station.setId(rs.getLong("id"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return station;
    }

    @Override
    public void delete(Station entity) {

    }

    @Override
    public Collection<Station> findAll() {
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


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stations;
    }
}
