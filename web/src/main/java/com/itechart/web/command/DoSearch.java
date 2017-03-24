package com.itechart.web.command;

import com.itechart.web.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class DoSearch implements Command {


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ServiceFactory.getServiceFactory().getRequestProcessingService().processSearchRequest(request);
        return "/jsp/main.jsp";
    }
}
