package com.itechart.web.service.request.processing.exception;

/**
 * Exception throws when file size in request exceeds maximum permitted value.
 */
public class FileSizeException extends Exception{
    public FileSizeException() {
    }

    public FileSizeException(String message) {
        super(message);
    }
}
