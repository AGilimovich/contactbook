package com.itechart.data.dto;

import com.itechart.data.entity.Address;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.File;

import java.util.Date;

/**
 * Data transfer object which includes some fields of contact and address objects.
 */
public class MainPageContactDTO {
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


    public MainPageContactDTO(Contact c, Address a, File photo) {
        if (c == null || a == null || photo == null) return;
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
        this.photo = photo.getStoredName();
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
