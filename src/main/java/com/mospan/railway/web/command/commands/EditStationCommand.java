package com.mospan.railway.web.command.commands;

import com.mospan.railway.model.Station;
import com.mospan.railway.service.StationService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditStationCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        StationService stationService = new StationService();
        if (request.getParameter("nameForNew") != null) {
            Station station = new Station();
            station.setName(request.getParameter("nameForNew"));
            stationService.insert(station);
            return (new AdminStationCommand()).execute(request, response);
        }
        if (request.getParameter("stationToDelete") != null) {
            Station station = stationService.find((request.getParameter("stationToDelete")));
            stationService.delete(station);
            return (new AdminStationCommand()).execute(request, response);
        }
        if (request.getParameter("stationName") != null) {
            Station station = stationService.find(request.getParameter("stationName"));
            request.getSession().setAttribute("station", station);
            return "edit.jsp";
        }
        if (request.getParameter("name") != null) {
            Station station = stationService.find(request.getParameter("prevName"));
            station.setName(request.getParameter("name"));
            stationService.update(station);
        }
        return (new AdminStationCommand()).execute(request, response);
    }
}
