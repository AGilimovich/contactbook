package com.itechart.web.service.files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class FileService implements AbstractFileService {
    String FILE_PATH;

    public FileService(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    public void deleteFile(String name) {
        if (StringUtils.isBlank(name)) return;
        String filePath = StringUtils.join(new Object[]{FILE_PATH, name.charAt(0), name}, FileSystems.getDefault().getSeparator());
        File file = null;
        if (StringUtils.isNotEmpty(filePath))
            file = new File(filePath);
        if (file != null && file.exists()) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void deleteFiles(ArrayList<String> names) {
        if (names != null) {
            for (String name : names) {
                deleteFile(name);
            }
        }
    }

    public byte[] getFile(String name) {
        if (StringUtils.isNotBlank(name)) {
            Path path = Paths.get(StringUtils.join(new Object[]{FILE_PATH, name.charAt(0), name}, FileSystems.getDefault().getSeparator()));
            if (path != null) {
                try {
                    return Files.readAllBytes(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
