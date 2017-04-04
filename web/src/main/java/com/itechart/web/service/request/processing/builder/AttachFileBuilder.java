package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.File;
import com.itechart.web.service.request.processing.parser.AttachmentFormFieldParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Aleksandr on 25.03.2017.
 */

public class AttachFileBuilder {
    private Logger logger = LoggerFactory.getLogger(AttachFileBuilder.class);

    public File buildFile(Map<String, String> parameters, String fieldId) {
        logger.info("Build attachment file entity with parameters: {} for form field with id: {}", parameters, fieldId);
        if (fieldId == null) return null;
        String realNameParam = StringUtils.trim(parameters.get("attachFile[" + fieldId + "]_real"));
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

