package com.itechart.web.command;

import com.itechart.data.dto.ContactWithAddressDTO;
import com.itechart.data.dto.FullContact;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;
import com.itechart.web.service.DataService;
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
        FullContact receivedFullContact = ServiceFactory.getServiceFactory().getRequestProcessingService().processContactRequest(request);

        long contactId = (long) request.getSession().getAttribute("id");
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        Contact contactToUpdate = dataService.getContactById(contactId);
        Address addressToUpdate = dataService.getAddressById(contactToUpdate.getAddress());
        contactToUpdate.update(receivedFullContact.getContact());
        addressToUpdate.update(receivedFullContact.getAddress());

        FullContact fullContact = new FullContact(contactToUpdate, addressToUpdate, receivedFullContact.getNewPhones(), receivedFullContact.getNewAttachments());
        fullContact.setUpdatedPhones(receivedFullContact.getUpdatedPhones());
        fullContact.setDeletedPhones(receivedFullContact.getDeletedPhones());
        fullContact.setUpdatedAttachments(receivedFullContact.getUpdatedAttachments());
        fullContact.setDeletedAttachments(receivedFullContact.getDeletedAttachments());
        dataService.updateContact(fullContact);


        //remove session attributes
        request.getSession().removeAttribute("action");
        request.getSession().removeAttribute("id");
        ArrayList<ContactWithAddressDTO> contacts = ServiceFactory.getServiceFactory().getDataService().getContactsWithAddressDTO();
        request.setAttribute("contacts", contacts);
        return "/jsp/main.jsp";
    }

}

