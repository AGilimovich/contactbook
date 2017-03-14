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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class ShowContactAddCommand implements Command{
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;

    public ShowContactAddCommand(JdbcContactDao contactDao, JdbcAddressDao addressDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao) {
        this.contactDao = contactDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
        this.addressDao = addressDao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
//        Contact contact = new Contact();
//        Address address = new Address();
//        ArrayList<Phone> phones = new ArrayList<>();
//        ArrayList<Attachment> attachments = new ArrayList<>();
//        request.setAttribute("contact", contact);
//        request.setAttribute("address", address);
//        request.setAttribute("phones", phones);
//        request.setAttribute("attachments", attachments);
        request.getSession().setAttribute("action", "save");


        return "/jsp/contact.jsp";

    }
}
