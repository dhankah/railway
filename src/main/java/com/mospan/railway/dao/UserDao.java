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
    public void insert(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void update(long id, User entity) {

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
