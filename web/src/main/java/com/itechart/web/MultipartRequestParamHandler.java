package com.itechart.web;

import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;
import com.itechart.web.parser.AddressParser;
import com.itechart.web.parser.AttachmentParser;
import com.itechart.web.parser.ContactParser;
import com.itechart.web.parser.PhoneParser;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Aleksandr on 17.03.2017.
 */

public class MultipartRequestParamHandler {
    private DiskFileItemFactory factory;
    private ServletFileUpload upload;
    private String FILE_PATH;

    public MultipartRequestParamHandler() {
        this.factory = new DiskFileItemFactory();
        this.upload = new ServletFileUpload(factory);
        ResourceBundle properties = ResourceBundle.getBundle("application");
        FILE_PATH = properties.getString("FILE_PATH");
    }

    public void handle(HttpServletRequest request, Contact contact, Address address, ArrayList<Phone> phones, ArrayList<Attachment> attachments) {
        //structures for storing request parameters
        Map<String, String> formParameters = new HashMap<>();
        ArrayList<String> formPhoneParameters = new ArrayList<>();
        ArrayList<String> formAttachmentsParameters = new ArrayList<>();
        String savedPhotoName = null;
        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    //putting all single request parameters into Map<name, values>, collective request params (phone, attachment) into ArrayLists
                    if (item.getFieldName().equals("phone")) {
                        try {
                            formPhoneParameters.add(item.getString("UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else if (item.getString() == "attachment") {
                        try {
                            formAttachmentsParameters.add(item.getString("UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else
                        try {
                            formParameters.put(item.getFieldName(), item.getString("UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                } else {
                    //processing input with type = file: saving it into file on disk
                    if (item.getSize() != 0) {
                        savedPhotoName = processFileInputs(item, FILE_PATH);
                    }

                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        AddressParser addressParser = new AddressParser();
        ContactParser contactParser = new ContactParser();
        PhoneParser phoneParser = new PhoneParser();
        AttachmentParser attachmentParser = new AttachmentParser();
        //set to address parameters from request except for id
        address.set(addressParser.parseAddress(formParameters));
        //set to contact parameters from request except for id's
        contact.set(contactParser.parseContact(formParameters));


        for (Phone phone : phoneParser.parsePhones(formPhoneParameters)) {
            Phone p = new Phone();
            p.set(phone);
            phones.add(p);
        }
        for (Attachment attachment : attachmentParser.parseAttachments(formAttachmentsParameters)) {
            Attachment a = new Attachment();
            a.set(attachment);
            attachments.add(a);
        }
        attachments = attachmentParser.parseAttachments(formAttachmentsParameters);
        //set name of saved photo to contact's field
        if (savedPhotoName != null)
            contact.setPhoto(savedPhotoName);
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
