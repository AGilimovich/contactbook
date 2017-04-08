package com.itechart.web.service.request.processing;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.web.service.email.Email;
import com.itechart.web.service.request.processing.exception.FileSizeException;
import com.itechart.web.service.validation.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Interface of service class for processing requests.
 */
public interface AbstractRequestProcessingService {

    FullContactDTO processMultipartContactRequest(HttpServletRequest request) throws FileSizeException, ValidationException;

    ArrayList<Long> processDeleteContactRequest(HttpServletRequest request) throws ValidationException;

    long processFetchSingleContactRequest(HttpServletRequest request) throws ValidationException;

    SearchDTO processSearchContactsRequest(HttpServletRequest request) throws ValidationException;

    ArrayList<Email> processSendEmailRequest(HttpServletRequest request) throws ValidationException;

    ArrayList<Long> processShowEmailViewRequest(HttpServletRequest request) throws ValidationException;

    int processChangeContactsCountRequest(HttpServletRequest request) throws ValidationException;
}
