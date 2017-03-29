package com.itechart.web.service.files;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 27.03.2017.
 */
public interface AbstractFileService {

    void deleteFile(String name);

    void deleteFiles(ArrayList<String> names);

    byte[] getFile(String name);
}
