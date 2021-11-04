package com.mospan.railway.service;


import com.mospan.railway.dao.TicketDao;
import com.mospan.railway.model.Ticket;
import com.mospan.railway.model.Trip;

import java.util.Collection;
import java.util.List;

public class TicketService {
    TicketDao dao = new TicketDao();

    public void insert(Ticket ticket) {
        dao.insert(ticket);
    }
    public void update(Ticket ticket) {
        dao.update(ticket);
    }
    public Ticket find(String param) {
        return dao.find(param);
    }
    public Ticket findById(long id) {
        return dao.findById(id);
    }
    public void delete(Ticket ticket) {
        dao.delete(ticket);
    }
    public Collection<Ticket> findAll() {
        return dao.findAll();
    }
    public Collection<Integer> findSeats(Trip trip) {
        return dao.findSeats(trip);
    }
    public List<List<Ticket>> findAllForUser(long id) {
        return dao.findAllForUser(id);
    }
    public Collection<Ticket> findTicketsForTrip(Trip trip) {
        return dao.findTicketsForTrip(trip);
    }

}
