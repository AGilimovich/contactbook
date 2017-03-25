package com.itechart.data.dto;

import com.itechart.data.entity.Address;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.ContactFile;

import java.util.Date;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class ContactWithAddressDTO {
    private long contactId;
    private String name;
    private String surname;
    private String patronymic;
    private Date dateOfBirth;
    private String country;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String placeOfWork;
    private String photo;


    public ContactWithAddressDTO(Contact c, Address a, ContactFile file) {
        this.contactId = c.getContactId();
        this.name = c.getName();
        this.surname = c.getSurname();
        this.patronymic = c.getPatronymic();
        this.dateOfBirth = c.getDateOfBirth();
        this.country = a.getCountry();
        this.city = a.getCity();
        this.street = a.getStreet();
        this.house = a.getHouse();
        this.apartment = a.getApartment();
        this.placeOfWork = c.getPlaceOfWork();
        this.photo = file.getStoredName();
    }

    public long getContactId() {
        return contactId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getApartment() {
        return apartment;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public String getPhoto() {
        return photo;
    }
}
