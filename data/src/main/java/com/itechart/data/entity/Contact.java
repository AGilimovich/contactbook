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
    private long address;
    private String photo;

    public Contact(long contactId, String name, String surname, String patronymic, Date dateOfBirth, Gender gender, String citizenship, FamilyStatus familyStatus, String website, String email, String placeOfWork, long address, String photo) {
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
        if (contact.getPhoto() != null)
            this.photo = contact.getPhoto();
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

    public long getAddress() {
        return address;
    }

    public String getPhoto() {
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

    public void setAddress(long address) {
        this.address = address;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
