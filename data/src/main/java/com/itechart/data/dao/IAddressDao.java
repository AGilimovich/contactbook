package com.itechart.data.dao;

import com.itechart.data.entity.Address;
import com.itechart.data.exception.DaoException;

/**
 * Interface of address DAO.
 */
public interface IAddressDao {

    long save(Address address) throws DaoException;

    void deleteForContact(long contactId) throws DaoException;

    void update(Address address) throws DaoException;

    Address getAddressByContactId(long contactId) throws DaoException;

}
