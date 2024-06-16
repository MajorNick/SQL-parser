package com.majornick.sql_parser.token;

public enum TokenType {
    KEYWORD,
    IDENTIFIER,
    STRING,
    NUMBER,
    OPERATOR,
    LOGICAL_OPERATOR,
    UNKNOWN,
    END_OF_SQL_KEYWORD_STATEMENT;

}
