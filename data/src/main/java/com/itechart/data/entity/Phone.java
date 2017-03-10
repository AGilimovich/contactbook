package com.itechart.data.entity;

/**
 * Represents phone entity.
 */
public class Phone {
    private int id;


    private String countryCode;
    private String operatorCode;
    private String phoneNumber;
    private PhoneType phoneType;
    private String comment;

    private enum PhoneType {
        HOME, MOBILE;
    }
}
