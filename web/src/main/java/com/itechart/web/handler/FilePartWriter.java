package com.itechart.web.handler;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> storedFiles = new HashMap<>();
        for (Map.Entry<String, FileItem> part : fileParts.entrySet()) {
            storedFiles.put(part.getKey(), writeFileParts(part.getValue()));
        }
        return storedFiles;
    }

    /**
     * Storing file part on disk and gives it unique name.
     *
     * @param item to store.
     * @return the name of stored file.
     */
    private String writeFileParts(FileItem item) {
        try {
            if (item.getSize() != 0) {
                String fileName = String.valueOf(new Date().getTime());
                File uploadedFile = new File(path + "\\" + fileName);
                item.write(uploadedFile);
                return fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
