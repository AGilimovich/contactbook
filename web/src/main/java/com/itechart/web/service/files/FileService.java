package com.itechart.web.service.files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of service class for executing operations with files in file system. .
 */
public class FileService implements AbstractFileService {
    private String FILE_PATH;
    private Logger logger = LoggerFactory.getLogger(FileService.class);

    public FileService(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    public void deleteFile(String name) {
        logger.info("Delete file with name: {}", name);
        if (StringUtils.isBlank(name)) return;
        String filePath = StringUtils.join(new Object[]{FILE_PATH, name.charAt(0), name}, FileSystems.getDefault().getSeparator());
        File file = null;
        if (StringUtils.isNotEmpty(filePath))
            file = new File(filePath);
        if (file != null && file.exists()) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                logger.error("Error during deleting file: {}", e.getMessage());
            }
        }
    }

    @Override
    public void deleteFiles(Collection<com.itechart.data.entity.File> files) {
        logger.info("Delete files with names: {}", files);
        if (files != null) {
            for (com.itechart.data.entity.File file : files) {
                if (file != null)
                    deleteFile(file.getStoredName());
            }
        }
    }

    public byte[] getFile(String name) {
        logger.info("Read file with name: {}", name);
        if (StringUtils.isNotBlank(name)) {
            Path path = Paths.get(StringUtils.join(new Object[]{FILE_PATH, name.charAt(0), name}, FileSystems.getDefault().getSeparator()));
            if (path != null) {
                try {
                    return Files.readAllBytes(path);
                } catch (IOException e) {
                    logger.error("Error during reading file: {}", e.getMessage());
                }
            }
        }
        return null;
    }

}
