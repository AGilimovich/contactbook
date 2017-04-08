package com.itechart.web.command;

import com.itechart.web.command.errors.ErrorDispatcher;
import com.itechart.web.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command executed on file request.
 */
public class DoSendFile implements Command {
    private static Logger logger = LoggerFactory.getLogger(DoSendFile.class);


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");
        byte[] file = ServiceFactory.getInstance().getFileService().getFile(request.getParameter("id"));
        if (file != null) {
            try {
                response.getOutputStream().write(file);
                response.getOutputStream().flush();
            } catch (IOException e) {
                logger.error("Error sending file: {}", e.getMessage());
            }
        }
        else {
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_NOT_FOUND);
        }
        return null;

    }
}
