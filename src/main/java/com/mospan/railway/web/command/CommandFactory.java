package com.mospan.railway.web.command;

import com.mospan.railway.web.command.commands.AdminStationCommand;
import com.mospan.railway.web.command.commands.ClientMainCommand;
import com.mospan.railway.web.command.commands.LoginCommand;

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

        commands.put("client", new ClientMainCommand());
    }


    public Command getCommand(String action) {
        return commands.get(action);
    }
}
