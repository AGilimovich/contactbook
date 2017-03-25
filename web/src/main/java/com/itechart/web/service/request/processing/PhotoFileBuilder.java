package com.itechart.web.service.request.processing;

import com.itechart.data.entity.ContactFile;

import java.util.Map;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class PhotoFileBuilder {
    public ContactFile buildFile(Map<String, String> parameters) {
        String realName = parameters.get("photoFile_real");
        String storedName = parameters.get("photoFile_stored");

        if (realName == null)
            realName = storedName;
        ContactFile file = new ContactFile();
        file.setName(realName);
        file.setStoredName(storedName);
        return file;
    }
}
