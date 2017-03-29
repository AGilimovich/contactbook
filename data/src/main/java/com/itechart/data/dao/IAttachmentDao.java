package com.itechart.data.dao;

import com.itechart.data.entity.Attachment;
import com.itechart.data.exception.DaoException;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public interface IAttachmentDao {

    ArrayList<Attachment> getAllForContact(long id) throws DaoException;

    Attachment getAttachmentById(long id) throws DaoException;

    long save(Attachment attachment) throws DaoException;

    void delete(long id) throws DaoException;

    void update(Attachment attachment) throws DaoException;

    void deleteForUser(long userId) throws DaoException;

}
