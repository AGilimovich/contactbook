package com.itechart.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for which produces command objects.
 */
public class CommandFactory {

    private static Map<String, Object> commands = new HashMap();

    static {
        commands.put("/", ShowContacts.class);
        commands.put("/email", ShowEmail.class);
        commands.put("/send", DoSendEmail.class);
        commands.put("/search", ShowSearch.class);
        commands.put("/add", ShowContactAdd.class);
        commands.put("/edit", ShowContactEdit.class);
        commands.put("/save", DoCreateContact.class);
        commands.put("/update", DoUpdateContact.class);
        commands.put("/find", DoSearch.class);
        commands.put("/delete", DoDeleteContact.class);
        commands.put("/file", DoSendFile.class);
    }


    public static Command getCommand(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        Class<Command> commandClass = (Class<Command>) commands.get(path);
        if (commandClass != null)
            try {
                return commandClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        return null;
    }
}
