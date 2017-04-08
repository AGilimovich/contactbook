package com.itechart.web.service.data;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Contact;
import com.itechart.web.service.data.exception.DataException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Interface of service class for manipulation data in database.
 */
public interface AbstractDataService {

    void deleteContact(long contactId) throws DataException;

    void deleteContacts(ArrayList<Long> contactId) throws DataException;

    void saveNewContact(FullContactDTO fullContactDTO) throws DataException;

    void updateContact(FullContactDTO reconstructed, FullContactDTO contactToUpdate) throws DataException;

    ArrayList<Contact> getContactsWithBirthday(Date date) throws DataException;

    ArrayList<MainPageContactDTO> getSearchResultContactsDTOForPage(SearchDTO dto, int page, int count) throws DataException;

    int getContactsSearchResultCount(SearchDTO dto) throws DataException;

    Contact getContactById(long contactId) throws DataException;

    ArrayList<MainPageContactDTO> getMainPageContactDTO(int page, int count) throws DataException;

    FullContactDTO getFullContactById(long contactId) throws DataException;

    int getContactsCount() throws DataException;


}
