package com.itechart.web.service.files;

import org.apache.commons.io.FileUtils;

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
        // TODO: 29.03.2017 check null
        File file = new File(FILE_PATH + FileSystems.getDefault().getSeparator() + name.charAt(0) + FileSystems.getDefault().getSeparator() + name);
        if (file.exists()) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void deleteFiles(ArrayList<String> names) {
        for (String name : names) {
            deleteFile(name);
        }
    }

    public byte[] getFile(String name) {
        if (!name.isEmpty()) {
            Path path = Paths.get(FILE_PATH + FileSystems.getDefault().getSeparator() + name.charAt(0) + FileSystems.getDefault().getSeparator() + name);
            try {
                return Files.readAllBytes(path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
