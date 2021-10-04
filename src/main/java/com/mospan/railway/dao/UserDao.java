package com.mospan.railway.dao;

import com.mospan.railway.model.Detail;
import com.mospan.railway.model.Role;
import com.mospan.railway.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class UserDao implements Dao<User>{

    Connection con = ConnectionPool.getInstance().getConnection();


    @Override
    public void insert(User user) {

        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO user (login, password, role_id, detail_id)" +
                    " VALUES (?,?, ?, ?)");

            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());

            PreparedStatement stDetail = con.prepareStatement("INSERT INTO detail (first_name, last_name, email) VALUES (?, ?, ?)");

            stDetail.setString(1, user.getDetails().getFirstName());
            stDetail.setString(1, user.getDetails().getLastName());
            stDetail.setString(1, user.getDetails().getEmail());

            stDetail.executeUpdate();

            ResultSet rs = stDetail.executeQuery("SELECT LAST_INSERT_ID()");
            rs.next();

            st.setLong(4, rs.getLong(1));

            if (user.getRole().equals(Role.ADMIN)) {
                st.setLong(3, 0);
            }
            else if (user.getRole().equals(Role.CLIENT)) {
                st.setLong(3, 1);
            }

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM user WHERE id = ?");
            st.setLong(1, user.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE user WHERE id = ? SET (login, password) VALUES (?, ?)");
            st.setLong(1, user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(String login) {
        User user = new User();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM user WHERE login = ?");

            st.setString(1, login);

            ResultSet rs = st.executeQuery();
            rs.next();

            user.setLogin(login);
            user.setPassword(rs.getString("password"));
            user.setId(rs.getLong("id"));

            PreparedStatement detailSt = con.prepareStatement("SELECT * FROM detail WHERE id = ?");
            detailSt.setLong(1, rs.getLong("detail_id"));

            ResultSet detailRs = detailSt.executeQuery();
            detailRs.next();

            Detail detail = new Detail();
            detail.setId(rs.getLong("detail_id"));
            detail.setFirstName(detailRs.getString("first_name"));
            detail.setLastName(detailRs.getString("last_name"));
            detail.setEmail(detailRs.getString("email"));

            user.setDetails(detail);

            if (rs.getInt("role_id") == 0) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.CLIENT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }
}
