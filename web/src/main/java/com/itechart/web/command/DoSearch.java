package com.itechart.web.command;

import com.itechart.data.dto.ContactWithAddressDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.ContactFile;
import com.itechart.web.service.DataService;
import com.itechart.web.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class DoSearch implements Command {


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        SearchDTO dto = ServiceFactory.getServiceFactory().getRequestProcessingService().processSearchContactsRequest(request);
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        ArrayList<Contact> contacts = dataService.getContactsByFields(dto);
        ArrayList<ContactWithAddressDTO> contactWithAddressDTOs = new ArrayList<>();
        for (Contact contact : contacts) {
            Address address = dataService.getAddressById(contact.getAddress());
            ContactFile photo = dataService.getPhotoById(contact.getPhoto());
            ContactWithAddressDTO contactWithAddressDTO = new ContactWithAddressDTO(contact, address, photo);
            contactWithAddressDTOs.add(contactWithAddressDTO);
        }
        request.setAttribute("contacts", contactWithAddressDTOs);

        return "/jsp/main.jsp";
    }
}
