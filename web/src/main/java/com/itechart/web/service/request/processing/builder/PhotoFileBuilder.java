package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.File;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class PhotoFileBuilder {
    public File buildFile(Map<String, String> parameters) {
        String realNameParam = parameters.get("photoFile_real");
        String storedNameParam = parameters.get("photoFile_stored");
        File file = new File();

        if (StringUtils.isNotEmpty(realNameParam)){
            file.setName(realNameParam);
        } else {
            //if real name is empty, give file stored name
            file.setName(storedNameParam);
        }
        if (StringUtils.isNotEmpty(storedNameParam)){
            file.setStoredName(storedNameParam);
        }

        // TODO: 29.03.2017 if  was not saved??

        return file;
    }
}
