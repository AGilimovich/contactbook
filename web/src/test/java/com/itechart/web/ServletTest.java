package com.itechart.web;

import com.itechart.web.controller.FrontCtrl;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Aleksandr on 04.04.2017.
 */
public class ServletTest {
    private FrontCtrl servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void setUp() {
        servlet = new FrontCtrl();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    public void correctUsernameInRequest() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(response).setStatus(404);
    }


}
