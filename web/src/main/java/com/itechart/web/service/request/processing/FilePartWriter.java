package com.itechart.web.service.request.processing;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Class for witing parts of multipart requests to disk.
 */
public class FilePartWriter {
    private final String basePath;
    private Logger logger = LoggerFactory.getLogger(FilePartWriter.class);


    public FilePartWriter(String basePath) {
        this.basePath = basePath;
    }

       public String writeFilePart(FileItem item) {
        try {
            String storedFileName = UUID.randomUUID().toString();
            //create folder with name first character of generated UUID
            if (StringUtils.isNotEmpty(storedFileName)) {
                String filePath = StringUtils.join(new Object[]{basePath, storedFileName.charAt(0)}, FileSystems.getDefault().getSeparator());
                File folder = new File(filePath);
                if (!folder.exists()) {
                    try {
                        logger.info("Creating folder: {}", filePath);
                        folder.mkdirs();
                    } catch (SecurityException e) {
                        logger.error("Exception during folder creating: {}", e.getMessage());
                    }
                }
                String fullFilePath = StringUtils.join(new Object[]{folder, storedFileName}, FileSystems.getDefault().getSeparator());
                File uploadedFile = new File(fullFilePath);
                logger.info("Writing file: {}", fullFilePath);
                item.write(uploadedFile);
                return storedFileName;
            }
        } catch (Exception e) {
            logger.error("Exception during file writing: {}", e.getMessage());
        }
        return null;
    }
}
