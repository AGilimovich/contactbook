package com.itechart.data.dao;

import com.itechart.data.entity.File;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public interface IFileDao {
    File getFileById(long id);

    ArrayList<File> getFilesByIdInFileStorage(long id);

    ArrayList<File> getFilesByName(String name);

    void update(File file);

    long save(File file);

    void delete(long id);

    void deleteForContact(long contactId);

}
