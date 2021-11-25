package com.mospan.railway.service;


import com.mospan.railway.dao.impl.TicketDaoImpl;
import com.mospan.railway.dao.interfaces.TicketDao;
import com.mospan.railway.model.Ticket;
import com.mospan.railway.model.Trip;
import com.mospan.railway.util.EmailSender;

import java.util.Collection;
import java.util.List;

public class TicketService {
    TicketDao dao = new TicketDaoImpl();

    public void insert(Ticket ticket) {
        dao.insert(ticket);
    }
    public void update(Ticket ticket) {
        dao.update(ticket);
    }
    public Ticket findById(long id) {
        return dao.findById(id);
    }
    public void delete(Ticket ticket, boolean needNotif) {
        if (needNotif) {
            EmailSender.sendTicketNotification(ticket, "ticket_return");
        }
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
