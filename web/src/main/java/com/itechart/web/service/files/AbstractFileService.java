package com.itechart.web.service.files;

/**
 * Created by Aleksandr on 27.03.2017.
 */
public interface AbstractFileService {

    void deleteFile(String name);

    byte[] getFile(String name);
}
