package com.itechart.web.command.view.formatter;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.data.exception.DataException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Forms list of contacts to display on the main page.
 */
public class DisplayingContactsListFormatter {

    public void formContactsList(HttpServletRequest request) throws DataException {
        //get displaying on page contacts count
        int contactsOnPage = 10;
        if (request.getSession().getAttribute("contactsOnPage") != null) {
            contactsOnPage = (int) request.getSession().getAttribute("contactsOnPage");
        }
        //get requested page number
        int pageNumber = 0;
        if (StringUtils.isNotBlank(request.getParameter("pageNumber"))) {
            pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
        }
        //get list of contacts to display and total count of contacts
        int contactsFoundCount = 0;
        ArrayList<MainPageContactDTO> contacts = null;
        AbstractDataService dataService = ServiceFactory.getInstance().getDataService();
        if (request.getSession().getAttribute("searchDTO") != null) {
            SearchDTO searchDTO = (SearchDTO) request.getSession().getAttribute("searchDTO");
            contactsFoundCount = dataService.getContactsSearchResultCount(searchDTO);
            contacts = dataService.getSearchResultContactsDTOForPage(searchDTO, pageNumber, contactsOnPage);
        } else {
            contactsFoundCount = dataService.getContactsCount();
            contacts = dataService.getMainPageContactDTO(pageNumber, contactsOnPage);

        }
        //calculate count of pages
        int pagesCount = 0;
        if (contactsOnPage != 0) {
            pagesCount = (int) Math.ceil((double) contactsFoundCount / contactsOnPage);
        }

        request.getSession().setAttribute("pageNumber", pageNumber);
        request.setAttribute("contacts", contacts);
        request.setAttribute("pagesCount", pagesCount);

    }
}
