package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.web.service.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for deleting selected contacts.
 */
public class DoDeleteContact implements Command {
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;

    public DoDeleteContact(JdbcContactDao contactDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao, JdbcAddressDao addressDao) {
        this.contactDao = contactDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
        this.addressDao = addressDao;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String[] selectedContactsId = request.getParameterValues("isSelected");
        SessionManager manager = new SessionManager(contactDao, phoneDao, attachmentDao, addressDao);
        if (selectedContactsId != null) {
            for (String c : selectedContactsId) {
                long contactId = Long.valueOf(c);
                manager.deleteContact(contactId);
            }
        }
        return (new ShowContacts(contactDao, addressDao)).execute(servlet, request, response);
    }
}
