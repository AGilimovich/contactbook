package com.itechart.web.service.request.processing;

import com.itechart.data.entity.File;

import java.util.Map;

/**
 * Created by Aleksandr on 25.03.2017.
 */

public class AttachFileBuilder {
    public File buildFile(Map<String, String> parameters, String id) {

        String realName = parameters.get("attachFile[" + id + "]_real");
        String storedName = parameters.get("attachFile[" + id + "]_stored");

        if (realName == null)
            realName = storedName;
        File file = new File();
        file.setName(realName);
        file.setStoredName(storedName);
        return file;
    }
}

