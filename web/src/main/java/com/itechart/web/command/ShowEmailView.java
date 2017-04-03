package com.itechart.web.command;

import com.itechart.data.entity.Contact;
import com.itechart.web.command.dispatcher.ErrorDispatcher;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.data.TransactionalDataService;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.template.AbstractTemplateProvidingService;
import com.itechart.web.service.template.Template;
import com.itechart.web.service.template.TemplatesProvidingService;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

/**
 * Command for invoking emailing view.
 */
public class ShowEmailView implements Command {

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ArrayList<Long> selectedContacts = null;
        ArrayList<Contact> contacts = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        try {
            selectedContacts = ServiceFactory.getServiceFactory().getRequestProcessingService().processShowEmailViewRequest(request);
            //create array list of email addresses of selected contacts
            AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();
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
                        ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        return null;
                    }

                }
            }


        } catch (ValidationException e) {
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        AbstractTemplateProvidingService templateService = ServiceFactory.getServiceFactory().getEmailTemplateProvidingService();
        Map<Class<? extends Template>, Template> templates = templateService.getPredefinedEmailTemplates();
        Template emailListTemplate = templateService.getEmailListTemplate(emailList);

        request.setAttribute("templates", templates.entrySet());
        request.setAttribute("contacts", contacts);
        request.setAttribute("emailListTemplate", emailListTemplate);


        return "/jsp/email.jsp";
    }
}
