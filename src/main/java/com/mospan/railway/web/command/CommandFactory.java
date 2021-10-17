package com.mospan.railway.web.command;

import com.mospan.railway.web.command.commands.*;
import com.mospan.railway.web.command.commands.auth.RegisterCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory factory = new CommandFactory();
    private static Map<String, Command> commands = new HashMap();

    public static CommandFactory commandFactory() {
        if (factory == null) {
            factory = new CommandFactory();
        }
        return factory;
    }

    static {
        commands.put("signup", new RegisterCommand());

        commands.put("stations", new AdminStationCommand());
        commands.put("editStation", new EditStationCommand());
        commands.put("routes", new AdminRouteCommand());
        commands.put("editRoute", new EditRouteCommand());

        commands.put("trains", new FindTrainsCommand());
        commands.put("selectSeat", new SelectSeatCommand());
        commands.put("buyTicket", new BuyTicketCommand());

    }


    public Command getCommand(String action) {
        return commands.get(action);
    }
}
