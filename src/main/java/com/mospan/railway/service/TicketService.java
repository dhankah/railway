package com.mospan.railway.service;

import com.mospan.railway.dao.Dao;
import com.mospan.railway.dao.TicketDao;
import com.mospan.railway.model.Ticket;

import java.util.Collection;

public class TicketService {
    Dao<Ticket> dao = new TicketDao();

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
}
