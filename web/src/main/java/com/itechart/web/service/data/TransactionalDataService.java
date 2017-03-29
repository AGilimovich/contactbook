package com.itechart.web.service.data;

import com.itechart.data.dao.*;
import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.FullAttachmentDTO;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.*;
import com.itechart.data.exception.DaoException;
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
        IAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        IContactDao contactDao = new JdbcContactDao(transaction);
        //  JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        AbstractFileService fileService = ServiceFactory.getServiceFactory().getFileService();
        ArrayList<String> listOfFilesForDeleting = new ArrayList<>();
        Contact contact = null;
        try {
            contact = contactDao.getContactById(contactId);
            long photoId = contact.getPhoto();
            ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);
            //delete contact; phones, address, attachments are cascading
            contactDao.delete(contactId);
            //delete files
            listOfFilesForDeleting.add(fileDao.getFileById(photoId).getStoredName());
            fileDao.delete(photoId);
            for (Attachment attachment : attachments) {
                listOfFilesForDeleting.add(fileDao.getFileById(attachment.getFile()).getStoredName());
                fileDao.delete(attachment.getFile());
            }
            for (String name : listOfFilesForDeleting) {
                fileService.deleteFile(name);
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            transaction.rollbackTransaction();
        }
    }


    public void saveNewContact(FullContactDTO fullContactDTO) {
        Transaction transaction = tm.getTransaction();
        IPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        IAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        IContactDao contactDao = new JdbcContactDao(transaction);
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);

        try {
            //save photo
            long photoId = 0;
            photoId = fileDao.save(fullContactDTO.getPhoto());
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
        } catch (DaoException e) {
            transaction.rollbackTransaction();
        }

    }


    public void updateContact(FullContactDTO reconstructedContact, FullContactDTO contactToUpdate) {
        Transaction transaction = tm.getTransaction();
        IPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        IAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        IContactDao contactDao = new JdbcContactDao(transaction);
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        AbstractFileService fileService = ServiceFactory.getServiceFactory().getFileService();

        ArrayList<String> filesToDelete = new ArrayList<>();
        try {
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
                attachmentDao.update(fullAttachmentToUpdate.getAttachment());
            }
            for (FullAttachmentDTO fullAttachmentToDelete : contactToUpdate.getDeletedAttachments()) {
                File file = fileDao.getFileByAttachmentId(fullAttachmentToDelete.getAttachment().getId());
                filesToDelete.add(file.getStoredName());
                attachmentDao.delete(fullAttachmentToDelete.getAttachment().getId());
                fileDao.delete(file.getId());
            }
            for (FullAttachmentDTO fullAttachmentToCreate : contactToUpdate.getNewAttachments()) {
                File fileToSave = fullAttachmentToCreate.getFile();
                long fileId = fileDao.save(fileToSave);
                Attachment attachmentToSave = fullAttachmentToCreate.getAttachment();
                attachmentToSave.setContact(contactToUpdate.getContact().getContactId());
                attachmentToSave.setFile(fileId);
                attachmentDao.save(attachmentToSave);
            }
            for (String name : filesToDelete) {
                fileService.deleteFile(name);
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            transaction.rollbackTransaction();
        }


    }


    public ArrayList<Contact> getContactsWithBirthday(Date date) {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = null;
        try {
            contacts = contactDao.getByBirthDate(date);
            transaction.commitTransaction();

        } catch (DaoException e) {
            transaction.rollbackTransaction();
        }
        return contacts;
    }

    public ArrayList<Contact> getContactsByFields(SearchDTO dto) {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = null;
        try {
            contacts = contactDao.findContactsByFields(dto);
            transaction.commitTransaction();
        } catch (DaoException e) {
            transaction.rollbackTransaction();
        }
        return contacts;
    }

    public Contact getContactById(long contactId) {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        Contact contact = null;
        try {
            contact = contactDao.getContactById(contactId);
            transaction.commitTransaction();
        } catch (DaoException e) {
            transaction.rollbackTransaction();
        }
        return contact;
    }


    public Address getAddressByContactId(long contactId) {
        Transaction transaction = tm.getTransaction();
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        Address address = null;
        try {
            address = addressDao.getAddressByContactId(contactId);
            transaction.commitTransaction();
        } catch (DaoException e) {
           transaction.rollbackTransaction();
        }
        return address;
    }

    public File getPhotoById(long photoId) {
        Transaction transaction = tm.getTransaction();
        IFileDao fileDao = new JdbcFileDao(transaction);
        File photo = null;
        try {
            photo = fileDao.getFileById(photoId);
            transaction.commitTransaction();
        } catch (DaoException e) {
            transaction.rollbackTransaction();
        }
        return photo;
    }


    public ArrayList<MainPageContactDTO> getMainPageContactDTO() {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        ArrayList<Contact> contacts = null;
        ArrayList<MainPageContactDTO> mainPageContactDTOs = new ArrayList<>();
        try {
            contacts = contactDao.getAll();

            for (Contact contact : contacts) {
                File photo = fileDao.getFileById(contact.getPhoto());
                Address address = addressDao.getAddressByContactId(contact.getContactId());
                MainPageContactDTO mainPageContactDTO = new MainPageContactDTO(contact, address, photo);
                mainPageContactDTOs.add(mainPageContactDTO);
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            transaction.rollbackTransaction();
        }

        return mainPageContactDTOs;
    }


    public FullContactDTO getFullContactById(long contactId) {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        IPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        IAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        FullContactDTO fullContactDTO = null;
        Contact contact = null;
        try {
            contact = contactDao.getContactById(contactId);
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
            fullContactDTO = new FullContactDTO(contact, address, photo);
            fullContactDTO.setPhones(phones);
            fullContactDTO.setAttachments(fullAttachmentDTOs);
            transaction.commitTransaction();

        } catch (DaoException e) {
            transaction.rollbackTransaction();
        }

        return fullContactDTO;
    }

}
