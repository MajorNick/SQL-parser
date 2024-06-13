package com.majornick.sql_parser.exceptions;

public class NextTokenNotExistsException extends RuntimeException {
    public NextTokenNotExistsException(String message) {
        super(message);
    }

    public NextTokenNotExistsException() {
    }
}
