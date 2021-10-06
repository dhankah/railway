package com.mospan.railway.web.command;

import com.mospan.railway.web.command.commands.*;

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
        commands.put("login", new LoginCommand());

        commands.put("stations", new AdminStationCommand());
        commands.put("editStation", new EditStationCommand());
        commands.put("routes", new AdminRouteCommand());

        commands.put("client", new ClientMainCommand());
    }


    public Command getCommand(String action) {
        return commands.get(action);
    }
}
