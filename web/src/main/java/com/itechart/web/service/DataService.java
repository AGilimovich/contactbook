package com.itechart.web.service;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.data.dto.ContactWithAddressDTO;
import com.itechart.data.dto.FullContact;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 22.03.2017.
 */
public class DataService {
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;

    public DataService(JdbcContactDao contactDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao, JdbcAddressDao addressDao) {
        this.contactDao = contactDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
        this.addressDao = addressDao;

    }

    public void deleteContact(long contactId) {
        phoneDao.deleteForUser(contactId);
        attachmentDao.deleteForUser(contactId);
        // TODO: 23.03.2017 delete from disk
        long addressId = contactDao.getContactById(contactId).getAddress();
        contactDao.delete(contactId);
        addressDao.delete(addressId);
    }


    public void saveNewContact(FullContact fullContact) {
        Address address = fullContact.getAddress();
        ArrayList<Phone> phones = fullContact.getNewPhones();
        ArrayList<Attachment> attachments = fullContact.getNewAttachments();
        Contact contact = fullContact.getContact();

        long addressId = addressDao.save(address);
        contact.setAddress(addressId);
        long contactId = contactDao.save(contact);
        for (Phone phone : phones) {
            phone.setContact(contactId);
            phoneDao.save(phone);
        }
        for (Attachment attachment : attachments) {
            attachment.setContact(contactId);
            attachmentDao.save(attachment);
        }
    }


    public void updateContact(FullContact fullContact) {

        Contact contactToUpdate = contactDao.getContactById(fullContact.getContact().getContactId());
        Address addressToUpdate = addressDao.getAddressById(contactToUpdate.getAddress());
        //update fields with new data
        contactToUpdate.update(fullContact.getContact());
        addressToUpdate.update(fullContact.getAddress());
        //update in db
        addressDao.update(addressToUpdate);
        contactDao.update(contactToUpdate);

        //delete old phones
        phoneDao.deleteForUser(fullContact.getContact().getContactId());
        //persist into db new phones
        for (Phone phone : fullContact.getNewPhones()) {
            phone.setContact(fullContact.getContact().getContactId());
            phoneDao.save(phone);
        }

        for (Attachment attachment : fullContact.getDeletedAttachments()) {
            attachmentDao.delete(attachment.getId());
            // TODO: 23.03.2017 delete from disk
        }
        for (Attachment attachment : fullContact.getNewAttachments()) {
            attachment.setContact(fullContact.getContact().getContactId());
            attachmentDao.save(attachment);
        }
        for (Attachment attachment : fullContact.getUpdatedAttachments()) {
            attachmentDao.update(attachment);
        }

    }


    public ArrayList<Contact> getAllContacts() {

        return contactDao.getAll();
    }

    public ArrayList<Contact> getContactsByFields(SearchDTO dto) {
        return contactDao.findContactsByFields(dto);
    }

    public Contact getContactById(long contactId) {
        return contactDao.getContactById(contactId);
    }


    public Address getAddressById(long addressId) {
        return addressDao.getAddressById(addressId);
    }

    public ArrayList<Phone> getAllPhonesForContact(long contactId) {
        return phoneDao.getAllForContact(contactId);
    }

    public ArrayList<Attachment> getAllAttachmentsForContact(long contactId) {
        return attachmentDao.getAllForContact(contactId);
    }

    public ArrayList<ContactWithAddressDTO> getContactsWithAddressDTO() {
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        ArrayList<Contact> contactsDTOs = dataService.getAllContacts();
        ArrayList<ContactWithAddressDTO> contactWithAddressDTOs = new ArrayList<>();
        for (Contact contact : contactsDTOs) {
            Address address = dataService.getAddressById(contact.getAddress());
            ContactWithAddressDTO contactWithAddressDTO = new ContactWithAddressDTO(contact, address);
            contactWithAddressDTOs.add(contactWithAddressDTO);
        }
        return contactWithAddressDTOs;
    }

}
