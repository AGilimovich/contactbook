package com.itechart.data.dao;

import com.itechart.data.entity.Phone;
import com.itechart.data.exception.DaoException;

import java.util.ArrayList;

/**
 * Interface of phone DAO.
 */
public interface IPhoneDao {

    ArrayList<Phone> getAllForContact(long id) throws DaoException;

    long save(Phone phone) throws DaoException;

    void delete(long id) throws DaoException;

    void update(Phone phone) throws DaoException;

}
