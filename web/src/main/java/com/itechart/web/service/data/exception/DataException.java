package com.itechart.web.service.data.exception;

/**
 * Exception thrown if error on data service layer occur.
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
