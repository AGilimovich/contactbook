package com.itechart.web.service.validation;

/**
 * Exception thrown when validation failed.
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
