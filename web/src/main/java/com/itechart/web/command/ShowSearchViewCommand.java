package com.itechart.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Invokes contact searching view.
 */
public class ShowSearchViewCommand implements Command {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        return "/jsp/search.jsp";
    }
}
