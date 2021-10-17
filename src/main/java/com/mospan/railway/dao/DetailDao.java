package com.mospan.railway.dao;

import com.mospan.railway.model.Detail;
import com.mospan.railway.model.Station;
import com.mospan.railway.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DetailDao implements Dao<Detail>{

    Connection con;

    @Override
    public void insert(Detail detail) {
        con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO detail (first_name, last_name, email) VALUES (?, ?, ?)");

            st.setString(1, detail.getFirstName());
            st.setString(2, detail.getLastName());
            st.setString(3, detail.getEmail());

            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Detail detail) {
        con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE detail " +
                    "SET first_name = ?, last_name = ?, email = ?" +
                    "WHERE id = ?");

            st.setString(1, detail.getFirstName());
            st.setString(2, detail.getLastName());
            st.setString(3, detail.getEmail());
            st.setLong(4, detail.getId());

            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Detail find(String email) {
        con = ConnectionPool.getInstance().getConnection();
        Detail detail = new Detail();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM detail WHERE email = ?");

            st.setString(1, email);

            ResultSet rs = st.executeQuery();
            rs.next();

            detail.setFirstName(rs.getString("first_name"));
            detail.setLastName(rs.getString("last_name"));
            detail.setEmail(email);
            detail.setId(rs.getLong("id"));
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detail;

    }

    @Override
    public Detail findById(long id) {
        con = ConnectionPool.getInstance().getConnection();
        Detail detail = new Detail();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM detail WHERE id = ?");

            st.setLong(1, id);

            ResultSet rs = st.executeQuery();
            rs.next();

            detail.setFirstName(rs.getString("first_name"));
            detail.setLastName(rs.getString("last_name"));
            detail.setEmail(rs.getString("email"));
            detail.setId(id);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detail;
    }

    @Override
    public void delete(Detail detail) {
        con = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM detail WHERE id = ?");
            st.setLong(1, detail.getId());
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Detail> findAll() {
        con = ConnectionPool.getInstance().getConnection();
        List<Detail> details = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM detail");

            ResultSet rs = st.executeQuery();
            long id = 1;
            while (rs.next()) {
                Detail detail = findById(id);
                details.add(detail);
                id++;
            }

        con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return details;
    }
}
