package com.mospan.railway.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mospan.railway.model.Ticket;
import com.mospan.railway.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;

@WebServlet(value = "/download/*")
public class Download extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        long id = Long.parseLong(path.substring(1));
        System.out.println(id);
        try {

            Ticket ticket = new TicketService().findById(id);
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("ticket.pdf"));

            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk name = new Chunk("Passenger name: " + ticket.getUser().getDetails().getFirstName() + " " + ticket.getUser().getDetails().getLastName(), font);
            Chunk trainNumber = new Chunk("train number №" + ticket.getTrip().getRoute().getId(), font);
            Chunk departStation = new Chunk("Depart station: " + ticket.getTrip().getRoute().getStartStation(), font);
            Chunk arrivalStation = new Chunk("Arrival station: " + ticket.getTrip().getRoute().getEndStation(), font);
            Chunk depart = new Chunk("Depart on: " + ticket.getTrip().getDepartDate() + ticket.getTrip().getRoute().getDepartTime(), font);
            Chunk arrive = new Chunk("Arrive on: " + ticket.getTrip().getArrivalDate() + ticket.getTrip().getRoute().getArrivalTime(), font);


            document.add(name);
            document.add(trainNumber);
            document.add(departStation);
            document.add(arrivalStation);
            document.add(depart);
            document.add(arrive);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
