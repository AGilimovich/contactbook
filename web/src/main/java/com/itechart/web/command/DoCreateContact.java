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

        FormFieldsParser parser = new FormFieldsParser(formFields, storedFiles);
        Contact contact = parser.getContact();
        Address address = parser.getAddress();
        ArrayList<Phone> phones = parser.getNewPhones();
        ArrayList<Attachment> attachments = parser.getNewAttachments();
        DBManager dbManager = new DBManager(contactDao, phoneDao, attachmentDao, addressDao);
        dbManager.saveNewContact(contact, address, phones, attachments);


//------------------------------------------------------------------------------
        request.getSession().removeAttribute("action");
        return (new ShowContacts(contactDao, addressDao)).execute(servlet, request, response);
    }


}
