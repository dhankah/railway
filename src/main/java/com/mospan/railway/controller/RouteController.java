package com.mospan.railway.controller;

import com.mospan.railway.model.Entity;
import com.mospan.railway.model.Route;

import com.mospan.railway.service.RouteService;
import com.mospan.railway.service.StationService;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.LocalTime;

import java.util.List;


@WebServlet (value = "/routes/*")
public class RouteController extends ResourceController{
    private static final Logger logger = Logger.getLogger(RouteController.class);
    @Override
    Entity findModel(String id) {
        try {
            return new RouteService().findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * PUT /routes/{id}
     * Updates specified route
     */
    @Override
    protected void update(Entity route, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("updating route " + route.getId());
        ((Route) route).setDepartTime(LocalTime.parse(req.getParameter("depart_time")));
        ((Route) route).setTime(convertTime(req));

        ((Route) route).setStartStation(new StationService().find(req.getParameter("start_station")));
        ((Route) route).setEndStation(new StationService().find(req.getParameter("end_station")));

        ((Route) route).setPrice(Double.parseDouble(req.getParameter("price")));

        new RouteService().update((Route) route);
        resp.sendRedirect(req.getContextPath() + "/routes/1/page");
    }

    /**
     * GET /routes/{id}/edit
     * Displays edit form for given route
     */
    @Override
    protected void edit(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("redirecting to route edit page");
        req.setAttribute("route", (Route) entity);
        req.setAttribute("stations", new StationService().findAll());
        req.getRequestDispatcher("/view/routes/edit.jsp").forward(req, resp);
    }

    /**
     * POST /routes
     * Save new routes
     */
    @Override
    protected void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("saving a new route");
        Route route = new Route();

        route.setDepartTime(LocalTime.parse(req.getParameter("depart_time")));

        route.setTime(convertTime(req));
        route.setStartStation(new StationService().find(req.getParameter("start_station")));
        route.setEndStation(new StationService().find(req.getParameter("end_station")));
        route.setPrice(Double.parseDouble(req.getParameter("price")));
        new RouteService().insert(route);
        resp.sendRedirect(req.getContextPath() + "/routes/1/page");
    }

    /**
     * GET /routes
     * Displays list of routes
     */
    @Override
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        goToPage(1, req, resp);
    }

    /**
     * DELETE routes/{id}
     * Removes a specified route from db
     */
    @Override
    protected void delete(Entity route, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("deleting route " + route.getId());
        new RouteService().delete((Route) route);
        resp.sendRedirect(req.getContextPath() + "/routes/1/page");
    }

    /**
     * converting time from days, hours, minutes to seconds format
     */
    private long convertTime(HttpServletRequest req) {
        long days = (!"".equals(req.getParameter("days"))) ? Long.parseLong(req.getParameter("days")) : 0;
        long hours = (!"".equals(req.getParameter("hours"))) ? Long.parseLong(req.getParameter("hours")) : 0;
        long minutes = (!"".equals(req.getParameter("minutes"))) ? Long.parseLong(req.getParameter("minutes")) : 0;

        hours += days * 24;
        minutes += hours * 60;
        return minutes * 60;
    }

    /**
     * GET /routes/{id}/page
     * Displays list of routes for the page {id}
     */
    @Override
    protected void goToPage(long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("forwarding to page " + id + " of routes");
        req.setAttribute("time", LocalTime.MIDNIGHT);
        req.setAttribute("stations", new StationService().findAll());
        req.setAttribute("routes", new RouteService().findAll());

        int size = new RouteService().findAll().size();
        int pages = size % 5 == 0 ? size / 5 : size / 5 + 1;
        req.setAttribute("pages", pages);

        List<Route> routes = (List<Route>) new RouteService().findRecords(id);
        req.setAttribute("routes", routes);
        req.getRequestDispatcher("/view/routes/list.jsp").forward(req, resp);
    }


}
