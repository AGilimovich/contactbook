package com.itechart.web.controller;

import com.itechart.web.command.Command;
import com.itechart.web.command.CommandFactory;
import com.itechart.web.command.dispatcher.ErrorDispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is controller of all requests on the formatter form.
 */
public class FrontCtrl extends HttpServlet {


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.getCommand(request);
        String page = null;
        if (command != null) {
            page = command.execute(this, request, response);
            if (!response.isCommitted()) {
                if (page != null)
                    dispatch(request, response, page);
                else
                    ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_NOT_FOUND);
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void dispatch(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }

}


