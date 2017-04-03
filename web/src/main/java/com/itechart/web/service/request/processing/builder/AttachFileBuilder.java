package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.File;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by Aleksandr on 25.03.2017.
 */

public class AttachFileBuilder {
    public File buildFile(Map<String, String> parameters, String fieldId) {
        if (fieldId == null) return null;
        String realNameParam = parameters.get("attachFile[" + fieldId + "]_real");
        String storedNameParam = parameters.get("attachFile[" + fieldId + "]_stored");

        File file = new File();

        if (StringUtils.isNotBlank(realNameParam)) {
            file.setName(realNameParam);
        } else {
            //if real name is empty, give file stored name
            file.setName(storedNameParam);
        }
        if (StringUtils.isNotEmpty(storedNameParam)) {
            file.setStoredName(storedNameParam);
        }

        return file;
    }
}

