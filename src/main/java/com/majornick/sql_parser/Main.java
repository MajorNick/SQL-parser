package com.majornick.sql_parser;

import com.majornick.sql_parser.exceptions.SQLKeywordExpectedException;
import com.majornick.sql_parser.parser.SQLParser;

public class Main {
    public static void main(String[] args) throws SQLKeywordExpectedException {
        SQLParser parser = new SQLParser("SELECT * FROM table;");
        System.out.println(parser.parse());
    }
}