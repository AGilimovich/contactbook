package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.web.service.data.DataService;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.IDataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Implementation of command which shows contacts according to request.
 */
public class ShowContactsView implements Command {

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        IDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        ArrayList<MainPageContactDTO> mainPageContactDTOs = dataService.getMainPageContactDTOs();
        request.setAttribute("contacts", mainPageContactDTOs);

        return "/jsp/main.jsp";
    }


}
