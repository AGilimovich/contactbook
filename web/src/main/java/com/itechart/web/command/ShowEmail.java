package com.itechart.web.command;

import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.entity.Contact;
import org.stringtemplate.v4.ST;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Command for invoking emailing view.
 */
public class ShowEmail implements Command {
    private JdbcContactDao contactDao;

    public ShowEmail(JdbcContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String[] selectedContacts = request.getParameterValues("isSelected");
        //create array list of email addresses of selected contacts
        ArrayList<String> emailAddresses = new ArrayList<>();
        if (selectedContacts != null) {
            for (String contactId : selectedContacts) {
                Contact contact = contactDao.getContactById(Long.valueOf(contactId));
                if (contact != null)
                    emailAddresses.add(contact.getEmail());
            }
        }



        ST bodyTemplate = new ST("Здравствуйте!\n<text>");




        return "/jsp/email.jsp";
    }
}
