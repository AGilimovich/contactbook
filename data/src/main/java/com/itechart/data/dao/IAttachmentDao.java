package com.itechart.data.dao;

import com.itechart.data.entity.Attachment;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public interface IAttachmentDao {

    ArrayList<Attachment> getAllForContact(long id);

    Attachment getAttachmentById(long id);

    long save(Attachment attachment);

    void delete(long id);

    void update(Attachment attachment);

    void deleteForContact(long userId);

}
