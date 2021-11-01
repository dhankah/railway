package com.mospan.railway.controller;

import com.mospan.railway.model.Entity;
import com.mospan.railway.model.Station;
import com.mospan.railway.service.StationService;
import com.mospan.railway.validator.Validator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet (value = "/stations/*")
public class StationController extends ResourceController {
    private static final Logger logger = Logger.getLogger(StationController.class);
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
    protected void update(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("updating station " + entity.getId());
        req.setCharacterEncoding("UTF-8");
        Station station = ((Station) entity);
        station.setName(req.getParameter("name"));

        if (validator.validateStations(station)) {
            logger.info("updated station " + station.getId() + " successfuly");
            new StationService().update(station);
            resp.sendRedirect(req.getContextPath() + "/stations/1/page");
            return;
        }
        ResourceBundle ua = ResourceBundle.getBundle("i18n.resources", new Locale("ua"));
        ResourceBundle en = ResourceBundle.getBundle("i18n.resources", new Locale("en"));
        logger.info("updating station " + station.getId() + " failed");
        if (req.getSession().getAttribute("defaultLocale").equals("ua")) {
            req.getSession().setAttribute("errorMessage", ua.getString("station_exists"));
        } else {
            req.getSession().setAttribute("errorMessage", en.getString("station_exists"));
        }
        resp.sendRedirect(req.getContextPath() + "/stations/1/page");
    }

    /**
     * GET /stations/{id}/edit
     * Displays edit form for given station
     */
    @Override
    protected void edit(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("forwarding to station edit page");
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("station", (Station) entity);
        req.getRequestDispatcher("/view/stations/edit.jsp").forward(req, resp);
    }

    /**
     * POST /stations
     * Save new stations
     */
    @Override
    protected void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("saving station");
        Station station = new Station();
        station.setName(req.getParameter("name"));
        if (validator.validateStations(station)) {
            logger.info("station " + station.getName() + " saved successfully");
            new StationService().insert(station);
            resp.sendRedirect(req.getContextPath() + "/stations/1/page");
            return;
        }
        ResourceBundle ua = ResourceBundle.getBundle("i18n.resources", new Locale("ua"));
        ResourceBundle en = ResourceBundle.getBundle("i18n.resources", new Locale("en"));

        logger.info("saving station " + station.getName() + " failed");
        if (req.getSession().getAttribute("defaultLocale").equals("ua")) {
            req.getSession().setAttribute("errorMessage", ua.getString("station_exists"));
        } else {
            req.getSession().setAttribute("errorMessage", en.getString("station_exists"));
        }

        resp.sendRedirect(req.getContextPath() + "/stations/1/page");
    }

    /**
     * GET /stations
     * Displays list of stations
     */
    @Override
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
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
        logger.info("deleting station " + ((Station) entity).getName());
        new StationService().delete((Station) entity);
        resp.sendRedirect(req.getContextPath() + "/stations/1/page");
    }

    @Override
    protected void goToPage(long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("forwarding to page " + id + " of stations");
        List<Station> stationsForList = (List<Station>) new StationService().findAll();
        req.setAttribute("stations", stationsForList);

        int size = new StationService().findAll().size();
        int pages = size % 10 == 0 ? size / 10 : size / 10 + 1;
        req.setAttribute("pages", pages);

        List<Station> stations = (List<Station>) new StationService().findRecords(id);
        req.setAttribute("stations", stations);
        req.getRequestDispatcher("/view/stations/list.jsp").forward(req, resp);
    }
}