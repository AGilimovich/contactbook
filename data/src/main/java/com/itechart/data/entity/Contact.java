package com.itechart.data.entity;

import java.awt.*;
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
    private Image photo;

    public enum Gender {
        MALE, FEMALE;


    }

    public enum FamilyStatus {
        SINGLE, MARRIED;
    }

}
