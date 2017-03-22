package com.itechart.data.dao;

import com.itechart.data.entity.Address;

/**
 * Interface of DAO class for Address entity.
 */
public interface IAddressDao {

    long save(Address address);

    void delete(long id);

    void update(Address address);

    Address getAddressById(long id);

}
