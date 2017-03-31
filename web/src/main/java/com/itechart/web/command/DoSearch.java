package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.File;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.ServiceFactory;
import org.apache.commons.lang3.StringUtils;

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
        SearchDTO dto = null;
        if (request.getSession().getAttribute("searchDTO") == null) {
            dto = ServiceFactory.getServiceFactory().getRequestProcessingService().processSearchContactsRequest(request);
        } else
            dto = (SearchDTO) request.getSession().getAttribute("searchDTO");
        AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();


        //default values
        int pageNumber = 0;
        int contactsOnPage = 10;

        String contactsOnPageParam = request.getParameter("contactsOnPage");
        String pageNumberParam = request.getParameter("pageNumber");
        if (StringUtils.isEmpty(contactsOnPageParam)) {
            if (request.getSession().getAttribute("contactsOnPage") != null)
                contactsOnPage = (int) request.getSession().getAttribute("contactsOnPage");
        } else {
            //if displaying contacts count was changed
            contactsOnPage = Integer.valueOf(contactsOnPageParam);
            request.getSession().setAttribute("contactsOnPage", contactsOnPage);
        }
        if (StringUtils.isNotEmpty(pageNumberParam)) {
            pageNumber = Integer.valueOf(pageNumberParam);
        }

        ArrayList<Contact> contacts = dataService.getContactsByFieldsForPage(dto, pageNumber, contactsOnPage);
        ArrayList<MainPageContactDTO> mainPageContactDTOs = new ArrayList<>();
        int contactsCount = dataService.getContactsSearchResultCount(dto);
        for (Contact contact : contacts) {
            Address address = dataService.getAddressByContactId(contact.getContactId());
            File photo = dataService.getPhotoById(contact.getPhoto());
            MainPageContactDTO mainPageContactDTO = new MainPageContactDTO(contact, address, photo);
            mainPageContactDTOs.add(mainPageContactDTO);
        }

        int pagesCount = (int) Math.ceil(contactsCount / contactsOnPage);
        request.getSession().setAttribute("searchDTO", dto);
        request.getSession().setAttribute("isSearch", true);
        request.setAttribute("contacts", mainPageContactDTOs);
        request.setAttribute("pagesCount", pagesCount);
        request.getSession().setAttribute("pageNumber", pageNumber);

        return "/jsp/main.jsp";
    }
}
