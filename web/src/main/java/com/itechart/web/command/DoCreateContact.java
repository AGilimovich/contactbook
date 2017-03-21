package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;
import com.itechart.web.handler.Action;
import com.itechart.web.handler.MultipartRequestParamHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class DoCreateContact implements Command {
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;

    public DoCreateContact(JdbcContactDao contactDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao, JdbcAddressDao addressDao) {
        this.contactDao = contactDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
        this.addressDao = addressDao;

    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        MultipartRequestParamHandler handler = new MultipartRequestParamHandler();
        Contact contact = new Contact();
        Address address = new Address();
        ArrayList<Phone> phones = new ArrayList<>();
        Map<Attachment, Action> attachments = new HashMap<>();
        //fills objects with data retrieved from request
        handler.handle(request, contact, address, phones, attachments);


        //assigning id's and persisting into db
        long addressId = addressDao.save(address);
        contact.setAddress(addressId);
        long contactId = contactDao.save(contact);
        for (Phone phone : phones) {
            phone.setContact(contactId);
            phoneDao.save(phone);
        }
        for (Map.Entry<Attachment, Action> attachment : attachments.entrySet()) {
            switch (attachment.getValue()) {
                case ADD:
                case UPDATE:
                    attachment.getKey().setContact(contactId);
                    attachmentDao.save(attachment.getKey());
                    break;
                default:


            }
        }

//------------------------------------------------------------------------------
        request.getSession().removeAttribute("action");
        return (new ShowContacts(contactDao, addressDao)).execute(servlet, request, response);
    }


}
