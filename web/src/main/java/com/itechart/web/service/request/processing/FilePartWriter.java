package com.itechart.web.service.request.processing;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Class for storing on disk file parts.
 */
public class FilePartWriter {
    private final String path;

    public FilePartWriter(String path) {
        this.path = path;
    }

    /**
     * Stores all received file parts on disk.
     *
     * @param fileParts
     * @return map of field names and names of files.
     */
    public Map<String, String> writeFileParts(Map<String, FileItem> fileParts) {
        if (fileParts == null) return null;
        Map<String, String> storedFiles = new HashMap<>();
        for (Map.Entry<String, FileItem> part : fileParts.entrySet()) {
            //stored name = name on disk
            storedFiles.put(part.getKey()+"_stored", writeFilePart(part.getValue()));
            //real file name
            storedFiles.put(part.getKey() + "_real", part.getValue().getName());
        }
        return storedFiles;
    }

    /**
     * Storing file part on disk and gives it unique name.
     *
     * @param item to store.
     * @return the name of stored file.
     */
    private String writeFilePart(FileItem item) {
        try {
            if (item.getSize() != 0) {
                String storedFileName = UUID.randomUUID().toString();
                File uploadedFile = new File(path + "\\" + storedFileName);
                item.write(uploadedFile);
                return storedFileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
