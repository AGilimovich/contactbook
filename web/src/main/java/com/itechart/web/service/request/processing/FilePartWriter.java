package com.itechart.web.service.request.processing;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.FileSystems;
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
        //todo null check
        if (fileParts == null) return null;
        Map<String, String> storedFiles = new HashMap<>();
        for (Map.Entry<String, FileItem> part : fileParts.entrySet()) {
            //if there is file, then save it
            if (StringUtils.isNotEmpty(part.getValue().getName())) {
                // TODO: 29.03.2017 check null
                //stored name = name on disk
                storedFiles.put(part.getKey() + "_stored", writeFilePart(part.getValue()));
                //real file name
                storedFiles.put(part.getKey() + "_real", part.getValue().getName());
            }
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

            String storedFileName = UUID.randomUUID().toString();
            //create folder with name first character of generated UUID
            File folder = new File(path + FileSystems.getDefault().getSeparator() + storedFileName.charAt(0));
            if (!folder.exists()) {
                try {
                    folder.mkdir();
                } catch (SecurityException e) {
                    // TODO: 29.03.2017 cant create folder
                    e.printStackTrace();
                }
            }
            // TODO: 29.03.2017 separators
            File uploadedFile = new File(folder + FileSystems.getDefault().getSeparator() + storedFileName);
            item.write(uploadedFile);
            return storedFileName;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
