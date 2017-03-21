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

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {

        MultipartRequestParamHandler handler = new MultipartRequestParamHandler();
        long id = (long) request.getSession().getAttribute("id");
        Contact contact = contactDao.getContactById(id);
        Address address = addressDao.getAddressById(contact.getAddress());
        ArrayList<Phone> phones = new ArrayList<>();
        Map<Attachment, Action> attachments = new HashMap<>();
        handler.handle(request, contact, address, phones, attachments);

        addressDao.update(address);
        contactDao.update(contact);

        //delete old phones
        phoneDao.deleteForUser(contact.getId());
        //persist into db new phones
        for (Phone phone : phones) {
            phone.setContact(id);
            phoneDao.save(phone);
        }

        for (Map.Entry<Attachment, Action> attachment : attachments.entrySet()) {
            switch (attachment.getValue()) {
                case UPDATE:
                    attachmentDao.update(attachment.getKey());
                    break;
                case DELETE:
                    attachmentDao.delete(attachment.getKey().getId());
                    break;
                case ADD:
                    attachment.getKey().setContact(id);
                    attachmentDao.save(attachment.getKey());
                    break;
                case NONE:
                default:


            }
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

