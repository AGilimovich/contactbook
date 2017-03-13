package com.itechart.data.dao;

import com.itechart.data.entity.Attachment;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public interface IAttachmentDao {

    ArrayList<Attachment> getAllForContact(int id);

    Attachment getAttachmentById(int id);

    void save(Attachment attachment);

    void delete(Attachment attachment);

    void update(Attachment attachment);


}
