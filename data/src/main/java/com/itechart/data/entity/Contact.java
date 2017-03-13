package com.itechart.data.entity;

import java.util.Date;

/**
 * Represents Contact entity.
 */
public class Contact {
    private int contactId;

    private String name;
    private String surname;
    private String patronymic;
    private Date dateOfBirth;
    private Gender gender;
    private String citizenship;
    private FamilyStatus familyStatus;
    private String website;
    private String email;
    private String placeOfWork;
    private Address address;
    private String photo;

    public Contact(int contactId, String name, String surname, String patronymic, Date dateOfBirth, Gender gender, String citizenship, FamilyStatus familyStatus, String website, String email, String placeOfWork, Address address, String photo) {
        this.contactId = contactId;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.citizenship = citizenship;
        this.familyStatus = familyStatus;
        this.website = website;
        this.email = email;
        this.placeOfWork = placeOfWork;
        this.address = address;

        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public enum Gender {
        MALE, FEMALE;

    }

    public enum FamilyStatus {
        SINGLE, MARRIED;
    }


    public int getId() {
        return contactId;
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

    public Gender getGender() {
        return gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public FamilyStatus getFamilyStatus() {
        return familyStatus;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoto() {
        return photo;
    }

}
