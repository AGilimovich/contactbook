package com.itechart.web.command;

import com.itechart.data.entity.Contact;
import com.itechart.web.command.errors.ErrorDispatcher;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.template.AbstractTemplateFactory;
import com.itechart.web.service.template.Template;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Command executed on request to show send emails view.
 */
public class ShowEmailView implements Command {
    private Logger logger = LoggerFactory.getLogger(ShowEmailView.class);

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");
        ArrayList<Long> selectedContacts = null;
        ArrayList<Contact> contacts = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        try {
            selectedContacts = ServiceFactory.getInstance().getRequestProcessingService().processShowEmailViewRequest(request);
            //create array list of email addresses of selected contacts
            AbstractDataService dataService = ServiceFactory.getInstance().getDataService();
            if (selectedContacts != null) {
                for (Long contactId : selectedContacts) {
                    try {
                        Contact contact = dataService.getContactById(contactId);
                        if (contact != null) {
                            contacts.add(contact);
                            if (StringUtils.isNotBlank(contact.getEmail()))
                                emailList.add(contact.getEmail());
                        }
                    } catch (DataException e) {
                        logger.error("Error during fetching contacts: {}", e.getMessage());
                        ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_NOT_FOUND);
                        return null;
                    }

                }
            }


        } catch (ValidationException e) {
            logger.error("Error during request processing: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        AbstractTemplateFactory templateService = ServiceFactory.getInstance().getEmailTemplateProvidingService();
        ArrayList<Template> templates = templateService.getPredefinedEmailTemplates();
        if (templates != null) {
            for (Template template : templates) {
                if (template != null)
                    template.getTemplate().add("name", "[Имя Отчество]");
            }
        }

        Template emailListTemplate = templateService.getEmailListTemplate(emailList);
        request.getSession().setAttribute("emailRecipients", contacts);
        request.setAttribute("templates", templates);
        request.setAttribute("contacts", contacts);
        request.setAttribute("emailListTemplate", emailListTemplate);


        return "/jsp/email.jsp";
    }
}
