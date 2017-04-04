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
 * Stores on disk file parts.
 */
public class FilePartWriter {
    private final String basePath;
    private Logger logger = LoggerFactory.getLogger(FilePartWriter.class);


    public FilePartWriter(String basePath) {
        this.basePath = basePath;
    }

    /**
     * Stores all received file parts on disk.
     *
     * @param fileParts
     * @return map of field names and names of files.
     */
    public Map<String, String> writeFileParts(Map<String, FileItem> fileParts) {
        logger.info("Writing file parts");
        if (fileParts == null) return null;
        Map<String, String> storedFiles = new HashMap<>();
        for (Map.Entry<String, FileItem> part : fileParts.entrySet()) {
            //if there is file, then save it
            if (part.getValue() != null) {
                if (StringUtils.isNotEmpty(part.getValue().getName())) {
                    //stored name = name on disk
                    if (part.getKey() != null) {
                        storedFiles.put(StringUtils.join(new Object[]{part.getKey(), "_stored"}), writeFilePart(part.getValue()));
                        //real file name
                        storedFiles.put(StringUtils.join(new Object[]{part.getKey(), "_real"}), part.getValue().getName());
                    }
                }
            }
        }
        return storedFiles;
    }

    /**
     * Stores file part on disk and gives it unique name.
     *
     * @param item to store.
     * @return the name of the stored file.
     */
    private String writeFilePart(FileItem item) {
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
                        logger.error("Exception during  new folder creating: {}", e.getMessage());
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
