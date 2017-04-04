package com.itechart.web.service.request.processing;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.web.properties.PropertiesManager;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.email.Email;
import com.itechart.web.service.email.EmailAddressesParser;
import com.itechart.web.service.files.AbstractFileService;
import com.itechart.web.service.request.processing.builder.FullContactDTOBuilder;
import com.itechart.web.service.request.processing.builder.SearchDTOBuilder;
import com.itechart.web.service.request.processing.exception.FileSizeException;
import com.itechart.web.service.validation.AbstractValidationService;
import com.itechart.web.service.validation.ValidationException;
import com.itechart.web.service.validation.ValidationService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Service layer. Process different requests.
 */
public class RequestProcessingService implements AbstractRequestProcessingService {
    private Logger logger = LoggerFactory.getLogger(RequestProcessingService.class);

    @Override
    public FullContactDTO processMultipartContactRequest(HttpServletRequest request) throws FileSizeException, ValidationException {
        logger.info("Processing multipart request");
        AbstractFileService fileService = ServiceFactory.getServiceFactory().getFileService();
        MultipartRequestHandler handler = new MultipartRequestHandler();
        handler.handle(request);
        //get map of form field names and corresponding values
        Map<String, String> formFields = handler.getFormFields();
        //get map of field names and corresponding file parts
        Map<String, FileItem> fileParts = handler.getFileParts();

        FilePartWriter writer = new FilePartWriter(PropertiesManager.FILE_PATH());
        //store file parts and get map of field names and file names
        Map<String, String> storedFiles = writer.writeFileParts(fileParts);

        FullContactDTOBuilder builder = new FullContactDTOBuilder();
        try {
            builder.build(formFields, storedFiles);
        } catch (ValidationException e) {
            fileService.deleteFiles(storedFiles.values());
            throw e;
        }
        return builder.getFullContact();
    }

    @Override
    public ArrayList<Long> processDeleteContactRequest(HttpServletRequest request) throws ValidationException {
        String[] idArrayParam = request.getParameterValues("isSelected");
        if (idArrayParam != null)
            logger.info("Processing request:  delete contacts with id's: {}", Arrays.toString(idArrayParam));
        else logger.info("Processing request:  delete contacts, no selected contacts");
        AbstractValidationService validationService = ServiceFactory.getServiceFactory().getValidationService();
        ArrayList<Long> selectedIdList = new ArrayList<>();
        if (idArrayParam != null) {
            for (String id : idArrayParam) {
                if (validationService.validateId(id)) {
                    selectedIdList.add(Long.valueOf(id));
                } else {
                    throw new ValidationException(StringUtils.join(new Object[]{"Illegal id", id}, ": "));
                }
            }
        }
        return selectedIdList;
    }

    @Override
    public long processFetchSingleContactRequest(HttpServletRequest request) throws ValidationException {
        String idParam = request.getParameter("id");
        logger.info("Processing request: fetch contact with id: {}", idParam);
        AbstractValidationService validationService = ServiceFactory.getServiceFactory().getValidationService();
        if (StringUtils.isNotEmpty(idParam)) {
            if (validationService.validateId(idParam)) {
                return Long.valueOf(idParam);
            }
        }
        throw new ValidationException("Illegal id");
    }


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

    @Override
    public ArrayList<Email> processSendEmailRequest(HttpServletRequest request) throws ValidationException {
        AbstractValidationService validationService = ServiceFactory.getServiceFactory().getValidationService();
        String emailAddressesString = request.getParameter("emailAddresses");
        String subject = request.getParameter("subject");
        String body = request.getParameter("email-body");
        logger.info("Processing request: send email with email addresses: {}, subject: {},email-body: {}", emailAddressesString, subject, body);

        ArrayList<String> emailAddresses = new EmailAddressesParser().getEmailAddresses(emailAddressesString);

        ArrayList<Email> emails = new ArrayList<>();
        if (emailAddresses != null) {
            for (String emailAddress : emailAddresses) {
                if (validationService.validateEmail(emailAddress)) {
                    emails.add(new Email(emailAddress, subject, body));
                }
            }
        } else {
            throw new ValidationException("List of emails is empty");
        }
        return emails;

    }

    @Override
    public ArrayList<Long> processShowEmailViewRequest(HttpServletRequest request) throws ValidationException {
        AbstractValidationService validationService = ServiceFactory.getServiceFactory().getValidationService();
        String[] selectedIdArrayParam = request.getParameterValues("isSelected");
        if (selectedIdArrayParam != null)
            logger.info("Processing request: show email view for contacts with id's: {}", Arrays.toString(selectedIdArrayParam));
        else logger.info("Processing request: show email view, no selected contacts ");
        ArrayList<Long> selectedIdList = new ArrayList<>();
        if (selectedIdArrayParam != null) {
            for (String id : selectedIdArrayParam) {
                if (validationService.validateId(id)) {
                    selectedIdList.add(Long.valueOf(id));
                } else {
                    throw new ValidationException(StringUtils.join(new Object[]{"Illegal id", id}, ": "));
                }
            }
        }
        return selectedIdList;
    }


}
