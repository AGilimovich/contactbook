package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.File;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Aleksandr on 25.03.2017.
 */

public class AttachFileBuilder {
    private Logger logger = LoggerFactory.getLogger(AttachFileBuilder.class);

    public File buildFile(Map<String, String> parameters) {
        logger.info("Build attachment file entity with parameters: {}", parameters);
        if (parameters == null) return null;
        String realNameParam = StringUtils.trim(parameters.get("realName"));
        String storedNameParam = parameters.get("storedName");


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

