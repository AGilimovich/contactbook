package com.itechart.web.service.request.processing;

import com.itechart.data.entity.File;

import java.util.Map;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class PhotoFileBuilder {
    public File buildFile(Map<String, String> parameters) {
        String realName = parameters.get("photoFile_real");
        String storedName = parameters.get("photoFile_stored");

        if (realName == null)
            realName = storedName;
        File file = new File();
        file.setName(realName);
        file.setStoredName(storedName);
        return file;
    }
}
