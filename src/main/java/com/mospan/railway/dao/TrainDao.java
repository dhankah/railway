package com.mospan.railway.dao;

import com.mospan.railway.model.Detail;
import com.mospan.railway.model.Role;
import com.mospan.railway.model.Train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class TrainDao implements Dao<Train> {

    Connection con = ConnectionPool.getInstance().getConnection();

    @Override
    public void insert(Train entity) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO train (depart_date, arrival_date, available_places, price, " +
                    "number, route_id)" +
                    " VALUES (?, ?, ?, ?, ?, ?)");

           /* st.setString(1, user.getLogin());
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
*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Train entity) {

    }

    @Override
    public Train find(String param) {
        return null;
    }

    @Override
    public Train findById(long id) {
        return null;
    }

    @Override
    public void delete(Train entity) {

    }

    @Override
    public Collection<Train> findAll() {
        return null;
    }
}
