package com.itechart.data.entity;

import java.util.Date;

/**
 * Represents Contact entity.
 */
public class Contact {
    private long contactId;

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
    private long photo;

    public Contact(long contactId, String name, String surname) {
        this.contactId = contactId;
        this.name = name;
        this.surname = surname;
       }

    public Contact() {
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

    public void update(Contact contact) {
        if (contact == null) return;
        this.name = contact.getName();
        this.surname = contact.getSurname();
        this.patronymic = contact.getPatronymic();
        this.dateOfBirth = contact.getDateOfBirth();
        this.gender = contact.getGender();
        this.citizenship = contact.getCitizenship();
        this.familyStatus = contact.getFamilyStatus();
        this.website = contact.getWebsite();
        this.email = contact.getEmail();
        this.placeOfWork = contact.getPlaceOfWork();
    }

    public long getContactId() {
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


    public long getPhoto() {
        return photo;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public void setFamilyStatus(FamilyStatus familyStatus) {
        this.familyStatus = familyStatus;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }


    public void setPhoto(long photo) {
        this.photo = photo;
    }
}
