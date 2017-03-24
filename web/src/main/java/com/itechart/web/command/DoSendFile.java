package com.itechart.web.command;

import com.itechart.web.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command for sending requested files.
 */
public class DoSendFile implements Command {


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        byte[] photo = ServiceFactory.getServiceFactory().getRequestProcessingService().processGetFileRequest(request);
        if (photo != null) {
            try {
                response.getOutputStream().write(photo);
                response.getOutputStream().flush();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "/resources/images/male.jpg";

    }
}
