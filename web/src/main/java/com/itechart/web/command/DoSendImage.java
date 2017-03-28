package com.itechart.web.command;

import com.itechart.web.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aleksandr on 28.03.2017.
 */
public class DoSendImage implements Command {
    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        byte[] image = ServiceFactory.getServiceFactory().getFileService().getFile(request.getParameter("id"));
        if (image != null) {
            try {
                response.getOutputStream().write(image);
                response.getOutputStream().flush();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "/resources/images/male.jpg";

    }
}
