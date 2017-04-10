package com.itechart.web.service.request.processing;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.File;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.email.Email;
import com.itechart.web.service.email.EmailAddressesParser;
import com.itechart.web.service.files.AbstractFileService;
import com.itechart.web.service.request.processing.builder.FullContactDTOBuilder;
import com.itechart.web.service.request.processing.builder.SearchDTOBuilder;
import com.itechart.web.service.request.processing.exception.FileSizeException;
import com.itechart.web.service.validation.AbstractValidationService;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Implementation of service class for processing requests.
 */
public class RequestProcessingService implements AbstractRequestProcessingService {
    private Logger logger = LoggerFactory.getLogger(RequestProcessingService.class);

    /**
     * Processes multipart requests to create or update contact.
     *
     * @param request received from client.
     * @return constructed from request contact DTO.
     * @throws FileSizeException   when size of one of files in request exceeds maximum permitted value
     * @throws ValidationException when one of fields has invalid value.
     */
    @Override
    public FullContactDTO processMultipartContactRequest(HttpServletRequest request) throws FileSizeException, ValidationException {
        logger.info("Processing multipart request");
        AbstractFileService fileService = ServiceFactory.getInstance().getFileService();
        MultipartRequestHandler handler = new MultipartRequestHandler();
        handler.handle(request);
        //get map of form field names and corresponding values
        Map<String, String> formFields = handler.getFormFields();
        //get map of field names and corresponding file parts
        Map<String, FileItem> fileParts = handler.getFileParts();

        FullContactDTOBuilder builder = new FullContactDTOBuilder();
        try {
            builder.build(formFields, fileParts);
        } catch (ValidationException e) {
            Collection<File> storedFiles = builder.getStoredFiles();
            fileService.deleteFiles(storedFiles);
            throw e;
        }
        return builder.getFullContact();
    }

    /**
     * Processes requests to delete contacts.
     *
     * @param request received from client.
     * @return list of contacts id's deleting of which was requested.
     * @throws ValidationException when one of received in request id's has illegal value.
     */
    @Override
    public ArrayList<Long> processDeleteContactRequest(HttpServletRequest request) throws ValidationException {
        String[] idArrayParam = request.getParameterValues("isSelected");
        if (idArrayParam != null)
            logger.info("Processing request:  delete contacts with id's: {}", Arrays.toString(idArrayParam));
        else logger.info("Processing request:  delete contacts, no selected contacts");
        AbstractValidationService validationService = ServiceFactory.getInstance().getValidationService();
        ArrayList<Long> selectedIdList = new ArrayList<>();
        if (idArrayParam != null) {
            for (String id : idArrayParam) {
                if (validationService.validateId(id)) {
                    try {
                        selectedIdList.add(Long.valueOf(id));
                    } catch (Exception e) {
                        throw new ValidationException(StringUtils.join(new Object[]{"Illegal id", id}, ": "), e);
                    }
                } else {
                    throw new ValidationException(StringUtils.join(new Object[]{"Illegal id", id}, ": "));
                }
            }
        }
        return selectedIdList;
    }

    /**
     * Processes requests to fetch one contact.
     *
     * @param request received from client.
     * @return id of requested contact.
     * @throws ValidationException when received in request id has illegal value.
     */
    @Override
    public long processFetchSingleContactRequest(HttpServletRequest request) throws ValidationException {
        String idParam = request.getParameter("id");
        logger.info("Processing request: fetch contact with id: {}", idParam);
        AbstractValidationService validationService = ServiceFactory.getInstance().getValidationService();
        if (StringUtils.isNotEmpty(idParam)) {
            if (validationService.validateId(idParam)) {
                try {
                    return Long.valueOf(idParam);
                } catch (Exception e) {
                    throw new ValidationException(StringUtils.join(new Object[]{"Illegal id", idParam}, ": "), e);
                }
            }
        }
        throw new ValidationException("Illegal id");
    }

