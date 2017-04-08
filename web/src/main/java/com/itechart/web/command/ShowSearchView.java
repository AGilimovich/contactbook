package com.itechart.web.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command executed on request to show contacts search view.
 */
public class ShowSearchView implements Command {
    private Logger logger = LoggerFactory.getLogger(ShowEmailView.class);

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");
        return "/jsp/search.jsp";
    }
}
