package com.majornick.sql_parser.token;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SQLTokenizerTest {

    @Test
    public void SQLTokenizerSimpleQueryTest() {
        String query = "SELECT * FROM table;";
        SQLTokenizer tokenizer = new SQLTokenizer(query);
        List<Token> expected = Arrays.asList(new Token(TokenType.KEYWORD, "SELECT"),
                new Token(TokenType.OPERATOR, "*"),
                new Token(TokenType.KEYWORD, "FROM"),
                new Token(TokenType.IDENTIFIER, "table"),
                new Token(TokenType.OPERATOR, ";"));

        List<Token> actual = new ArrayList<>();
        while (tokenizer.hasNext()) {
            actual.add(tokenizer.nextToken());
        }
        assertEquals(expected, actual);
    }

}