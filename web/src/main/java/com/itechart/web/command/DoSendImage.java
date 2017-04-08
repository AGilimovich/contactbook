package com.itechart.web.command;

import com.itechart.web.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command executed on image request.
 */
public class DoSendImage implements Command {
    private static Logger logger = LoggerFactory.getLogger(DoSearch.class);

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");

        byte[] image = ServiceFactory.getInstance().getFileService().getFile(request.getParameter("id"));
        if (image != null) {
            try {
                response.getOutputStream().write(image);
                response.getOutputStream().flush();
                return null;
            } catch (IOException e) {
                logger.error("Error sending file: {}", e.getMessage());
            }
        }
        return "/resources/images/male.jpg";

    }
}
