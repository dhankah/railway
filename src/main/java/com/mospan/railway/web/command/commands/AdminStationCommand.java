package com.mospan.railway.web.command.commands;

import com.mospan.railway.model.Station;
import com.mospan.railway.service.StationService;
import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;


public class AdminStationCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        StationService stationService = new StationService();
        Collection<Station> stations = stationService.findAll();
        request.getSession().setAttribute("stations", stations);
        return "admin_station.jsp";
    }
}
