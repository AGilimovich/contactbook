package com.itechart.data.dao;

import com.itechart.data.entity.Address;

/**
 * Interface of DAO class for Address entity.
 */
public interface IAddressDao {

    long save(Address address, long contactId);

    void delete(long id);

    void update(Address address);

    Address getAddressById(long id);

}
