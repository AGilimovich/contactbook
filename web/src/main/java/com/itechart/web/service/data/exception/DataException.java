package com.itechart.web.service.data.exception;

/**
 * Created by Aleksandr on 03.04.2017.
 */
public class DataException extends Exception {
    public DataException() {
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}
