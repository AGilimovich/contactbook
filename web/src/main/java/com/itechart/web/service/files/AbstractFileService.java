package com.itechart.web.service.files;

import com.itechart.data.entity.File;

import java.util.Collection;

/**
 * Interface of service class for executing operations with files in file system.
 */
public interface AbstractFileService {

    void deleteFile(String name);

    void deleteFiles(Collection<File> files);

    byte[] getFile(String name);
}
