package com.itechart.web.service.data;

import com.itechart.data.dao.*;
import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.FullAttachmentDTO;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.*;
import com.itechart.data.transaction.Transaction;
import com.itechart.data.transaction.TransactionManager;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.files.AbstractFileService;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleksandr on 22.03.2017.
 */
public class TransactionalDataService implements AbstractDataService {

    private TransactionManager tm;

    public TransactionalDataService(TransactionManager tm) {
        this.tm = tm;
    }

    public void deleteContact(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        AbstractFileService fileService = ServiceFactory.getServiceFactory().getFileService();



        Contact contact = contactDao.getContactById(contactId);
        File photo = fileDao.getFileById(contact.getPhoto());
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);

        for (Attachment attachment : attachments) {
            File file = fileDao.getFileById(attachment.getFile());
            fileDao.delete(file.getId());
            fileService.deleteFile(file.getStoredName());
        }
        phoneDao.deleteForUser(contactId);
        attachmentDao.deleteForUser(contactId);

        fileDao.delete(contact.getPhoto());
        fileService.deleteFile(photo.getStoredName());

        long addressId = contactDao.getContactById(contactId).getAddress();
        contactDao.delete(contactId);
        addressDao.delete(addressId);
        transaction.commitTransaction();
    }


    public void saveNewContact(FullContactDTO fullContactDTO) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);

        Address address = fullContactDTO.getAddress();
        ArrayList<Phone> phones = fullContactDTO.getNewPhones();
        ArrayList<FullAttachmentDTO> attachments = fullContactDTO.getNewAttachments();
        File photo = fullContactDTO.getPhoto();
        Contact contact = fullContactDTO.getContact();

//        if (photo != null) {
        long photoId = fileDao.save(photo);
        contact.setPhoto(photoId);
//        }
        long addressId = addressDao.save(address);
        contact.setAddress(addressId);
        long contactId = contactDao.save(contact);
        for (Phone phone : phones) {
            phone.setContact(contactId);
            phoneDao.save(phone);
        }
        for (FullAttachmentDTO fullAttachmentDTO : attachments) {
            File file = fullAttachmentDTO.getFile();
            long fileId = fileDao.save(file);
            Attachment attachment = fullAttachmentDTO.getAttachment();
            attachment.setContact(contactId);
            attachment.setFile(fileId);
            attachmentDao.save(attachment);
        }
        transaction.commitTransaction();
    }


    public void updateContact(FullContactDTO reconstructed, FullContactDTO contactToUpdate) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        Contact contactToUpdate = contactDao.getContactById(reconstructed.getContact().getContactId());
        File photo = reconstructed.getPhoto();
        photo.setId(contactToUpdate.getPhoto());
        //if received new photo file
        if (photo.getStoredName() != null && photo.getName() != null) {
            // TODO: 25.03.2017 remove old file from file system
            fileDao.update(photo);
        }

        Address addressToUpdate = addressDao.getAddressById(contactToUpdate.getAddress());
        //update fields with new data
        contactToUpdate.update(reconstructed.getContact());
        addressToUpdate.update(reconstructed.getAddress());
        //update in db
        addressDao.update(addressToUpdate);
        contactDao.update(contactToUpdate);

        //delete old phones
        phoneDao.deleteForUser(reconstructed.getContact().getContactId());
        //persist into db new phones
        for (Phone phone : reconstructed.getNewPhones()) {
            phone.setContact(reconstructed.getContact().getContactId());
            phoneDao.save(phone);
        }

        for (FullAttachmentDTO fullAttachmentDTO : reconstructed.getDeletedAttachments()) {
            fileDao.delete(fullAttachmentDTO.getFile().getId());
            attachmentDao.delete(fullAttachmentDTO.getAttachment().getId());
            // TODO: 23.03.2017 delete from disk
        }
        for (FullAttachmentDTO fullAttachmentDTO : reconstructed.getNewAttachments()) {
            File file = fullAttachmentDTO.getFile();
            long fileId = fileDao.save(file);
            Attachment attachment = fullAttachmentDTO.getAttachment();
            attachment.setFile(fileId);
            attachment.setContact(reconstructed.getContact().getContactId());
            attachmentDao.save(attachment);
        }
        for (FullAttachmentDTO fullAttachmentDTO : reconstructed.getUpdatedAttachments()) {
            File file = fullAttachmentDTO.getFile();
            if (file != null)
                fileDao.update(file);
            attachmentDao.update(fullAttachmentDTO.getAttachment());
        }
        transaction.commitTransaction();

    }


    public ArrayList<Contact> getContactsWithBirthday(Date date) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = contactDao.getByBirthDate(date);
        transaction.commitTransaction();
        return contacts;
    }

    public ArrayList<Contact> getContactsByFields(SearchDTO dto) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = contactDao.findContactsByFields(dto);
        transaction.commitTransaction();
        return contacts;
    }

    public Contact getContactById(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        Contact contact = contactDao.getContactById(contactId);
        transaction.commitTransaction();
        return contact;
    }


    public Address getAddressById(long addressId) {
        Transaction transaction = tm.getTransaction();
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        Address address = addressDao.getAddressById(addressId);
        transaction.commitTransaction();
        return address;
    }

    public File getPhotoById(long photoId) {
        Transaction transaction = tm.getTransaction();
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        File photo = fileDao.getFileById(photoId);
        transaction.commitTransaction();
        return photo;
    }


    public ArrayList<MainPageContactDTO> getMainPageContactDTO() {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        ArrayList<Contact> contacts = contactDao.getAll();
        ArrayList<MainPageContactDTO> mainPageContactDTOs = new ArrayList<>();
        for (Contact contact : contacts) {
            File photo = fileDao.getFileById(contact.getPhoto());
            Address address = addressDao.getAddressById(contact.getAddress());
            MainPageContactDTO mainPageContactDTO = new MainPageContactDTO(contact, address, photo);
            mainPageContactDTOs.add(mainPageContactDTO);
        }
        transaction.commitTransaction();
        return mainPageContactDTOs;
    }


    public FullContactDTO getFullContactById(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);

        Contact contact = contactDao.getContactById(contactId);
        Address address = addressDao.getAddressById(contactId);
        File photo = fileDao.getFileById(contact.getPhoto());
        ArrayList<Phone> phones = phoneDao.getAllForContact(contactId);

        ArrayList<FullAttachmentDTO> fullAttachmentDTOs = new ArrayList<>();
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);
        for (Attachment attachment : attachments) {
            File file = fileDao.getFileById(attachment.getFile());
            FullAttachmentDTO fullAttachmentDTO = new FullAttachmentDTO(attachment, file);
            fullAttachmentDTOs.add(fullAttachmentDTO);
        }


        FullContactDTO fullContactDTO = new FullContactDTO(contact, address, photo);
        fullContactDTO.setPhones(phones);
        fullContactDTO.setAttachments(fullAttachmentDTOs);


        transaction.commitTransaction();
        return fullContactDTO;
    }

}
