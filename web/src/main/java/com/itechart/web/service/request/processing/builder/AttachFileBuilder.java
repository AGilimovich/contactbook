package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.File;

import java.util.Map;

/**
 * Created by Aleksandr on 25.03.2017.
 */

public class AttachFileBuilder {
    public File buildFile(Map<String, String> parameters, String fieldId) {

        String realName = parameters.get("attachFile[" + fieldId + "]_real");
        String storedName = parameters.get("attachFile[" + fieldId + "]_stored");

        // TODO: 29.03.2017
        if (realName == null)
            realName = storedName;
        File file = new File();
        file.setName(realName);
        file.setStoredName(storedName);
        return file;
    }
}

