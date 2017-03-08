package com.itechart.dao;

import com.itechart.entity.Contact;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class for manipulating data in table 'contacts' in database.
 */
public interface IContactDao {

    void save(Contact contact);

    void delete(Contact contact);

    void update(Contact contact);

    Contact getContactById(int id);

    ArrayList<Contact> getContactsByName(String name);

    ArrayList<Contact> getContactsBySurname(String surname);

    ArrayList<Contact> getContactsByPatronymic(String patronymic);

    ArrayList<Contact> getContactsYounger(Date date);

    ArrayList<Contact> getContactsOlder(Date date);

    ArrayList<Contact> getContactsByGender(Contact.Gender gender);

    ArrayList<Contact> getContactsByFamilyStatus(Contact.FamilyStatus familyStatus);

    ArrayList<Contact> getContactsByCitizenship(String citizenship);

    ArrayList<Contact> getContactsByAddress(String citizenship);

}
