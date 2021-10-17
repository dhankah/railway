package com.mospan.railway.dao;

import com.mospan.railway.model.Ticket;
import com.mospan.railway.model.Trip;
import com.mospan.railway.model.User;
import com.mospan.railway.service.TripService;
import com.mospan.railway.service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TicketDao implements Dao<Ticket>{

    Connection con;

    @Override
    public void insert(Ticket ticket) {
        con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO ticket (user_id, trip_id, seat) VALUES (?, ?, ?)");
            st.setLong(1, ticket.getUser().getId());
            st.setLong(2, ticket.getTrip().getId());
            st.setInt(3, ticket.getSeat());
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Ticket ticket) {
        con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE ticket SET (user_id, trip_id, seat) VALUES (?, ?, ?) WHERE id = ?");

            st.setLong(1, ticket.getUser().getId());
            st.setLong(2, ticket.getTrip().getId());
            st.setInt(3, ticket.getSeat());
            st.setLong(4, ticket.getId());
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ticket find(String param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Ticket findById(long id) {
        con = ConnectionPool.getInstance().getConnection();
        Ticket ticket = new Ticket();
        UserService userService = new UserService();
        TripService tripService = new TripService();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("SELECT * FROM ticket WHERE id = ?");
            st.setLong(1,id);

            ResultSet rs = st.executeQuery();
            rs.next();
            ticket.setId(rs.getLong("id"));
            ticket.setUser(userService.findById(rs.getLong("user_id")));
            ticket.setTrip(tripService.findById(rs.getLong("trip_id")));
            ticket.setSeat(rs.getInt("seat"));

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public void delete(Ticket ticket) {
        con = ConnectionPool.getInstance().getConnection();

        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM ticket WHERE id = ?");
            st.setLong(1, ticket.getId());
            st.executeUpdate();

            st = con.prepareStatement("UPDATE trip SET available_places = ? WHERE id = ?");
            st.setLong(1, ticket.getTrip().getAvailablePlaces() + 1);
            st.setLong(2, ticket.getTrip().getId());
            st.executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        con = ConnectionPool.getInstance().getConnection();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM trip");

            ResultSet rs = st.executeQuery();
            long id = 1;
            while (rs.next()) {
                Ticket ticket = findById(id);
                tickets.add(ticket);
                id++;
                con.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    public Collection<Integer> findSeats(Trip trip) {
        List<Integer> seats = new ArrayList<>();
        con = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("SELECT seat FROM ticket WHERE trip_id = ?");
            st.setLong(1, trip.getId());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                seats.add(rs.getInt("seat"));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public List<Ticket> findAllForUser(long id) {
        con = ConnectionPool.getInstance().getConnection();
        List<Ticket> tickets = new ArrayList<>();

        try {
            PreparedStatement st = null;
            st = con.prepareStatement("SELECT * FROM ticket WHERE user_id = ?");
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getLong("id"));
                ticket.setUser(new UserService().findById(id));
                ticket.setSeat(rs.getInt("seat"));
                ticket.setTrip(new TripService().findById(rs.getLong("trip_id")));
                tickets.add(ticket);
            }

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }
}
