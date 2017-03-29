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
        // JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        //  JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        AbstractFileService fileService = ServiceFactory.getServiceFactory().getFileService();

        Contact contact = contactDao.getContactById(contactId);
        long photoId = contact.getPhoto();
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);
        //delete contact; phones, address, attachments are cascading
        contactDao.delete(contactId);
        //delete files
        ArrayList<String> listOfFilesForDeleting = new ArrayList<>();
        listOfFilesForDeleting.add(fileDao.getFileById(photoId).getStoredName());
        fileDao.delete(photoId);
        for (Attachment attachment : attachments) {
            listOfFilesForDeleting.add(fileDao.getFileById(attachment.getFile()).getStoredName());
            fileDao.delete(attachment.getFile());
        }
        transaction.commitTransaction();
        //todo if transaction committed delete files from disk
        if (true) {
            for (String name : listOfFilesForDeleting) {
                fileService.deleteFile(name);
            }
        }

    }


    public void saveNewContact(FullContactDTO fullContactDTO) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);

        //save photo
        long photoId = fileDao.save(fullContactDTO.getPhoto());
        //set photo id in contact and save it
        Contact contactToSave = fullContactDTO.getContact();
        contactToSave.setPhoto(photoId);
        long contactId = contactDao.save(contactToSave);
        //set contact id in address object and save it
        Address addressToSave = fullContactDTO.getAddress();
        addressToSave.setContactId(contactId);
        addressDao.save(addressToSave);
        //set contact id in every phone object and save it
        for (Phone phoneToSave : fullContactDTO.getPhones()) {
            phoneToSave.setContact(contactId);
            phoneDao.save(phoneToSave);
        }
        //save attachments with files
        for (FullAttachmentDTO fullAttachmentToSave : fullContactDTO.getAttachments()) {
            File fileToSave = fullAttachmentToSave.getFile();
            long fileId = fileDao.save(fileToSave);
            Attachment attachmentToSave = fullAttachmentToSave.getAttachment();
            attachmentToSave.setContact(contactId);
            attachmentToSave.setFile(fileId);
            attachmentDao.save(attachmentToSave);
        }
        transaction.commitTransaction();
    }


    public void updateContact(FullContactDTO reconstructedContact, FullContactDTO contactToUpdate) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        AbstractFileService fileService = ServiceFactory.getServiceFactory().getFileService();

        ArrayList<String> filesToDelete = new ArrayList<>();
        //todo use commons library
        if (reconstructedContact.getPhoto() != null)
            filesToDelete.add(contactToUpdate.getPhoto().getStoredName());
        contactToUpdate.update(reconstructedContact);
        contactDao.update(contactToUpdate.getContact());
        addressDao.update(contactToUpdate.getAddress());
        fileDao.update(contactToUpdate.getPhoto());


        for (Phone phoneToCreate : contactToUpdate.getNewPhones()) {
            phoneToCreate.setContact(contactToUpdate.getContact().getContactId());
            phoneDao.save(phoneToCreate);
        }
        for (Phone phoneToUpdate : contactToUpdate.getUpdatedPhones()) {
            phoneToUpdate.setContact(contactToUpdate.getContact().getContactId());
            phoneDao.update(phoneToUpdate);
        }
        for (Phone phoneToDelete : contactToUpdate.getDeletedPhones()) {
            phoneToDelete.setContact(contactToUpdate.getContact().getContactId());
            phoneDao.delete(phoneToDelete.getId());
        }


        for (FullAttachmentDTO fullAttachmentToUpdate : contactToUpdate.getUpdatedAttachments()) {
            //todo delete old files and update db

            attachmentDao.update(fullAttachmentToUpdate.getAttachment());
        }
        for (FullAttachmentDTO fullAttachmentToDelete : contactToUpdate.getDeletedAttachments()) {
            filesToDelete.add(fullAttachmentToDelete.getFile().getStoredName());
            attachmentDao.delete(fullAttachmentToDelete.getAttachment().getId());
            fileDao.delete(fullAttachmentToDelete.getFile().getId());
        }
        for (FullAttachmentDTO fullAttachmentToCreate : contactToUpdate.getNewAttachments()) {
            File fileToSave = fullAttachmentToCreate.getFile();
            long fileId = fileDao.save(fileToSave);
            Attachment attachmentToSave = fullAttachmentToCreate.getAttachment();
            attachmentToSave.setContact(contactToUpdate.getContact().getContactId());
            attachmentToSave.setFile(fileId);
            attachmentDao.save(attachmentToSave);
        }

        transaction.commitTransaction();
        //todo if transaction committed delete files from disk
        if (false) {
            for (String name : filesToDelete) {
                fileService.deleteFile(name);
            }
        }

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


    public Address getAddressByContactId(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        Address address = addressDao.getAddressByContactId(contactId);
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
            Address address = addressDao.getAddressByContactId(contact.getContactId());
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
        Address address = addressDao.getAddressByContactId(contactId);
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
