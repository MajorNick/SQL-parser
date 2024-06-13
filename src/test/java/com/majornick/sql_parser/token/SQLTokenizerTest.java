package com.majornick.sql_parser.token;

import com.majornick.sql_parser.SQLKeywords.Column;
import com.majornick.sql_parser.SQLKeywords.Query;
import com.majornick.sql_parser.SQLKeywords.Source;
import com.majornick.sql_parser.parser.SQLParser;
import lombok.SneakyThrows;
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


    @Test
    public void SQLTokenizerQueryWithWhereTest() {
        String query = "SELECT * FROM table " +
                "WHERE A>B;";
        SQLTokenizer tokenizer = new SQLTokenizer(query);
        List<Token> expected = Arrays.asList(new Token(TokenType.KEYWORD, "SELECT"),
                new Token(TokenType.OPERATOR, "*"),
                new Token(TokenType.KEYWORD, "FROM"),
                new Token(TokenType.IDENTIFIER, "table"),
                new Token(TokenType.KEYWORD, "WHERE"),
                new Token(TokenType.IDENTIFIER, "A"),
                new Token(TokenType.OPERATOR, ">"),
                new Token(TokenType.IDENTIFIER, "B"),
                new Token(TokenType.OPERATOR, ";"));
        List<Token> actual = new ArrayList<>();
        while (tokenizer.hasNext()) {
            actual.add(tokenizer.nextToken());
        }
        assertEquals(expected, actual);
    }

    @Test
    @SneakyThrows
    public void parseSimpleQueryTest() {
        String q = "SELECT * FROM table;";
        Query query = new Query();
        query.addSource(new Source("table",null));
        query.addColumn(new Column("*",null));
        SQLParser parser = new SQLParser(q);
        assertEquals(query,parser.parse());
    }

    @Test
    @SneakyThrows
    public void parseSimpleQueryWithAliasesTest() {
        String q = "SELECT col1 AS c1, col2 AS c2 FROM table t;";
        Query query = new Query();
        query.addSource(new Source("table","t"));
        query.addColumn(new Column("col1","c1"));
        query.addColumn(new Column("col2","c2"));
        SQLParser parser = new SQLParser(q);
        assertEquals(query,parser.parse());
    }

}