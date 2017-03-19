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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aleksandr on 17.03.2017.
 */

public class MultipartRequestParamHandler {
    private DiskFileItemFactory factory;
    private ServletFileUpload upload;
    private String FILE_PATH;
    private final String attachFileRegex = "attachFile\\[(\\d+)\\]";
    private final String attachMetaRegex = "attachMeta\\[(\\d+)\\]";
    private Pattern attachMetaPattern = Pattern.compile(attachMetaRegex);
    private Pattern attachFilePattern = Pattern.compile(attachFileRegex);


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
        Map<Long, String> formAttachmentsParameters = new HashMap<>();
        Map<Long, String> attachmentFiles = new HashMap<>();

        String savedPhotoName = null;
        try {
            List<FileItem> items = upload.parseRequest(request);
            Matcher matcher = null;
            for (FileItem item : items) {
                if (item.isFormField()) {
                    //putting all single request parameters into Map<name, values>, collective request params (phone, attachment) into ArrayLists
                    if (item.getFieldName().equals("phone")) {
                        try {
                            formPhoneParameters.add(item.getString("UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else if ((matcher = attachMetaPattern.matcher(item.getFieldName())).matches()) {
                        try {
                            Long id = Long.valueOf(matcher.group(1));
                            formAttachmentsParameters.put(id, item.getString("UTF-8"));
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
                        matcher = attachFilePattern.matcher(item.getFieldName());
                        if (matcher.matches()) {
                            Long id = Long.valueOf(matcher.group(1));
                            attachmentFiles.put(id, processFileField(item, FILE_PATH));
                        } else if (item.getFieldName().equals("photoFile"))
                            savedPhotoName = processFileField(item, FILE_PATH);

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
        //set address object parameters with values from request except for id
        address.set(addressParser.parseAddress(formParameters));
        //set contact object parameters with values from request except for id's
        contact.set(contactParser.parseContact(formParameters));


        for (Phone phone : phoneParser.parsePhones(formPhoneParameters)) {
            Phone p = new Phone();
            p.set(phone);
            phones.add(p);
        }
        for (Attachment attachment : attachmentParser.parseAttachments(formAttachmentsParameters, attachmentFiles)) {
            Attachment a = new Attachment();
            a.set(attachment);
            attachments.add(a);
        }
        //set name of saved photo to contact's field
        if (savedPhotoName != null) {
            contact.setPhoto(savedPhotoName);
            //todo delete old photo from disk
        }
    }


    /**
     * Function processes request parameters with type file.
     * Stores file on file system.
     * Name given to file is the number of milliseconds since January 1, 1970, 00:00:00 GMT.
     *
     * @param item
     * @param path
     * @return unique name given to file.
     */
    private String processFileField(FileItem item, String path) {

        Map<Long, String> map = new HashMap<>();
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