    /**
     * Processes requests to search contacts and fetch found ones.
     *
     * @param request received from client.
     * @return contact DTO corresponding to the search parameters.
     * @throws ValidationException when one of fields in request has illegal value.
     */
    @Override
    public SearchDTO processSearchContactsRequest(HttpServletRequest request) throws ValidationException {
        logger.info("Processing request: search for contacts");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("surname", request.getParameter("surname"));
        parameters.put("name", request.getParameter("name"));
        parameters.put("patronymic", request.getParameter("patronymic"));
        parameters.put("gender", request.getParameter("gender"));
        parameters.put("familyStatus", request.getParameter("familyStatus"));
        parameters.put("fromDate", request.getParameter("fromDate"));
        parameters.put("toDate", request.getParameter("toDate"));
        parameters.put("citizenship", request.getParameter("citizenship"));
        parameters.put("country", request.getParameter("country"));
        parameters.put("city", request.getParameter("city"));
        parameters.put("street", request.getParameter("street"));
        parameters.put("house", request.getParameter("house"));
        parameters.put("apartment", request.getParameter("apartment"));
        parameters.put("zipCode", request.getParameter("zipCode"));
        SearchDTO dto = (new SearchDTOBuilder()).buildSearchDTO(parameters);
        return dto;
    }

    /**
     * Processes requests to send emails.
     *
     * @param request received from client.
     * @return Email to be sent.
     * @throws ValidationException when body of email is empty.
     */
    @Override
    public Email processSendEmailRequest(HttpServletRequest request) throws ValidationException {
        String subject = request.getParameter("subject");
        String body = request.getParameter("email-body");
        String template = request.getParameter("template");
        logger.info("Processing request: send email with subject: {}, template:{}, email-body: {}", subject, template, body);
        if (StringUtils.isEmpty(body)) {
            throw new ValidationException("Email body is empty");
        }
        return new Email(subject, template, body);

    }

    /**
     * Processes request to show email sending view.
     *
     * @param request received from client.
     * @return list of contacts id's to whom emails are sent.
     * @throws ValidationException if one of received contact's id's has illegal value.
     */
    @Override
    public ArrayList<Long> processShowEmailViewRequest(HttpServletRequest request) throws ValidationException {
        AbstractValidationService validationService = ServiceFactory.getInstance().getValidationService();
        String[] selectedIdArrayParam = request.getParameterValues("isSelected");
        if (selectedIdArrayParam != null)
            logger.info("Processing request: show email view for contacts with id's: {}", Arrays.toString(selectedIdArrayParam));
        else logger.info("Processing request: show email view, no selected contacts ");
        ArrayList<Long> selectedIdList = new ArrayList<>();
        if (selectedIdArrayParam != null) {
            for (String id : selectedIdArrayParam) {
                if (validationService.validateId(id)) {
                    try {
                        selectedIdList.add(Long.valueOf(id));
                    } catch (Exception e) {
                        throw new ValidationException(StringUtils.join(new Object[]{"Illegal id", id}, ": "), e);
                    }
                } else {
                    throw new ValidationException(StringUtils.join(new Object[]{"Illegal id", id}, ": "));
                }
            }
        }
        return selectedIdList;
    }

    /**
     * Process requests to change the count of displauing on main page contacts.
     *
     * @param request received from client.
     * @return count of contacts to display.
     * @throws ValidationException if received count has illegal value.
     */
    @Override
    public int processChangeContactsCountRequest(HttpServletRequest request) throws ValidationException {
        String contactsCountParam = request.getParameter("contactsOnPage");
        logger.info("Processing request:  change contacts count to value: {}", contactsCountParam);
        if (StringUtils.isNotBlank(contactsCountParam)) {
            try {
                int contactsCount = Integer.valueOf(contactsCountParam);
                return contactsCount;
            } catch (Exception e) {
                throw new ValidationException("Illegal request parameter", e);
            }
        } else {
            throw new ValidationException("Illegalrequest parameter");
        }

    }


}
