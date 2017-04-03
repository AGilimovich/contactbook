package com.itechart.web.service.data;

import com.itechart.data.dao.*;
import com.itechart.data.dto.FullAttachmentDTO;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.*;
import com.itechart.data.exception.DaoException;
import com.itechart.data.transaction.Transaction;
import com.itechart.data.transaction.TransactionManager;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.files.AbstractFileService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleksandr on 22.03.2017.
 */
public class TransactionalDataService implements AbstractDataService {
    private static final Logger log = Logger.getLogger(TransactionalDataService.class);
    private TransactionManager tm;

    public TransactionalDataService(TransactionManager tm) {
        this.tm = tm;
    }

    @Override
    public void deleteContact(long contactId) throws DataException {
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
            log.error(e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
    }

    @Override
    public void deleteContacts(ArrayList<Long> contactId) throws DataException {
        for (long id : contactId) {
            deleteContact(id);
        }
    }


    @Override
    public void saveNewContact(FullContactDTO fullContactDTO) throws DataException {
        Transaction transaction = tm.getTransaction();
        IPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        IAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        IContactDao contactDao = new JdbcContactDao(transaction);
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        AbstractFileService fileService = ServiceFactory.getServiceFactory().getFileService();
        ArrayList<String> savedFiles = new ArrayList<>();
        try {
            //save photo
            Contact contactToSave = fullContactDTO.getContact();
            if (StringUtils.isNotEmpty(fullContactDTO.getPhoto().getStoredName())) {
                savedFiles.add(fullContactDTO.getPhoto().getStoredName());
            }

            long photoId = fileDao.save(fullContactDTO.getPhoto());

            //set photo id in contact and save it
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
                if (fileToSave != null) {
                    savedFiles.add(fileToSave.getStoredName());
                    long fileId = fileDao.save(fileToSave);
                    Attachment attachmentToSave = fullAttachmentToSave.getAttachment();
                    attachmentToSave.setContact(contactId);
                    attachmentToSave.setFile(fileId);
                    attachmentDao.save(attachmentToSave);
                }
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            log.error(e.getCause().getMessage());
            transaction.rollbackTransaction();
            fileService.deleteFiles(savedFiles);
            throw new DataException(e.getMessage());
        }

    }

    @Override
    public void updateContact(FullContactDTO reconstructedContact, FullContactDTO contactToUpdate) throws DataException {
        Transaction transaction = tm.getTransaction();
        IPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        IAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        IContactDao contactDao = new JdbcContactDao(transaction);
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        AbstractFileService fileService = ServiceFactory.getServiceFactory().getFileService();

        ArrayList<String> filesToDelete = new ArrayList<>();
        ArrayList<String> savedFiles = new ArrayList<>();

        try {
            //if old file exists and new file was stored -> mark old file for delition
            if (StringUtils.isNotEmpty(contactToUpdate.getPhoto().getStoredName()) && StringUtils.isNotEmpty(reconstructedContact.getPhoto().getStoredName()))
                filesToDelete.add(contactToUpdate.getPhoto().getStoredName());

            contactToUpdate.update(reconstructedContact);
            if (StringUtils.isNotEmpty(reconstructedContact.getPhoto().getStoredName())) {
                fileDao.update(contactToUpdate.getPhoto());
            }

            contactDao.update(contactToUpdate.getContact());
            addressDao.update(contactToUpdate.getAddress());


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
                savedFiles.add(fileToSave.getStoredName());
                Attachment attachmentToSave = fullAttachmentToCreate.getAttachment();
                attachmentToSave.setContact(contactToUpdate.getContact().getContactId());
                attachmentToSave.setFile(fileId);
                attachmentDao.save(attachmentToSave);
            }

            fileService.deleteFiles(filesToDelete);

            transaction.commitTransaction();
        } catch (DaoException e) {
            log.error(e.getCause().getMessage());
            transaction.rollbackTransaction();
            fileService.deleteFiles(savedFiles);
            throw new DataException(e.getMessage());
        }
    }

    @Override
    public ArrayList<Contact> getContactsWithBirthday(Date date) throws DataException {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = null;
        try {
            contacts = contactDao.getByBirthDate(date);
            transaction.commitTransaction();

        } catch (DaoException e) {
            log.error(e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
        return contacts;
    }

    @Override
    public ArrayList<Contact> getAllContactsByFields(SearchDTO dto) throws DataException {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = new ArrayList<>();
        // TODO: 03.04.2017
//        try {
//            contacts = contactDao.findContactsByFields(dto);
//            transaction.commitTransaction();
//        } catch (DaoException e) {
//            transaction.rollbackTransaction();
//            throw new DataException(e.getMessage());
//        }
        return contacts;
    }

    @Override
    public ArrayList<Contact> getContactsByFieldsForPage(SearchDTO dto, int page, int count) throws DataException {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            contacts = contactDao.findContactsByFieldsLimit(dto, page * count, count);
            transaction.commitTransaction();
        } catch (DaoException e) {
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
        return contacts;
    }

    @Override
    public int getContactsSearchResultCount(SearchDTO dto) throws DataException {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        try {
            return contactDao.getContactsSearchResultCount(dto);
        } catch (DaoException e) {
            log.error(e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
    }

    @Override
    public Contact getContactById(long contactId) throws DataException {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        Contact contact = null;
        try {
            contact = contactDao.getContactById(contactId);
            transaction.commitTransaction();
        } catch (DaoException e) {
            log.error(e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
        return contact;
    }

    @Override
    public Address getAddressByContactId(long contactId) throws DataException {
        Transaction transaction = tm.getTransaction();
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        Address address = null;
        try {
            address = addressDao.getAddressByContactId(contactId);
            transaction.commitTransaction();
        } catch (DaoException e) {
            log.error(e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
        return address;
    }

    @Override
    public File getPhotoById(long photoId) throws DataException {
        Transaction transaction = tm.getTransaction();
        IFileDao fileDao = new JdbcFileDao(transaction);
        File photo = null;
        try {
            photo = fileDao.getFileById(photoId);
            transaction.commitTransaction();
        } catch (DaoException e) {
            log.error(e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
        return photo;
    }

    @Override
    public ArrayList<MainPageContactDTO> getMainPageContactDTO(int page, int count) throws DataException {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        ArrayList<Contact> contacts = null;
        ArrayList<MainPageContactDTO> mainPageContactDTOs = new ArrayList<>();
        try {
            contacts = contactDao.getContactsLimit(page * count, count);
            for (Contact contact : contacts) {
                File photo = fileDao.getFileById(contact.getPhoto());
                Address address = addressDao.getAddressByContactId(contact.getContactId());
                MainPageContactDTO mainPageContactDTO = new MainPageContactDTO(contact, address, photo);
                mainPageContactDTOs.add(mainPageContactDTO);
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            log.error(e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }

        return mainPageContactDTOs;
    }

    @Override
    public FullContactDTO getFullContactById(long contactId) throws DataException {
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
            log.error(e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }

        return fullContactDTO;
    }

    @Override
    public int getContactsCount() throws DataException {
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        try {
            return contactDao.getContactsCount();
        } catch (DaoException e) {
            log.error(e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
    }

}
