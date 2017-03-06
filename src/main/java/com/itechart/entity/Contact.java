package com.itechart.entity;

import java.util.Date;

/**
 * Represents Contact in application.
 */
public class Contact {
    private String name;
    private String surname;
    private String patronymic;
    private Date dateOfBirth;
    private Gender gender;
    private String citizenship;
    private FamilyStatus familyStatus;
    private String webSite;
    private String email;
    private String currentJob;
    private Address address;

    private enum Gender {
        MALE, FEMALE;
    }

    private enum FamilyStatus {
        SINGLE, MARRIED;
    }

}
