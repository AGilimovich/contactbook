package com.itechart.web.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for handling multipart requests.
 */
public class MultipartRequestHandler {

    private Map<String, String> formFields;
    private Map<String, FileItem> fileParts;

    /**
     * Creates maps of form fields names and their values, file items and their field names.
     * @param request from client.
     */
    public void handle(HttpServletRequest request) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        formFields = new HashMap<>();
        fileParts = new HashMap<>();
        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    try {
                        formFields.put(item.getFieldName(), item.getString("UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    fileParts.put(item.getFieldName(), item);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }


    }


    public Map<String, String> getFormFields() {
        return formFields;
    }

    public Map<String, FileItem> getFileParts() {
        return fileParts;
    }

}
