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
     * Extracts from request form fields and file parts and puts them into maps.
     * @param request received from client.
     * @throws FileSizeException when received file size exceeds maximum permitted value.
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
                            logger.error("Error getting form field value: {}", e.getMessage());
                        }
                    } else {
                        fileParts.put(item.getFieldName(), item);
                    }
                }
            }
        } catch (FileUploadBase.SizeLimitExceededException e) {
            throw new FileSizeException("The file size exceeds maximum permitted value");
        } catch (FileUploadException e) {
            logger.error("Error during file uploading: {}", e.getMessage());
        }
    }


    public Map<String, String> getFormFields() {
        return formFields;
    }

    public Map<String, FileItem> getFileParts() {
        return fileParts;
    }

}
