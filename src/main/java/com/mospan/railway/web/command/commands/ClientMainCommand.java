package com.mospan.railway.web.command.commands;

import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientMainCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "client_trains.jsp";
    }
}
