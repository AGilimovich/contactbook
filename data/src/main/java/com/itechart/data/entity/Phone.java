package com.itechart.data.entity;

/**
 * Represents phone entity.
 */
public class Phone {
    private long id;
    private String countryCode;
    private String operatorCode;
    private String phoneNumber;
    private PhoneType phoneType;
    private String comment;
    private long contact;

    public Phone(long id, String countryCode, String operatorCode, String phoneNumber, PhoneType phoneType, String comment, long contact) {
        this.id = id;
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.comment = comment;
        this.contact = contact;
    }

    public Phone() {
    }

    public enum PhoneType {
        HOME, MOBILE;
    }

    public long getId() {
        return id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public String getComment() {
        return comment;
    }

    public long getContact() {
        return contact;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }
}
