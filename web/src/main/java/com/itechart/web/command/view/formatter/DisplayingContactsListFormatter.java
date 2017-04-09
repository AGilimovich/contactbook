package com.itechart.web.command.view.formatter;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.data.exception.DataException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Forms list of contacts to display on the main page.
 */
public class DisplayingContactsListFormatter {
    private static Logger logger = LoggerFactory.getLogger(DisplayingContactsListFormatter.class);

    public void formContactsList(HttpServletRequest request, SearchDTO searchDTO) throws DataException {
        logger.info("Forming contact list for displaying on main page");
        AbstractDataService dataService = ServiceFactory.getInstance().getDataService();
        //get displaying on page contacts count
        int contactsOnPage = 10;
        if (request.getSession().getAttribute("contactsOnPage") != null) {
            try {
                contactsOnPage = (int) request.getSession().getAttribute("contactsOnPage");
            } catch (Exception e) {
                logger.error("Error getting attribute from session: {}", e);
            }
        }

        //get page number from request
        int pageNumber = 0;
        if (StringUtils.isNotBlank(request.getParameter("pageNumber"))) {
            try {
                pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
            } catch (Exception e) {
                logger.error("Error getting parameter value from request: {}", e);
            }
        }
        int contactsFoundCount = 0;
        ArrayList<MainPageContactDTO> contacts = new ArrayList<>();
        if (searchDTO != null) {
            contactsFoundCount = dataService.getContactsSearchResultCount(searchDTO);
            contacts = dataService.getSearchResultContactsDTOForPage(searchDTO, pageNumber, contactsOnPage);
        } else {
            contactsFoundCount = dataService.getContactsCount();
            contacts = dataService.getMainPageContactDTO(pageNumber, contactsOnPage);
        }

        //calculate number of pages
        int pagesCount = 0;
        if (contactsOnPage != 0) {
            pagesCount = (int) Math.ceil((double) contactsFoundCount / contactsOnPage);
        }
        if (pageNumber != 0 && pageNumber >= pagesCount) {
            throw new DataException("Requested page was not found");
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("contacts", contacts);
        request.setAttribute("pagesCount", pagesCount);

    }
}
