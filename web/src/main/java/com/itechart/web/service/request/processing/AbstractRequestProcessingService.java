package com.itechart.web.service.request.processing;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.web.service.email.Email;
import com.itechart.web.service.validation.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 27.03.2017.
 */
public interface AbstractRequestProcessingService {
    FullContactDTO processMultipartContactRequest(HttpServletRequest request) throws FileSizeException, ValidationException;

    String[] processDeleteContactRequest(HttpServletRequest request);


    long processFetchSingleContactRequest(HttpServletRequest request);

    String processFetchContactsRequest(HttpServletRequest request);

    SearchDTO processSearchContactsRequest(HttpServletRequest request);

    Email processSendEmailRequest(HttpServletRequest request);


    String[] processShowEmailViewRequest(HttpServletRequest request);
}
