package com.itechart.entity;

/**
 * Represents phone number.
 */
public class Phone {
    private String countryCode;
    private String operatorCode;
    private String phoneNumber;
    private PhoneType phoneType;
    private String comment;

    private enum PhoneType {
        HOME, WORK;
    }
}
