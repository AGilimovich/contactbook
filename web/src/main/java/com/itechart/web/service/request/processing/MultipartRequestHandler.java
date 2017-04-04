package com.itechart.web.service.request.processing;

import com.itechart.web.properties.PropertiesManager;
import com.itechart.web.service.request.processing.exception.FileSizeException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Logger logger = LoggerFactory.getLogger(MultipartRequestHandler.class);

    /**
     * Creates maps of form fields names and their values, file items and their field names.
     *
     * @param request from client.
     */
    public void handle(HttpServletRequest request) throws FileSizeException {
        logger.info("Handle multipart request");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(PropertiesManager.MAX_FILE_SIZE());
        formFields = new HashMap<>();
        fileParts = new HashMap<>();
        try {
            List<FileItem> items = upload.parseRequest(request);
            if (items != null) {
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
            }
        } catch (FileUploadBase.SizeLimitExceededException e) {
            throw new FileSizeException("The file size exceeds maximum permitted value");
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
