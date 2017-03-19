package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;
import com.itechart.web.MultipartRequestParamHandler;
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
    private String FILE_PATH;


    public DoUpdateContact(JdbcContactDao contactDao, JdbcAddressDao addressDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao) {
        this.contactDao = contactDao;
        this.addressDao = addressDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
        ResourceBundle properties = ResourceBundle.getBundle("application");
        String FILE_PATH = properties.getString("FILE_PATH");
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {

        MultipartRequestParamHandler handler = new MultipartRequestParamHandler();
        long id = (long) request.getSession().getAttribute("id");
        Contact contact = contactDao.getContactById(id);
        Address address = addressDao.getAddressById(contact.getAddress());
        ArrayList<Phone> phones = new ArrayList<>();
        ArrayList<Attachment> attachments = new ArrayList<>();
        handler.handle(request, contact, address, phones, attachments);

        addressDao.update(address);
        contactDao.update(contact);

        //delete old phones and attachments
        phoneDao.deleteForUser(contact.getId());
        attachmentDao.deleteForUser(contact.getId());
        //persist into db new phones and attachments
        for (Phone phone : phones) {
            phone.setContact(id);
            phoneDao.save(phone);
        }
        for (Attachment attachment : attachments) {
            attachment.setContact(id);
            attachmentDao.save(attachment);
        }

        //remove session attributes
        request.getSession().removeAttribute("action");
        request.getSession().removeAttribute("id");
        return (new ShowContacts(contactDao, addressDao)).execute(servlet, request, response);
    }


    /**
     * Function processes request parameters with type file.
     * Stores file on file system and returns unique name given to file.
     * Name given to file is the number of milliseconds since January 1, 1970, 00:00:00 GMT.
     *
     * @param item
     * @param path
     * @return
     */
    private String processFileInputs(FileItem item, String path) {
        String fileName = String.valueOf(new Date().getTime());
        File uploadedFile = new File(path + "\\" + fileName);
        try {
            item.write(uploadedFile);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

