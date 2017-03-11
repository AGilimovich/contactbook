package com.itechart.data.dao;

import com.itechart.data.entity.Contact;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Interface for contacts data access object.
 */
public interface IContactDao {

    void save(Contact contact);

    void delete(Contact contact);

    void update(Contact contact);

    ArrayList<Contact> getAll();

    Contact getContactById(int id);

    Contact getContactByCriteria(Properties criteria);

//    ArrayList<Contact> getContactsByName(String name);
//
//    ArrayList<Contact> getContactsBySurname(String surname);
//
//    ArrayList<Contact> getContactsByPatronymic(String patronymic);
//
//    ArrayList<Contact> getContactsYounger(Date date);
//
//    ArrayList<Contact> getContactsOlder(Date date);
//
//    ArrayList<Contact> getContactsByGender(Contact.Gender gender);
//
//    ArrayList<Contact> getContactsByFamilyStatus(Contact.FamilyStatus familyStatus);
//
//    ArrayList<Contact> getContactsByCitizenship(String citizenship);
//
//    ArrayList<Contact> getContactsByCountry(String country);
//
//    ArrayList<Contact> getContactsByCity(String city);
//
//    ArrayList<Contact> getContactsByStreet(String street);
//
//    ArrayList<Contact> getContactsByHouse(String house);
//
//    ArrayList<Contact> getContactsByApartment(String apartment);
//
//    ArrayList<Contact> getContactsByZipCode(String zipCode);


}
