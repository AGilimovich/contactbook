package com.itechart.data.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents Contact entity.
 */
public class Contact {
    private int id;

    private String name;
    private String surname;
    private String patronymic;
    private Date dateOfBirth;
    private Gender gender;
    private String citizenship;
    private FamilyStatus familyStatus;
    private String webSite;
    private String email;
    private String placeOfWork;
    private Address address;
    private ArrayList<Phone> phones;
    private ArrayList<Attachment> attachments;
    private String photo;

    public Contact(int id, String name, String surname, String patronymic, Date dateOfBirth, Gender gender, String citizenship, FamilyStatus familyStatus, String webSite, String email, String placeOfWork, Address address, ArrayList<Phone> phones, ArrayList<Attachment> attachments, String photo) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.citizenship = citizenship;
        this.familyStatus = familyStatus;
        this.webSite = webSite;
        this.email = email;
        this.placeOfWork = placeOfWork;
        this.address = address;
        this.phones = phones;
        this.attachments = attachments;
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
        return id;
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

    public String getWebSite() {
        return webSite;
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

    public ArrayList<Phone> getPhones() {
        return phones;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public String getPhoto() {
        return photo;
    }

}
