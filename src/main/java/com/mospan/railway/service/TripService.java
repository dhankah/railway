package com.mospan.railway.service;

import com.mospan.railway.dao.interfaces.TripDao;
import com.mospan.railway.model.User;
import com.mospan.railway.util.EmailSender;
import com.mospan.railway.dao.impl.TripDaoImpl;
import com.mospan.railway.model.Route;
import com.mospan.railway.model.Ticket;
import com.mospan.railway.model.Trip;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public class TripService {

    TripDao dao = new TripDaoImpl();

    public void insert(Trip trip) {
        dao.insert(trip);
    }
    public void update(Trip trip) {
        dao.update(trip);
    }
    public Trip findById(long id) {
        return dao.findById(id);
    }
    public void delete(Trip trip) {
        Collection<Ticket> tickets = new TicketService().findTicketsForTrip(trip);
        for (Ticket ticket : tickets) {
            if (ticket.getTrip().getDepartDate().isAfter(LocalDate.now()) ||
                    ticket.getTrip().getDepartDate().isEqual(LocalDate.now()) && ticket.getTrip().getRoute().getDepartTime().isAfter(LocalTime.now())) {
                EmailSender.sendTicketNotification(ticket, "trip_cancel");
                User user = ticket.getUser();
                user.setBalance(user.getBalance() + ticket.getTrip().getRoute().getPrice());
                new UserService().update(user);
            }
            new TicketService().delete(ticket, false);
        }
        dao.delete(trip);
    }
    public Collection<Trip> findAll() {
        return dao.findAll();
    }

    public Collection<Trip> findRecords(Route route, LocalDate date) {
        return dao.findRecords(route, date);
    }
    public Collection<Trip> findTripsForRoute(Route route){
        return dao.findTripsForRoute(route);
    }
}
