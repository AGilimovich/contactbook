package com.itechart.data.dto;

import java.util.Date;

/**
 * Data transfer object for contact entity.
 */
public class ContactDTO {


    private int contactId;
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

    public ContactDTO(int contactId, String name, String surname, String patronymic, Date dateOfBirth, String country, String city, String street, String house, String apartment, String placeOfWork, String photo) {
        this.contactId = contactId;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.placeOfWork = placeOfWork;
        this.photo = photo;
    }

    public int getContactId() {
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
