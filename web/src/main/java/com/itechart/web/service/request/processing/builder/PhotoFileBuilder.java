package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.File;
import com.itechart.web.service.request.processing.parser.AttachmentFormFieldParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Builder of photo's file object from request parameters.
 */
public class PhotoFileBuilder {
    private Logger logger = LoggerFactory.getLogger(PhotoFileBuilder.class);

    public File buildFile(Map<String, String> parameters) {
        logger.info("Build photo file entity with parameters: {}", parameters);

        String realNameParam = StringUtils.trim(parameters.get("realName"));
        String storedNameParam = parameters.get("storedName");
        File file = new File();

        if (StringUtils.isNotBlank(realNameParam)) {
            file.setName(realNameParam);
        } else {
            //if real name is empty, give file stored name
            file.setName(storedNameParam);
        }
        if (StringUtils.isNotBlank(storedNameParam)) {
            file.setStoredName(storedNameParam);
        }
        return file;
    }
}
