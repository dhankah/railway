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

    public String validateUser(User user, User userUpd) {
        con = cp.getConnection();
        PreparedStatement st = null;
        if (user.getLogin().equals(userUpd.getLogin()) && user.getDetails().getEmail().equals(userUpd.getDetails().getEmail())
        && user.getDetails().getFirstName().equals(userUpd.getDetails().getFirstName()) &&
                user.getDetails().getLastName().equals(userUpd.getDetails().getLastName())) {
            return "no_change";
        }
        try {
            ResultSet rs;
            if (!user.getLogin().equals(userUpd.getLogin())) {
                System.out.println("user " + user.getLogin() + "new" + userUpd.getLogin());
                st = con.prepareStatement("SELECT * FROM user WHERE login = ?");
                st.setString(1, userUpd.getLogin());
                rs = st.executeQuery();
                rs.next();
                String temp = rs.getString("login");
                return "false";
            } else if (!user.getDetails().getEmail().equals(userUpd.getDetails().getEmail())) {
                System.out.println("user " + user.getDetails().getEmail() + "new" + userUpd.getDetails().getEmail());
                st = con.prepareStatement("SELECT * FROM detail WHERE email = ?");
                st.setString(1, userUpd.getDetails().getEmail());
                rs = st.executeQuery();
                rs.next();
                String temp = rs.getString("email");
                return "false";
            }

        } catch (SQLException e) {
            //validation was successful, there is no user with such credentials
            return "true";
        } finally {
            cp.closeConnection(con);
        }
        //validation was not successful, there are users with such credentials
        return "true";
    }


    public boolean validateRegisterUser(User user) {
        con = cp.getConnection();
        PreparedStatement st = null;

        try {
            ResultSet rs;
            st = con.prepareStatement("SELECT * FROM user WHERE login = ?");
            st.setString(1, user.getLogin());
            rs = st.executeQuery();
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