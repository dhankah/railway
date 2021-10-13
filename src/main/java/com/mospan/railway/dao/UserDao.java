package com.mospan.railway.dao;

import com.mospan.railway.model.Detail;
import com.mospan.railway.model.Role;
import com.mospan.railway.model.User;
import com.mospan.railway.service.DetailService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDao implements Dao<User>{

    Connection con;
    DetailService detailService = new DetailService();

    @Override
    public void insert(User user) {
        con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO user (login, password, role_id, detail_id)" +
                    " VALUES (?, ?, ?, ?)");

            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());

            Detail detail = user.getDetails();
            detailService.insert(detail);


            st.setLong(4, detailService.find(detail.getEmail()).getId());

            if (user.getRole().equals(Role.ADMIN)) {
                st.setLong(3, 0);
            }
            else if (user.getRole().equals(Role.CLIENT)) {
                st.setLong(3, 1);
            }

            st.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(User user) {
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM user WHERE id = ?");
            st.setLong(1, user.getId());

            detailService.delete(detailService.findById(user.getId()));

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE user SET (login, password) VALUES (?, ?) WHERE id = ?");

            st.setLong(1, user.getId());
            st.setString(2, user.getLogin());
            st.setString(3, user.getPassword());

            st.executeUpdate();

            Detail detail = user.getDetails();
            detailService.update(detail);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(String login) {
        User user = new User();

        con = ConnectionPool.getInstance().getConnection();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM user WHERE login = ?");

            st.setString(1, login);

            ResultSet rs = st.executeQuery();
            rs.next();

            user.setLogin(login);
            user.setPassword(rs.getString("password"));
            user.setId(rs.getLong("id"));

            user.setDetails(detailService.findById(rs.getLong("detail_id")));


            if (rs.getInt("role_id") == 0) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.CLIENT);
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    @Override
    public User findById(long id) {
        con = ConnectionPool.getInstance().getConnection();
        User user = new User();
        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM user WHERE id = ?");
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();
            rs.next();

            user.setId(id);
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));

            if (rs.getInt("role_id") == 0) {
                user.setRole(Role.ADMIN);
            }
            else if (rs.getInt("role_id") == 1) {
                user.setRole(Role.CLIENT);
            }

            PreparedStatement stDetail = con.prepareStatement("SELECT detail_id FROM user WHERE id = ?");
            stDetail.setLong(1, id);

            ResultSet rsDetail = stDetail.executeQuery();
            rs.next();

            user.setDetails(detailService.findById(rs.getLong("detail_id")));
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public Collection<User> findAll() {
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM user");

            ResultSet rs = st.executeQuery();
            long id = 1;
            while (rs.next()) {
               User user = findById(id);
               users.add(user);
               id++;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
