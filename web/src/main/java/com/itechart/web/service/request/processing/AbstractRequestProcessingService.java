package com.itechart.web.service.request.processing;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.web.service.email.Email;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Aleksandr on 27.03.2017.
 */
public interface AbstractRequestProcessingService {
    FullContactDTO processMultipartContactRequest(HttpServletRequest request) throws FileSizeException;

    String[] processDeleteContactRequest(HttpServletRequest request);


    long processFetchSingleContactRequest(HttpServletRequest request);

    SearchDTO processSearchContactsRequest(HttpServletRequest request);

    Email processSendEmailRequest(HttpServletRequest request);


    String[] processShowEmailViewRequest(HttpServletRequest request);
}
