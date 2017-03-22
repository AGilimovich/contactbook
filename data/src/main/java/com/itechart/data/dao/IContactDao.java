package com.itechart.data.dao;

import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Contact;

import java.util.ArrayList;

/**
 * Interface for contacts data access object.
 */
public interface IContactDao {

    long save(Contact contact);

    void delete(long id);

    void update(Contact contact);

    ArrayList<Contact> getAll();

    Contact getContactById(long id);

    ArrayList<Contact> findContactsByFields(SearchDTO dto);


}
