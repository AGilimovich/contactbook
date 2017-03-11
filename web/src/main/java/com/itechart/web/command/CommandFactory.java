package com.itechart.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Factory class for which produces command objects.
 */
public class CommandFactory {
    private static String regex = "";
    private static Pattern pattern = Pattern.compile(regex);

    public static Command getCommand(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        System.out.println(path);

        if (path == null) return new ShowContactsCommand();
        switch (path) {
            case "/":
                return new ShowContactsCommand();
            case "/email":
                return new ShowEmailViewCommand();
            case "/search":
                return new ShowSearchViewCommand();
            case "contact/add":
                return new CreateContactCommand();
            case "contact/edit":
                return new EditContactCommand();
            case "contact/delete":
                return new DeleteContactCommand();
            default:
                throw new ServletException("no such path");
        }

    }
}
