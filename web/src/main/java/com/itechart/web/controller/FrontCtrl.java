package com.itechart.web.controller;

import com.itechart.web.command.Command;
import com.itechart.web.command.CommandFactory;
import com.itechart.web.properties.PropertiesManager;
import com.itechart.web.service.scheduler.SchedulerStarter;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is controller of all requests on the main form.
 */
public class FrontCtrl extends HttpServlet {

    private static final Logger log = Logger.getLogger(FrontCtrl.class);
    private CommandFactory commandFactory;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        commandFactory = new CommandFactory();
        int scheduledHours = Integer.valueOf(PropertiesManager.scheduledHours());
        int scheduledMinutes = Integer.valueOf(PropertiesManager.scheduledMinutes());
        new SchedulerStarter().startScheduler(scheduledHours, scheduledMinutes);

    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = commandFactory.getCommand(request);
        String page = command.execute(this, request, response);
        if (page != null)
            dispatch(request, response, page);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("HTTP GET запрос");
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("HTTP POST запрос");
        processRequest(request, response);
    }

    protected void dispatch(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

}


