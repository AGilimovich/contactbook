package com.itechart.web.service.validation;

/**
 * Created by Aleksandr on 01.04.2017.
 */
public class ValidationException extends Exception{
    public ValidationException() {
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message) {
        super(message);
    }
}
