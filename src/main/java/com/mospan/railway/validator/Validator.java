package com.mospan.railway.validator;

import com.mospan.railway.dao.ConnectionPool;
import com.mospan.railway.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validator {

    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con;

    public boolean validateStations(Station station) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("SELECT * FROM station WHERE name = ?");
            st.setString(1, station.getName());
            ResultSet rs = st.executeQuery();
            rs.next();
            String temp = rs.getString("name");
        } catch (SQLException e) {
            return true;
        } finally {
            cp.closeConnection(con);
        }
        return false;
    }

    public boolean validateUser(User user) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {

            st = con.prepareStatement("SELECT * FROM user WHERE login = ?");
            st.setString(1, user.getLogin());
            ResultSet rs = st.executeQuery();
            rs.next();
            String temp = rs.getString("login");

            st = con.prepareStatement("SELECT * FROM detail WHERE email = ?");
            st.setString(1, user.getDetails().getEmail());
            rs = st.executeQuery();
            rs.next();
            temp = rs.getString("email");


        } catch (SQLException e) {

            return true;
            //validation was successful, there is no user with such credentials
        } finally {
            cp.closeConnection(con);
        }
        //validation was not successful, there are users with such credentials
        return false;
    }



}