package com.itechart.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for command which is then executed by controller.
 */
public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException;
}
