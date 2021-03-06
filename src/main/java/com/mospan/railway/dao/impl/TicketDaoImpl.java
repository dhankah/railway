package com.mospan.railway.dao.impl;

import com.mospan.railway.dao.ConnectionPool;
import com.mospan.railway.dao.interfaces.TicketDao;
import com.mospan.railway.model.Ticket;
import com.mospan.railway.model.Trip;
import com.mospan.railway.service.TripService;
import com.mospan.railway.service.UserService;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TicketDaoImpl implements TicketDao {
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con;

    @Override
    public void insert(Ticket ticket) {
        con = cp.getConnection();

        try ( PreparedStatement st = con.prepareStatement("INSERT INTO ticket (user_id, trip_id, seat) VALUES (?, ?, ?)")) {
            st.setLong(1, ticket.getUser().getId());
            st.setLong(2, ticket.getTrip().getId());
            st.setInt(3, ticket.getSeat());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public void update(Ticket ticket) {
        con = cp.getConnection();

        try (PreparedStatement st = con.prepareStatement("UPDATE ticket SET (user_id, trip_id, seat) VALUES (?, ?, ?) WHERE id = ?")) {

            st.setLong(1, ticket.getUser().getId());
            st.setLong(2, ticket.getTrip().getId());
            st.setInt(3, ticket.getSeat());
            st.setLong(4, ticket.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }


    @Override
    public Ticket findById(long id) {
        con = cp.getConnection();
        Ticket ticket = new Ticket();
        UserService userService = new UserService();
        TripService tripService = new TripService();
        try (PreparedStatement st = con.prepareStatement("SELECT * FROM ticket WHERE id = ?")) {
            st.setLong(1,id);

            ResultSet rs = st.executeQuery();
            rs.next();
            ticket.setId(rs.getLong("id"));
            ticket.setUser(userService.findById(rs.getLong("user_id")));
            ticket.setTrip(tripService.findById(rs.getLong("trip_id")));
            ticket.setSeat(rs.getInt("seat"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
        return ticket;
    }

    @Override
    public void delete(Ticket ticket) {
        con = cp.getConnection();

        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM ticket WHERE id = ?");
            st.setLong(1, ticket.getId());
            st.executeUpdate();

            st = con.prepareStatement("UPDATE trip SET available_places = ? WHERE id = ?");
            st.setLong(1, ticket.getTrip().getAvailablePlaces() + 1);
            st.setLong(2, ticket.getTrip().getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
    }

    @Override
    public Collection<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        con = cp.getConnection();

        try (PreparedStatement st = con.prepareStatement("SELECT * FROM trip");) {

            ResultSet rs = st.executeQuery();
            long id = 1;
            while (rs.next()) {
                Ticket ticket = findById(id);
                tickets.add(ticket);
                id++;
                cp.closeConnection(con);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    public Collection<Integer> findSeats(Trip trip) {
        List<Integer> seats = new ArrayList<>();
        con = cp.getConnection();
        try (PreparedStatement st = con.prepareStatement("SELECT seat FROM ticket WHERE trip_id = ?")) {
            st.setLong(1, trip.getId());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                seats.add(rs.getInt("seat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.closeConnection(con);
        }
        return seats;
    }



    @Override
    public List<List<Ticket>> findAllForUser(long id) {

        con = cp.getConnection();
        List<List<Ticket>> tickets = new ArrayList<>();
        List<Ticket> upcomingTickets = new ArrayList<>();
        List<Ticket> oldTickets = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement("SELECT * FROM ticket WHERE user_id = ?")) {
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getLong("id"));
                ticket.setUser(new UserService().findById(id));
                ticket.setSeat(rs.getInt("seat"));
                ticket.setTrip(new TripService().findById(rs.getLong("trip_id")));

                if (ticket.getTrip().getDepartDate().isBefore(LocalDate.now())) {

                    oldTickets.add(ticket);
                } else if (ticket.getTrip().getDepartDate().isAfter(LocalDate.now())) {

                    upcomingTickets.add(ticket);
                } else if (ticket.getTrip().getRoute().getDepartTime().isBefore(LocalTime.now())) {

                    oldTickets.add(ticket);
                } else {
                    upcomingTickets.add(ticket);
                }
            }
            tickets.add(oldTickets);
            tickets.add(upcomingTickets);

            if (tickets.get(1).isEmpty()) {
                tickets.set(1, null);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            cp.closeConnection(con);
        }
        return tickets;
    }

    @Override
    public Collection<Ticket> findTicketsForTrip(Trip trip) {
        con = cp.getConnection();
        List<Ticket> tickets = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement("SELECT * FROM ticket WHERE trip_id = ?")) {
            st.setLong(1, trip.getId());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getLong("id"));
                ticket.setUser(new UserService().findById(rs.getLong("user_id")));
                ticket.setSeat(rs.getInt("seat"));
                ticket.setTrip(new TripService().findById(rs.getLong("trip_id")));

                tickets.add(ticket);

            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            cp.closeConnection(con);
        }
        return tickets;
    }
}
