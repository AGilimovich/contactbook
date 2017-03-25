package com.itechart.data.dao;

import com.itechart.data.entity.ContactFile;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public interface IContactFileDao {
    ContactFile getFileById(long id);

    ArrayList<ContactFile> getFilesByName(String name);

    void update(ContactFile file);

    long save(ContactFile file);

    void delete(long id);
}
