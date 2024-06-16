package com.majornick.sql_parser.SQLKeywords;

public interface KEYWORDS {
    String[] KEYWORDS = {"SELECT", "FROM", "WHERE", "JOIN",
            "ON", "AS", "ORDER", "BY", "GROUP", "HAVING","NULL",
            "LEFT", "RIGHT","INNER", "OUTER", "FULL", "CROSS"};

    String[] MAIN_KEYWORDS = {
        "SELECT", "WHERE","JOIN","ORDER","GROUP","HAVING","FROM",
            "LEFT", "RIGHT","INNER", "OUTER", "FULL", "CROSS"
    };

    String[] COMPARISON_OPERATORS = {
            "=", "<>", "!=", ">", "<", ">=", "<="
    };





}
