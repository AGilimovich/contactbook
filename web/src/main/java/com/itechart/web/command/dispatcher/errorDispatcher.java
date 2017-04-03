package com.itechart.web.command.dispatcher;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aleksandr on 03.04.2017.
 */
public class ErrorDispatcher {

    public static void dispatchError(HttpServletResponse response, int code){
        try {
            response.sendError(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
