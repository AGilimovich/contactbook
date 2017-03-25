package com.itechart.web.service.request.processing;

import com.itechart.data.entity.ContactFile;

import java.util.Map;

/**
 * Created by Aleksandr on 25.03.2017.
 */

public class AttachFileBuilder {
    public ContactFile buildFile(Map<String, String> parameters, String id) {

        String realName = parameters.get("attachFile[" + id + "]_real");
        String storedName = parameters.get("attachFile[" + id + "]_stored");

        if (realName == null)
            realName = storedName;
        ContactFile file = new ContactFile();
        file.setName(realName);
        file.setStoredName(storedName);
        return file;
    }
}

