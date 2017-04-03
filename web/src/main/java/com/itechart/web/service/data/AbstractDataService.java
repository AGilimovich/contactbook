package com.itechart.web.service.data;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.File;
import com.itechart.web.service.data.exception.DataException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleksandr on 27.03.2017.
 */
public interface AbstractDataService {

    void deleteContact(long contactId) throws DataException;

    void deleteContacts(ArrayList<Long> contactId) throws DataException;

    void saveNewContact(FullContactDTO fullContactDTO) throws DataException;

    void updateContact(FullContactDTO reconstructed, FullContactDTO contactToUpdate) throws DataException;

    ArrayList<Contact> getContactsWithBirthday(Date date) throws DataException;

    ArrayList<Contact> getAllContactsByFields(SearchDTO dto) throws DataException;

    ArrayList<Contact> getContactsByFieldsForPage(SearchDTO dto, int page, int count) throws DataException;

    int getContactsSearchResultCount(SearchDTO dto) throws DataException;

    Contact getContactById(long contactId) throws DataException;

    Address getAddressByContactId(long addressId) throws DataException;

    File getPhotoById(long photoId) throws DataException;

    ArrayList<MainPageContactDTO> getMainPageContactDTO(int page, int count) throws DataException;

    FullContactDTO getFullContactById(long contactId) throws DataException;

    int getContactsCount() throws DataException;


}
