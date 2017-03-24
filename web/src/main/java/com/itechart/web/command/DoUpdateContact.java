package com.itechart.web.command;

import com.itechart.data.dto.ContactWithAddressDTO;
import com.itechart.data.entity.Contact;
import com.itechart.web.service.ServiceFactory;

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
        ServiceFactory.getServiceFactory().getRequestProcessingService().processUpdatingRequest(request);
        ArrayList<ContactWithAddressDTO> contacts = ServiceFactory.getServiceFactory().getDataService().getContactsWithAddressDTO();

        request.setAttribute("contacts", contacts);
        return "/jsp/main.jsp";
    }

}

