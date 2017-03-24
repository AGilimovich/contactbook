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
        Map<String, String> storedFiles = new HashMap<>();
        for (Map.Entry<String, FileItem> part : fileParts.entrySet()) {
            storedFiles.put(part.getKey(), writeFilePart(part.getValue()));
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
                String fileName = UUID.randomUUID().toString()+ item.getName();
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
