package com.itechart.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface of command which is then executed by controller.
 */
public interface Command {
    String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException;
}
