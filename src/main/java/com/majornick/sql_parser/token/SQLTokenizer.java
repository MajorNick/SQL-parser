package com.majornick.sql_parser.token;

import com.majornick.sql_parser.exceptions.NextTokenNotExistsException;

import static com.majornick.sql_parser.SQLKeywords.KEYWORDS.*;

public class SQLTokenizer {

    private final String query;
    private int pos = 0;

    private Token nextToken;

    public SQLTokenizer(String query) {
        this.query = query.trim();
        advance();
    }

    private void advance() {
        if (pos >= query.length()) {
            nextToken = null;
            return;
        }

        char c = query.charAt(pos);

        while (Character.isWhitespace(c)) {
            pos++;
            if (pos >= query.length()) {
                nextToken = null;
                return;
            }
            c = query.charAt(pos);
        }

        if (Character.isLetter(c) || c == '_') {
            StringBuilder identifier = new StringBuilder();
            while (pos < query.length() &&
                    (Character.isLetterOrDigit(query.charAt(pos)) || query.charAt(pos) == '_' || query.charAt(pos) == '.')) {
                identifier.append(query.charAt(pos));
                pos++;
            }
            String idString = identifier.toString();
            TokenType type = TokenType.IDENTIFIER;
            if (isSQLKeyword(idString)) {
                type = TokenType.KEYWORD;
            }
            if(isLogicalOperator(idString)){
                type = TokenType.LOGICAL_OPERATOR;
            }
            nextToken = new Token(type, idString);
        } else if (Character.isDigit(c)) {
            StringBuilder number = new StringBuilder();
            while (pos < query.length() && Character.isDigit(query.charAt(pos))) {
                number.append(query.charAt(pos));
                pos++;
            }
            nextToken = new Token(TokenType.NUMBER, number.toString());
        } else if (c == '\'' || c == '"') {
            StringBuilder literal = new StringBuilder();
            literal.append(c);
            pos++;
            while (pos < query.length() && query.charAt(pos) != c) {
                literal.append(query.charAt(pos));
                pos++;
            }
            if (pos < query.length()) {
                literal.append(c);
                pos++;
            }
            nextToken = new Token(TokenType.STRING, literal.toString());
        } else if (isOperator(c)) {
            nextToken = new Token(TokenType.OPERATOR, Character.toString(c));
            pos++;
        } else {
            nextToken = new Token(TokenType.UNKNOWN, Character.toString(c));
            pos++;
        }
    }

    public Token nextToken() {
        Token currentToken = nextToken;
        advance();
        return currentToken;
    }

    public TokenType nextTokenType() {
        if (!hasNext()) {
            throw new NextTokenNotExistsException();
        }
        return nextToken.getType();
    }

    public String nextTokenValue() {
        if (!hasNext()) {
            throw new NextTokenNotExistsException();
        }
        return nextToken.getValue();
    }

    public boolean hasNext() {
        return nextToken != null;
    }


    private static boolean isSQLKeyword(String word) {
        for (String keyword : KEYWORDS) {
            if (keyword.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMainSQLKeyword(String word) {
        for (String keyword : MAIN_KEYWORDS) {
            if (keyword.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isLogicalOperator(String s){
        return "NULL".equalsIgnoreCase(s) || "OR".equalsIgnoreCase(s);
    }


    private static boolean isOperator(char c) {
        return "=<>!+-*/(),;z".indexOf(c) != -1;
    }
    public static boolean isComparisonOperator(String c){
        for (String keyword : COMPARISON_OPERATORS) {
            if (keyword.equalsIgnoreCase(c)) {
                return true;
            }
        }
        return false;
    }
}
