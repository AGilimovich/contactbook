package com.itechart.web.controller;

import com.itechart.web.command.Command;
import com.itechart.web.command.CommandFactory;
import com.itechart.web.properties.PropertiesManager;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.scheduler.AbstractSchedulingService;
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

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        int scheduledHours = Integer.valueOf(PropertiesManager.scheduledHours());
        int scheduledMinutes = Integer.valueOf(PropertiesManager.scheduledMinutes());
        AbstractSchedulingService schedulingService = ServiceFactory.getServiceFactory().getEmailCongratsService();
        schedulingService.startScheduler(scheduledHours, scheduledMinutes);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.getCommand(request);
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


