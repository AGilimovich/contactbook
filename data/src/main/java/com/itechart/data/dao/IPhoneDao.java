package com.itechart.data.dao;

import com.itechart.data.entity.Phone;
import com.itechart.data.exception.DaoException;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public interface IPhoneDao {

    ArrayList<Phone> getAllForContact(long id) throws DaoException;

    Phone getPhoneById(long id) throws DaoException;

    long save(Phone phone) throws DaoException;

    void delete(long id) throws DaoException;

    void update(Phone phone) throws DaoException;

    void deleteForContact(long contactId) throws DaoException;

}
