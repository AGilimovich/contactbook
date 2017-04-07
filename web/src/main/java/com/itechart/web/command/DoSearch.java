package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.File;
import com.itechart.web.command.dispatcher.ErrorDispatcher;
import com.itechart.web.command.view.formatter.DisplayingContactsListFormatter;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class DoSearch implements Command {
    private static Logger logger = LoggerFactory.getLogger(DoSearch.class);

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");
        SearchDTO dto = null;
        try {
            dto = ServiceFactory.getInstance().getRequestProcessingService().processSearchContactsRequest(request);
        } catch (ValidationException e) {
            logger.error("Error during request processing: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        request.getSession().setAttribute("searchDTO", dto);
        try {
            new DisplayingContactsListFormatter().formContactsList(request);
        } catch (DataException e) {
            logger.error("Error during fetching contacts: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

//        SearchDTO dto = null;
//        if (request.getSession().getAttribute("searchDTO") == null) {
//            try {
//                dto = ServiceFactory.getInstance().getRequestProcessingService().processSearchContactsRequest(request);
//            } catch (ValidationException e) {
//                logger.error("Error during request processing: {}", e.getMessage());
//                ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_BAD_REQUEST);
//                return null;
//            }
//        } else
//            dto = (SearchDTO) request.getSession().getAttribute("searchDTO");
//        AbstractDataService dataService = ServiceFactory.getInstance().getDataService();
//
//        //default values
//        int pageNumber = 0;
//        int contactsOnPage = 10;
//
//        String contactsOnPageParam = request.getParameter("contactsOnPage");
//        String pageNumberParam = request.getParameter("pageNumber");
//        if (StringUtils.isEmpty(contactsOnPageParam)) {
//            if (request.getSession().getAttribute("contactsOnPage") != null)
//                contactsOnPage = (int) request.getSession().getAttribute("contactsOnPage");
//        } else {
//            //if displaying contacts count was changed
//            contactsOnPage = Integer.valueOf(contactsOnPageParam);
//            request.getSession().setAttribute("contactsOnPage", contactsOnPage);
//        }
//        if (StringUtils.isNotEmpty(pageNumberParam)) {
//            pageNumber = Integer.valueOf(pageNumberParam);
//        }
//
//
//        int contactsCount = 0;
//        ArrayList<MainPageContactDTO> mainPageContactDTOs = new ArrayList<>();
//        try {
//            ArrayList<Contact> contacts = dataService.getSearchResultContactsDTOForPage(dto, pageNumber, contactsOnPage);
//            contactsCount = dataService.getContactsSearchResultCount(dto);
//            for (Contact contact : contacts) {
//                Address address = dataService.getAddressByContactId(contact.getContactId());
//                File photo = dataService.getPhotoById(contact.getPhoto());
//                MainPageContactDTO mainPageContactDTO = new MainPageContactDTO(contact, address, photo);
//                mainPageContactDTOs.add(mainPageContactDTO);
//            }
//        } catch (DataException e) {
//            logger.error("Error during fetching contacts: {}", e.getMessage());
//            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            return null;
//        }
//
//
//        int pagesCount = 1;
//        if (contactsOnPage != 0) {
//            pagesCount = (int) Math.ceil((double) contactsCount / contactsOnPage);
//        }
//        request.getSession().setAttribute("searchDTO", dto);
//        request.getSession().setAttribute("isSearch", true);
//        request.setAttribute("contacts", mainPageContactDTOs);
//        request.setAttribute("pagesCount", pagesCount);
//        request.getSession().setAttribute("pageNumber", pageNumber);
        return "/jsp/main.jsp";
    }
}
