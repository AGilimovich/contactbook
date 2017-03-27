package com.itechart.data.dao;

import com.itechart.data.entity.Address;

/**
 * Interface of DAO class for Address entity.
 */
public interface IAddressDao {

    long save(Address address);

    void deleteForContact(long contactId);

    void update(Address address);

    Address getAddressByContactId(long contactId);

}
