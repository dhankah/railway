package com.mospan.railway.controller;

import com.mospan.railway.model.Entity;
import com.mospan.railway.model.Station;
import com.mospan.railway.service.StationService;
import com.mospan.railway.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet (value = "/stations/*")
public class StationController extends ResourceController {
    Validator validator = new Validator();

    @Override
    Entity findModel(String id) {
        try {
            return new StationService().findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * PUT /stations/{id}
     * Updates specified station
     */
    @Override
    protected void update(Entity station, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ((Station) station).setName(req.getParameter("name"));
        if (validator.validateStations((Station)station)) {
            new StationService().update((Station) station);
            resp.sendRedirect(req.getContextPath() + "/stations");
            return;
        }
        req.getSession().setAttribute("errorMessage", "Station with such name already exists");
        resp.sendRedirect(req.getContextPath() + "/stations");
    }

    /**
     * GET /stations/{id}/edit
     * Displays edit form for given station
     */
    @Override
    protected void edit(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("station", (Station) entity);
        req.getRequestDispatcher("/view/stations/edit.jsp").forward(req, resp);
    }

    /**
     * POST /stations
     * Save new stations
     */
    @Override
    protected void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Station station = new Station();
        station.setName(req.getParameter("name"));
        if (validator.validateStations((Station)station)) {
            new StationService().insert(station);
            resp.sendRedirect(req.getContextPath() + "/stations");
            return;
        }
        req.getSession().setAttribute("errorMessage", "Station with such name already exists");
        resp.sendRedirect(req.getContextPath() + "/stations");
    }

    /**
     * GET /stations
     * Displays list of stations
     */
    @Override
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Station> stations = (List<Station>) new StationService().findAll();
        req.setAttribute("stations", stations);
        req.getRequestDispatcher("/view/stations/list.jsp").forward(req, resp);
    }

    /**
     * DELETE stations/{id}
     * Removes specified station from db
     */
    @Override
    protected void delete(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new StationService().delete((Station) entity);
        resp.sendRedirect(req.getContextPath() + "/stations");
    }
}
