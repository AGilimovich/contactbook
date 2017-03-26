package com.itechart.web.service.data;

import com.itechart.data.entity.FullContactEntity;
import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.File;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleksandr on 26.03.2017.
 */
public interface IDataService {

    void deleteContact(long contactId);

    void saveNewContact(FullContactEntity fullContactEntity);

    void updateContact(FullContactEntity changedContact, FullContactEntity contactToUpdate);

    ArrayList<Contact> getContactsWithBirthday(Date date);

    ArrayList<Contact> getContactsByFields(SearchDTO dto);

    Contact getContactById(long contactId);

    Address getAddressById(long addressId);

    File getFileById(long photoId);

    ArrayList<MainPageContactDTO> getMainPageContactDTOs();

    FullContactEntity getFullContactEntityById(long contactId);


}
