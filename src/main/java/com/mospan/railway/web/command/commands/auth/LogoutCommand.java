package com.mospan.railway.web.command.commands.auth;

import com.mospan.railway.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {

    private static final Logger logger = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("logging out");
        String locale = (String) request.getSession().getAttribute("defaultLocale");
        request.getSession().invalidate();
        request.getSession().setAttribute("defaultLocale", locale);
        return request.getContextPath() + "/auth/login";
    }
}
