package com.itechart.data.dto;

import com.itechart.data.entity.Contact;

import java.util.Date;

/**
 * DTO object containing fields by which search executed.
 */
public class SearchDTO {
    private String name;
    private String surname;
    private String patronymic;
    private Date fromDate;
    private Date toDate;
    private Contact.Gender gender;
    private Contact.FamilyStatus familyStatus;
    private String citizenship;
    //address fields
    private String placeOfWork;
    private String country;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String zipCOde;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Contact.Gender getGender() {
        return gender;
    }

    public void setGender(Contact.Gender gender) {
        this.gender = gender;
    }

    public Contact.FamilyStatus getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(Contact.FamilyStatus familyStatus) {
        this.familyStatus = familyStatus;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getZipCOde() {
        return zipCOde;
    }

    public void setZipCOde(String zipCOde) {
        this.zipCOde = zipCOde;
    }
}
