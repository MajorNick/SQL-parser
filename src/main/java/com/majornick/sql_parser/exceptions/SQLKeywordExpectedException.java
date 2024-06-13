package com.majornick.sql_parser.exceptions;

public class SQLKeywordExpectedException extends Exception {
    public SQLKeywordExpectedException(String message) {
        super(message);
    }

    public SQLKeywordExpectedException() {
    }
}
