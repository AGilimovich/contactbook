package com.itechart.web.service.files;

import java.util.Collection;

/**
 * Created by Aleksandr on 27.03.2017.
 */
public interface AbstractFileService {

    void deleteFile(String name);


    void deleteFiles(Collection<String> names);


    byte[] getFile(String name);
}
