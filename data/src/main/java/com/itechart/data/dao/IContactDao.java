package com.itechart.data.dao;

import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Contact;
import com.itechart.data.exception.DaoException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Interface of contact DAO.
 */
public interface IContactDao {

    long save(Contact contact) throws DaoException;

    void delete(long id) throws DaoException;

    void update(Contact contact) throws DaoException;

    ArrayList<Contact> getAll() throws DaoException;

    Contact getContactById(long id) throws DaoException;

 //   ArrayList<Contact> findContactsByFields(SearchDTO dto) throws DaoException;

    ArrayList<Contact> findContactsByFieldsLimit(SearchDTO dto, int from, int count) throws DaoException;

    ArrayList<Contact> getByBirthDate(Date date) throws DaoException;

    ArrayList<Contact> getContactsLimit(int startingFrom, int count) throws DaoException;

    int getContactsCount() throws DaoException;

    int getContactsSearchResultCount(SearchDTO dto)throws DaoException;

}
