package com.mospan.railway.dao;

import com.mospan.railway.model.Detail;
import com.mospan.railway.model.Role;
import com.mospan.railway.model.User;
import com.mospan.railway.service.DetailService;
import com.mospan.railway.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDao implements Dao<User>{
    Validator validator = new Validator();
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con;
    DetailService detailService = new DetailService();

    @Override
    public void insert(User user) {
        con = cp.getConnection();
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

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

    }

    @Override
    public void delete(User user) {
        con = cp.getConnection();
        new DetailService().delete(user.getDetails());
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM user WHERE id = ?");
            st.setLong(1, user.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public void update(User user) {
        con = cp.getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE user SET login = ?, password = ? WHERE id = ?");


            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setLong(3, user.getId());

            st.executeUpdate();

            Detail detail = user.getDetails();
            detailService.update(detail);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public User find(String login) {
        User user = new User();

        con = cp.getConnection();

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

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            cp.closeConnection(con);
        }
        return user;
    }

    @Override
    public User findById(long id) {
        con = cp.getConnection();
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


            user.setDetails(detailService.findById(rs.getLong("detail_id")));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }

        return user;
    }

    @Override
    public Collection<User> findAll() {
        con = cp.getConnection();
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
        } finally {
            cp.closeConnection(con);
        }

        return users;
    }
}
