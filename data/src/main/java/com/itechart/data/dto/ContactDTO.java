package com.itechart.data.dto;

import java.util.Date;

/**
 * Data transfer object for contact entity.
 */
public class ContactDTO {


    private int contactId;
    private String name;
    private String surname;
    private String patronymic;
    private Date dateOfBirth;
    private int address;
    private String placeOfWork;
    private String photo;

    public ContactDTO(int contactId, String name, String surname, String patronymic, Date dateOfBirth, int address, String placeOfWork, String photo) {
        this.contactId = contactId;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.address = address;

        this.placeOfWork = placeOfWork;
        this.photo = photo;
    }

    public int getContactId() {
        return contactId;
    }

    public String getName() {
        return name;
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


    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public String getPhoto() {
        return photo;
    }


    public int getAddress() {
        return address;
    }
}
