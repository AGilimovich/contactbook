package com.itechart.web.command.dispatcher;

import com.itechart.web.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aleksandr on 03.04.2017.
 */
public class ErrorDispatcher {
    private static Logger logger = LoggerFactory.getLogger(ErrorDispatcher.class);

    public static void dispatchError(HttpServletResponse response, int code){
        logger.info("Dispatch error page, code: {}", code);
        try {
            response.sendError(code);
        } catch (IOException e) {
            logger.error("Error dispatching error page: {}", e.getMessage());
        }
    }

}
