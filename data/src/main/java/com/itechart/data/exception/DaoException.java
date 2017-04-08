package com.itechart.data.exception;

/**
 * Exception generated when exception was generated in DAO level methods.
 */
public class DaoException extends Exception {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
