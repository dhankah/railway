package com.mospan.railway.dao.interfaces;

import com.mospan.railway.model.Ticket;
import com.mospan.railway.model.Trip;

import java.util.Collection;
import java.util.List;

public interface TicketDao {
    void insert(Ticket ticket);
    void update(Ticket ticket);
    Ticket findById(long id);
    void delete(Ticket ticket);
    Collection<Ticket> findAll();
    Collection<Integer> findSeats(Trip trip);
    List<List<Ticket>> findAllForUser(long id);
    Collection<Ticket> findTicketsForTrip(Trip trip);

}
