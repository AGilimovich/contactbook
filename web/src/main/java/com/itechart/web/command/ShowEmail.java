package com.itechart.web.command;

import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.entity.Contact;
import com.itechart.web.service.DataService;
import com.itechart.web.service.ServiceFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Command for invoking emailing view.
 */
public class ShowEmail implements Command {

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String[] selectedContacts = request.getParameterValues("isSelected");
        //create array list of email addresses of selected contacts
        ArrayList<Contact> contacts = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
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
        ArrayList<ST> templates = new ArrayList<>();


        final STGroup stGroup = new STGroupFile("templates/template.stg");
        final ST commonEmail = stGroup.getInstanceOf("commonEmail");

        final ST birthdayEmail = stGroup.getInstanceOf("birthdayEmail");


        templates.add(commonEmail);
        templates.add(birthdayEmail);

        ST emailListTemplate = new ST("<emails:{email | <email>}; separator=\", \">");

        emailListTemplate.add("emails", emailList);

        request.setAttribute("templates", templates);
        request.setAttribute("contacts", contacts);
        request.setAttribute("emailListTemplate", emailListTemplate);


        return "/jsp/email.jsp";
    }
}
