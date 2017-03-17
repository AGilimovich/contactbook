package com.itechart.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Command for sending requested files.
 */
public class DoSendFile implements Command {


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ResourceBundle properties = ResourceBundle.getBundle("application");
        String FILE_PATH = properties.getString("FILE_PATH");

        String photoId = request.getParameter("id");
        if (!photoId.isEmpty()) {
            Path path = Paths.get(FILE_PATH + "\\" + photoId);
            byte[] photo = null;
            try {
                photo = Files.readAllBytes(path);
                response.setContentType("image/jpeg");
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
