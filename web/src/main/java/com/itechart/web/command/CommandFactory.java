package com.itechart.web.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for which produces command objects.
 */
public class CommandFactory {
    private static Logger logger = LoggerFactory.getLogger(CommandFactory.class);
    private static Map<String, Class<? extends Command>> commands = new HashMap();

    static {
        commands.put("/", ShowContactsView.class);
        commands.put("/email", ShowEmailView.class);
        commands.put("/send", DoSendEmail.class);
        commands.put("/search", ShowSearchView.class);
        commands.put("/add", ShowContactAddView.class);
        commands.put("/edit", ShowContactEditView.class);
        commands.put("/save", DoCreateContact.class);
        commands.put("/update", DoUpdateContact.class);
        commands.put("/find", DoSearch.class);
        commands.put("/delete", DoDeleteContact.class);
        commands.put("/file", DoSendFile.class);
        commands.put("/image", DoSendImage.class);
    }


    public static Command getCommand(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        Class<? extends Command> commandClass = commands.get(path);
        if (commandClass != null)
            try {
                logger.info("Get command for path: {}", request.getRequestURI());
                return commandClass.newInstance();
            } catch (InstantiationException e) {
                logger.error("Error instantiating command object", e.getMessage());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                logger.error("Error instantiating command object", e.getMessage());
                e.printStackTrace();
            }

        return null;
    }
}
