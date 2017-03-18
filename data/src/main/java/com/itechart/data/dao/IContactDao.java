package com.itechart.data.dao;

import com.itechart.data.dto.ContactDTO;
import com.itechart.data.entity.Contact;

import java.util.ArrayList;
import java.util.Date;

/**
 * Interface for contacts data access object.
 */
public interface IContactDao {

    long save(Contact contact);

    void delete(long id);

    void update(Contact contact);

    ArrayList<Contact> getAll();

    Contact getContactById(long id);

    ArrayList<Contact> findContactsByFields(String surname, String name, String patronymic, Date fromDate, Date toDate, Contact.Gender gender, Contact.FamilyStatus familyStatus, String Citizenship, String country, String city, String street, String house, String apartment, String zipCode);

}
