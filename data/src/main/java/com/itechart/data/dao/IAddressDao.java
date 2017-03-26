package com.itechart.data.dao;

import com.itechart.data.entity.Address;

import java.util.ArrayList;

/**
 * Interface of DAO class for Address entity.
 */
public interface IAddressDao {

    long save(Address address);

    ArrayList<Address> getAllForContact(long contactId);

    void delete(long id);

    void deleteForContact(long contactId);

    void update(Address address);

    Address getAddressById(long id);

}
