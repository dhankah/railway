package com.mospan.railway.controller;

import com.mospan.railway.model.Entity;
import com.mospan.railway.model.Route;
import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.StationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;


@WebServlet (value = "/routes/*")
public class RouteController extends ResourceController{

    @Override
    Entity findModel(String id) {
        try {
            return new RouteService().findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }


    @Override
    protected void update(Entity route, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ((Route) route).setDepartTime(LocalTime.parse(req.getParameter("depart_time")));
        ((Route) route).setTime(convertTime(req));

        ((Route) route).setStartStation(new StationService().find(req.getParameter("start_station")));
        ((Route) route).setEndStation(new StationService().find(req.getParameter("end_station")));

        ((Route) route).setPrice(Double.parseDouble(req.getParameter("price")));

        new RouteService().update((Route) route);
        resp.sendRedirect(req.getContextPath() + "/routes");
    }


    @Override
    protected void edit(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("route", (Route) entity);
        req.setAttribute("stations", new StationService().findAll());
        req.getRequestDispatcher("/view/routes/edit.jsp").forward(req, resp);
    }

    @Override
    protected void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Route route = new Route();

        ((Route) route).setDepartTime(LocalTime.parse(req.getParameter("depart_time")));

        ((Route) route).setTime(convertTime(req));
        ((Route) route).setStartStation(new StationService().find(req.getParameter("start_station")));
        ((Route) route).setEndStation(new StationService().find(req.getParameter("end_station")));
        ((Route) route).setPrice(Double.parseDouble(req.getParameter("price")));
        new RouteService().insert(route);
        resp.sendRedirect(req.getContextPath() + "/routes");
    }

    @Override
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("time", LocalTime.MIDNIGHT);
        req.setAttribute("stations", new StationService().findAll());
        req.setAttribute("routes", new RouteService().findAll());
        req.getRequestDispatcher("/view/routes/list.jsp").forward(req, resp);
    }


    @Override
    protected void delete(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new RouteService().delete((Route) entity);
        resp.sendRedirect(req.getContextPath() + "/routes");
    }

    private long convertTime(HttpServletRequest req) {
        Long days = (!"".equals(req.getParameter("days"))) ? Long.parseLong(req.getParameter("days")) : 0;
        Long hours = (!"".equals(req.getParameter("hours"))) ? Long.parseLong(req.getParameter("hours")) : 0;
        Long minutes = Long.parseLong(req.getParameter("minutes"));

        hours += days * 24;
        minutes += hours * 60;
        return minutes * 60;

    }
}
