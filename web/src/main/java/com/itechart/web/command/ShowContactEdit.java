package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Command for creating new contact.
 */
public class ShowContactEdit implements Command {
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;

    public ShowContactEdit(JdbcContactDao contactDao, JdbcAddressDao addressDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao) {
        this.contactDao = contactDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
        this.addressDao = addressDao;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {

        long id = Long.valueOf(request.getParameter("id"));
        Contact contact = contactDao.getContactById(id);
        Address address = addressDao.getAddressById(contact.getAddress());
        ArrayList<Phone> phones = phoneDao.getAllForContact(id);
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(id);
//        ArrayList<Attachment> attachments = null;
        request.getSession().setAttribute("action", "update");
        request.getSession().setAttribute("id", id);
        request.setAttribute("contact", contact);
        request.setAttribute("address", address);
        request.setAttribute("phones", phones);
        request.setAttribute("attachments", attachments);
//-------------------------------------------------------------------

        return "/jsp/contact.jsp";

    }
}
