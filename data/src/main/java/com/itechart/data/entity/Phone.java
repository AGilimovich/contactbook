package com.itechart.data.entity;

/**
 * Represents phone entity.
 */
public class Phone {
    private int id;


    public Phone(int id, String countryCode, String operatorCode, String phoneNumber, PhoneType phoneType, String comment) {
        this.id = id;
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.comment = comment;
    }

    private String countryCode;
    private String operatorCode;
    private String phoneNumber;
    private PhoneType phoneType;
    private String comment;

    public enum PhoneType {
        HOME, MOBILE;
    }

    public int getId() {
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
}
