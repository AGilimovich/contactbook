package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.entity.FullContactEntity;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.IDataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class DoUpdateContact implements Command {


    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        FullContactEntity constructedFullContactEntity = ServiceFactory.getServiceFactory().getRequestProcessingService().processContactRequest(request);
        //id of contacted retrieved from session

        FullContactEntity contactToUpdate = (FullContactEntity) request.getSession().getAttribute("contactToUpdate");
        constructedFullContactEntity.getContact();
        IDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        dataService.updateContact(constructedFullContactEntity, contactToUpdate);

        //remove session attributes
        request.getSession().removeAttribute("action");
        request.getSession().removeAttribute("id");
        request.getSession().removeAttribute("contactToUpdate");

        ArrayList<MainPageContactDTO> contacts = dataService.getMainPageContactDTOs();
        request.setAttribute("contacts", contacts);
        return "/jsp/main.jsp";
    }

}

