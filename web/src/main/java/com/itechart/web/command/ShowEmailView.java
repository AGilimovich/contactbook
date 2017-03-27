package com.itechart.web.command;

import com.itechart.data.entity.Contact;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.data.TransactionalDataService;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.template.AbstractTemplateProvidingService;
import com.itechart.web.service.template.Template;
import com.itechart.web.service.template.TemplatesProvidingService;

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
        String[] selectedContacts = ServiceFactory.getServiceFactory().getRequestProcessingService().processShowEmailViewRequest(request);
        //create array list of email addresses of selected contacts
        ArrayList<Contact> contacts = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        if (selectedContacts != null) {
            for (String contactId : selectedContacts) {
                Contact contact = dataService.getContactById(Long.valueOf(contactId));
                if (contact != null) {
                    contacts.add(contact);
                    if (!contact.getEmail().isEmpty())
                        emailList.add(contact.getEmail());
                }
            }
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
