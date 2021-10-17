package com.mospan.railway.web.command.commands.auth;

import com.mospan.railway.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        return request.getContextPath() + "/auth/login";
    }
}
