package com.itechart.web.service.files;

import com.itechart.web.properties.PropertiesManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class FileService implements AbstractFileService {
    String FILE_PATH;

    public FileService(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    public void deleteFile(String name) {
        String path = FILE_PATH + "\\" + name;
        new File(path).delete();
    }

    public byte[] getFile(String name) {
        if (!name.isEmpty()) {
            Path path = Paths.get(FILE_PATH + "\\" + name);
            try {
                return Files.readAllBytes(path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
