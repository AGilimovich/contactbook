package com.itechart.data.dao;

import com.itechart.data.dto.ContactDTO;
import com.itechart.data.entity.Contact;

import java.util.ArrayList;

/**
 * Interface for contacts data access object.
 */
public interface IContactDao {

    void save(Contact contact);

    void delete(Contact contact);

    void update(Contact contact);

    ArrayList<Contact> getAll();

    Contact getContactById(int id);

    ArrayList<ContactDTO> getAllDto();


}
