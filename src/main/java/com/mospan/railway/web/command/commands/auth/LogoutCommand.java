package com.mospan.railway.web.command.commands.auth;

import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String locale = (String) request.getSession().getAttribute("defaultLocale");
        request.getSession().invalidate();
        request.getSession().setAttribute("defaultLocale", locale);
        return request.getContextPath() + "/auth/login";
    }
}
