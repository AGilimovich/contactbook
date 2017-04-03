package com.itechart.data.dao;

import com.itechart.data.entity.File;
import com.itechart.data.exception.DaoException;

import java.util.ArrayList;

/**
 * Interface of file DAO.
 */
public interface IFileDao {
    File getFileById(long id) throws DaoException;

    ArrayList<File> getFilesByName(String name) throws DaoException;

    void update(File file) throws DaoException;

    long save(File file) throws DaoException;

    void delete(long id) throws DaoException;

    File getFileByAttachmentId(long attachId) throws DaoException;
}
