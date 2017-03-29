package com.itechart.data.exception;

/**
 * Created by Aleksandr on 29.03.2017.
 */
public class DaoException extends Exception {

       public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
