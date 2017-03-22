package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;
import com.itechart.web.handler.*;
import com.itechart.web.properties.PropertiesManager;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class DoUpdateContact implements Command {
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;
    private String FILE_PATH = PropertiesManager.FILE_PATH();


    public DoUpdateContact(JdbcContactDao contactDao, JdbcAddressDao addressDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao) {
        this.contactDao = contactDao;
        this.addressDao = addressDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
    }


    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        RequestHandler handler = new RequestHandler();
        handler.handle(request);
        //get map of form field names and corresponding values
        Map<String, String> formFields = handler.getFormFields();
        //get map of field names and corresponding file parts
        Map<String, FileItem> fileParts = handler.getFileParts();

        FilePartWriter writer = new FilePartWriter(PropertiesManager.FILE_PATH());
        //store file parts and get map of field names and file names
        Map<String, String> storedFiles = writer.writeFileParts(fileParts);

        long contactId = (long) request.getSession().getAttribute("id");
        long addressId = contactDao.getContactById(contactId).getAddress();
        FormFieldsParser parser = new FormFieldsParser(formFields, storedFiles);
        Contact contact = parser.getContact();
        contact.setContactId(contactId);
        contact.setAddress(addressId);
        Address address = parser.getAddress();
        address.setId(addressId);

        ArrayList<Phone> newPhones = parser.getNewPhones();
        ArrayList<Phone> updatedPhones = parser.getUpdatedPhones();
        ArrayList<Phone> deletedPhones = parser.getDeletedPhones();

        ArrayList<Attachment> newAttachments = parser.getNewAttachments();
        ArrayList<Attachment> updatedAttachments = parser.getUpdatedAttachments();
        ArrayList<Attachment> deletedAttachments = parser.getDeletedAttachments();


        DBManager dbManager = new DBManager(contactDao, phoneDao, attachmentDao, addressDao);
        dbManager.updateContact(contact, address, newPhones, newAttachments, updatedPhones, updatedAttachments, deletedPhones, deletedAttachments);


        //remove session attributes
        request.getSession().removeAttribute("action");
        request.getSession().removeAttribute("id");
        return (new ShowContacts(contactDao, addressDao)).execute(servlet, request, response);
    }

}

