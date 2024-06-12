package com.majornick.sql_parser.token;

import static com.majornick.sql_parser.SQLKeywords.KEYWORDS.KEYWORDS;

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
                    (Character.isLetterOrDigit(query.charAt(pos)) || query.charAt(pos) == '_')) {
                identifier.append(query.charAt(pos));
                pos++;
            }
            String idString = identifier.toString();
            TokenType type = TokenType.IDENTIFIER;
            if (isKeyword(idString)) {
                type = TokenType.KEYWORD;
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

    public boolean hasNext() {
        return nextToken != null;
    }

    private static boolean isKeyword(String word) {
        for (String keyword : KEYWORDS) {
            if (keyword.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isOperator(char c) {
        return "=<>!+-*/(),;".indexOf(c) != -1;
    }
}
