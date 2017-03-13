package com.itechart.web.command;

import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Command for editing existing contact.
 */
public class EditContactCommand implements Command {


    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;

    public EditContactCommand(JdbcContactDao contactDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao) {
        this.contactDao = contactDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int id = Integer.valueOf(request.getParameter("id"));
        Contact c = contactDao.getContactById(id);
        ArrayList<Phone> phones = phoneDao.getAllForContact(id);
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(id);
        request.setAttribute("contact", c);
        request.setAttribute("phones", phones);
        request.setAttribute("attachments", attachments);
        return "/jsp/edit.jsp";

    }
}
