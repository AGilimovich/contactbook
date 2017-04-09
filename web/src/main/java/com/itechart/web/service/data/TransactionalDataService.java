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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;

/**
 * Implementation of service for manipulation data which uses transactions.
 */
public class TransactionalDataService implements AbstractDataService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionalDataService.class);
    private TransactionManager tm;

    public TransactionalDataService(TransactionManager tm) {
        this.tm = tm;
    }

    @Override
    public void deleteContact(long contactId) throws DataException {
        logger.info("Delete contact with id: {}", contactId);
        Transaction transaction = tm.getTransaction();
        // JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        IAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        IContactDao contactDao = new JdbcContactDao(transaction);
        //  JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        AbstractFileService fileService = ServiceFactory.getInstance().getFileService();
        ArrayList<String> listOfFilesForDeleting = new ArrayList<>();
        Contact contact = null;
        try {
            contact = contactDao.getContactById(contactId);
            if (contact != null) {
                contactDao.delete(contactId);
                //delete files
                long photoId = contact.getPhoto();
                File photo = fileDao.getFileById(photoId);
                if (photo != null) {
                    if (StringUtils.isNotEmpty(photo.getStoredName()))
                        listOfFilesForDeleting.add(photo.getStoredName());
                    fileDao.delete(photoId);
                }
                ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);
                for (Attachment attachment : attachments) {
                    if (attachment != null) {
                        File file = fileDao.getFileById(attachment.getFile());
                        if (file != null) {
                            if (StringUtils.isNotEmpty(file.getStoredName()))
                                listOfFilesForDeleting.add(file.getStoredName());
                            fileDao.delete(attachment.getFile());
                        }
                    }
                }
                for (String name : listOfFilesForDeleting) {
                    fileService.deleteFile(name);
                }
            } else {
                throw new DataException("Contact with provided id was not found");
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            logger.error("Error deleting contact: {}", e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
    }

    @Override
    public void deleteContacts(ArrayList<Long> contactId) throws DataException {
        logger.info("Delete contacts with id's: {}", contactId);
        for (long id : contactId) {
            deleteContact(id);
        }
    }


    @Override
    public void saveNewContact(FullContactDTO fullContactDTO) throws DataException {
        logger.info("Save new contact");
        if (fullContactDTO == null) return;
        Transaction transaction = tm.getTransaction();
        IPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        IAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        IContactDao contactDao = new JdbcContactDao(transaction);
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        AbstractFileService fileService = ServiceFactory.getInstance().getFileService();
        ArrayList<String> savedFiles = new ArrayList<>();
        try {
            //save photo
            Contact contactToSave = fullContactDTO.getContact();
            if (contactToSave != null) {
                File photo = fullContactDTO.getPhoto();
                if (photo != null) {
                    if (StringUtils.isNotEmpty(photo.getStoredName())) {
                        savedFiles.add(photo.getStoredName());
                    }
                    long photoId = fileDao.save(photo);
                    //set photo id in contact and save it
                    contactToSave.setPhoto(photoId);
                }


                long contactId = contactDao.save(contactToSave);
                //set contact id in address object and save it
                Address addressToSave = fullContactDTO.getAddress();
                if (addressToSave != null) {
                    addressToSave.setContactId(contactId);
                    addressDao.save(addressToSave);
                }
                //set contact id in every phone object and save it
                ArrayList<Phone> phones = fullContactDTO.getPhones();
                if (phones != null) {
                    for (Phone phoneToSave : phones) {
                        if (phoneToSave != null) {
                            phoneToSave.setContact(contactId);
                            phoneDao.save(phoneToSave);
                        }
                    }
                }
                //save attachments with files
                ArrayList<FullAttachmentDTO> attachments = fullContactDTO.getAttachments();
                if (attachments != null) {
                    for (FullAttachmentDTO fullAttachmentToSave : attachments) {
                        if (fullAttachmentToSave != null) {
                            File fileToSave = fullAttachmentToSave.getFile();
                            if (fileToSave != null) {
                                savedFiles.add(fileToSave.getStoredName());
                                long fileId = fileDao.save(fileToSave);
                                Attachment attachmentToSave = fullAttachmentToSave.getAttachment();
                                if (addressToSave != null) {
                                    attachmentToSave.setContact(contactId);
                                    attachmentToSave.setFile(fileId);
                                    attachmentDao.save(attachmentToSave);
                                }
                            }
                        }
                    }
                }
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            logger.error("Error saving contact: {}", e.getCause().getMessage());
            transaction.rollbackTransaction();
            fileService.deleteFiles(savedFiles);
            throw new DataException(e.getMessage());
        }

    }

    @Override
    public void updateContact(FullContactDTO reconstructedContact, FullContactDTO contactToUpdate) throws DataException {
        logger.info("Update contact");
        if (reconstructedContact == null || contactToUpdate == null) return;
        Transaction transaction = tm.getTransaction();
        IPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        IAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        IContactDao contactDao = new JdbcContactDao(transaction);
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        AbstractFileService fileService = ServiceFactory.getInstance().getFileService();

        ArrayList<String> filesToDelete = new ArrayList<>();
        ArrayList<String> savedFiles = new ArrayList<>();
        try {
            if (contactToUpdate.getPhoto() != null && reconstructedContact.getPhoto() != null) {
                //if old file exists and new file was stored -> mark old file for deleting
                if (StringUtils.isNotEmpty(contactToUpdate.getPhoto().getStoredName()) && StringUtils.isNotEmpty(reconstructedContact.getPhoto().getStoredName()))
                    filesToDelete.add(contactToUpdate.getPhoto().getStoredName());
            }
            contactToUpdate.update(reconstructedContact);
            if (reconstructedContact.getPhoto() != null) {
                if (StringUtils.isNotEmpty(reconstructedContact.getPhoto().getStoredName())) {
                    fileDao.update(contactToUpdate.getPhoto());
                }
            }
            if (contactToUpdate.getContact() != null) {
                contactDao.update(contactToUpdate.getContact());
            }
            if (contactToUpdate.getAddress() != null) {
                addressDao.update(contactToUpdate.getAddress());
            }
            ArrayList<Phone> phonesToCreate = contactToUpdate.getNewPhones();
            if (phonesToCreate != null) {
                for (Phone phoneToCreate : phonesToCreate) {
                    if (phoneToCreate != null && contactToUpdate.getContact() != null) {
                        phoneToCreate.setContact(contactToUpdate.getContact().getContactId());
                        phoneDao.save(phoneToCreate);
                    }
                }
            }
            ArrayList<Phone> phonesToUpdate = contactToUpdate.getUpdatedPhones();
            if (phonesToUpdate != null) {
                for (Phone phoneToUpdate : phonesToUpdate) {
                    if (phoneToUpdate != null || contactToUpdate.getContact() != null) {
                        phoneToUpdate.setContact(contactToUpdate.getContact().getContactId());
                        phoneDao.update(phoneToUpdate);
                    }
                }
            }
            ArrayList<Phone> phonesToDelete = contactToUpdate.getDeletedPhones();
            if (phonesToDelete != null) {
                for (Phone phoneToDelete : phonesToDelete) {
                    if (phoneToDelete != null && contactToUpdate.getContact() != null) {
                        phoneToDelete.setContact(contactToUpdate.getContact().getContactId());
                        phoneDao.delete(phoneToDelete.getId());
                    }
                }
            }

            ArrayList<FullAttachmentDTO> fullAttachmentDTOsToUpdate = contactToUpdate.getUpdatedAttachments();
            if (fullAttachmentDTOsToUpdate != null) {
                for (FullAttachmentDTO fullAttachmentToUpdate : fullAttachmentDTOsToUpdate) {
                    if (fullAttachmentToUpdate != null) {
                        Attachment attachment = fullAttachmentToUpdate.getAttachment();
                        if (attachment != null) {
                            attachmentDao.update(attachment);
                        }
                    }
                }
            }
            ArrayList<FullAttachmentDTO> fullAttachmentDTOsToDelete = contactToUpdate.getDeletedAttachments();
            if (fullAttachmentDTOsToDelete != null) {
                for (FullAttachmentDTO fullAttachmentToDelete : fullAttachmentDTOsToDelete) {
                    if (fullAttachmentToDelete != null) {
                        if (fullAttachmentToDelete.getAttachment() != null) {
                            File file = fileDao.getFileByAttachmentId(fullAttachmentToDelete.getAttachment().getId());
                            if (file != null) {
                                if (StringUtils.isNotEmpty(file.getStoredName()))
                                    filesToDelete.add(file.getStoredName());
                                attachmentDao.delete(fullAttachmentToDelete.getAttachment().getId());
                                fileDao.delete(file.getId());
                            }
                        }
                    }
                }
            }
            ArrayList<FullAttachmentDTO> fullAttachmentDTOsToCreate = contactToUpdate.getNewAttachments();
            if (fullAttachmentDTOsToCreate != null) {
                for (FullAttachmentDTO fullAttachmentToCreate : fullAttachmentDTOsToCreate) {
                    if (fullAttachmentToCreate.getAttachment() != null && fullAttachmentToCreate.getFile() != null) {
                        File fileToSave = fullAttachmentToCreate.getFile();
                        if (fileToSave != null) {
                            long fileId = fileDao.save(fileToSave);
                            if (StringUtils.isNotBlank(fileToSave.getStoredName()))
                                savedFiles.add(fileToSave.getStoredName());
                            Attachment attachmentToSave = fullAttachmentToCreate.getAttachment();
                            if (attachmentToSave != null && contactToUpdate.getContact() != null) {
                                attachmentToSave.setContact(contactToUpdate.getContact().getContactId());
                                attachmentToSave.setFile(fileId);
                                attachmentDao.save(attachmentToSave);
                            }
                        }
                    }

                }
            }

            fileService.deleteFiles(filesToDelete);

            transaction.commitTransaction();
        } catch (DaoException e) {
            logger.error("Error updating contact: {}", e.getCause().getMessage());
            transaction.rollbackTransaction();
            fileService.deleteFiles(savedFiles);
            throw new DataException(e.getMessage());
        }
    }

    @Override
    public ArrayList<Contact> getContactsWithBirthday(Date date) throws DataException {
        logger.info("Fetch contacts with birth date: {}", date);
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = null;
        try {
            contacts = contactDao.getByBirthDate(date);
            transaction.commitTransaction();
        } catch (DaoException e) {
            logger.error("Error fetching contacts by birthday: {}", e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
        return contacts;
    }


    @Override
    public ArrayList<MainPageContactDTO> getSearchResultContactsDTOForPage(SearchDTO dto, int page, int count) throws DataException {
        logger.info("Fetch search result contacts: {} for page: {}, count: {}", dto, page, count);
        if (dto == null) throw new DataException("DTO object is null");
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        ArrayList<MainPageContactDTO> mainPageContactDTOs = new ArrayList<>();
        try {
            ArrayList<Contact> contacts = contactDao.findContactsByFieldsLimit(dto, page * count, count);
            if (contacts != null) {
                for (Contact contact : contacts) {
                    if (contact != null) {
                        File photo = fileDao.getFileById(contact.getPhoto());
                        Address address = addressDao.getAddressByContactId(contact.getContactId());
                        MainPageContactDTO mainPageContactDTO = new MainPageContactDTO(contact, address, photo);
                        mainPageContactDTOs.add(mainPageContactDTO);
                    }
                }
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            logger.error("Error fetching search result contacts: {}", e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
        return mainPageContactDTOs;
    }

    @Override
    public int getContactsSearchResultCount(SearchDTO dto) throws DataException {
        logger.info("Fetch search result contacts count: {}", dto);
        if (dto == null) throw new DataException("DTO object is null");
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        try {
            int count = contactDao.getContactsSearchResultCount(dto);
            transaction.commitTransaction();
            return count;
        } catch (DaoException e) {
            logger.error("Error fetching search result contacts count: {}", e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
    }

    @Override
    public Contact getContactById(long contactId) throws DataException {
        logger.info("Fetch contact with id: {}", contactId);
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        Contact contact = null;
        try {
            contact = contactDao.getContactById(contactId);
            transaction.commitTransaction();
        } catch (DaoException e) {
            logger.error("Error fetching contact by id: {}", e.getCause().getMessage());

            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
        return contact;
    }

    @Override
    public ArrayList<MainPageContactDTO> getMainPageContactDTO(int page, int count) throws DataException {
        logger.info("Fetch contact DTOs for page: {}, count: {}", page, count);
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        IAddressDao addressDao = new JdbcAddressDao(transaction);
        IFileDao fileDao = new JdbcFileDao(transaction);
        ArrayList<Contact> contacts = null;
        ArrayList<MainPageContactDTO> mainPageContactDTOs = new ArrayList<>();
        try {
            contacts = contactDao.getContactsLimit(page * count, count);
            if (contacts != null) {
                for (Contact contact : contacts) {
                    if (contact != null) {
                        File photo = fileDao.getFileById(contact.getPhoto());
                        Address address = addressDao.getAddressByContactId(contact.getContactId());
                        MainPageContactDTO mainPageContactDTO = new MainPageContactDTO(contact, address, photo);
                        mainPageContactDTOs.add(mainPageContactDTO);
                    }
                }
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            logger.error("Error fetching formatter page contact DTOs: {}", e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }

        return mainPageContactDTOs;
    }

    @Override
    public FullContactDTO getFullContactById(long contactId) throws DataException {
        logger.info("Fetch full contact DTO with id: {}", contactId);
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
            if (contact != null) {
                Address address = addressDao.getAddressByContactId(contactId);
                File photo = fileDao.getFileById(contact.getPhoto());
                ArrayList<Phone> phones = phoneDao.getAllForContact(contactId);

                ArrayList<FullAttachmentDTO> fullAttachmentDTOs = new ArrayList<>();
                ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);
                if (attachments != null) {
                    for (Attachment attachment : attachments) {
                        if (attachment != null) {
                            File file = fileDao.getFileById(attachment.getFile());
                            FullAttachmentDTO fullAttachmentDTO = new FullAttachmentDTO(attachment, file);
                            fullAttachmentDTOs.add(fullAttachmentDTO);
                        }
                    }
                }
                fullContactDTO = new FullContactDTO(contact, address, photo);
                fullContactDTO.setPhones(phones);
                fullContactDTO.setAttachments(fullAttachmentDTOs);
            } else {
                throw new DataException("Contact with provided id was not found");
            }
            transaction.commitTransaction();

        } catch (DaoException e) {
            logger.error("Error fetching full contact DTO: {}", e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }

        return fullContactDTO;
    }

    @Override
    public int getContactsCount() throws DataException {
        logger.info("Fetch contacts count");
        Transaction transaction = tm.getTransaction();
        IContactDao contactDao = new JdbcContactDao(transaction);
        int count = 0;
        try {
            count = contactDao.getContactsCount();
            transaction.commitTransaction();
        } catch (DaoException e) {
            logger.error("Error fetching contacts count: {}", e.getCause().getMessage());
            transaction.rollbackTransaction();
            throw new DataException(e.getMessage());
        }
        return count;
    }

}
