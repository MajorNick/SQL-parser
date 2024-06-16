package com.majornick.sql_parser.token;

import com.majornick.sql_parser.SQLKeywords.Column;
import com.majornick.sql_parser.SQLKeywords.Join;
import com.majornick.sql_parser.SQLKeywords.Query;
import com.majornick.sql_parser.SQLKeywords.Source;
import com.majornick.sql_parser.SQLKeywords.enums.JoinType;
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
        String q = "SELECT * FROM table";
        Query query = new Query();
        query.addSource(new Source("table",null));
        query.addColumn(new Column("*",null));
        SQLParser parser = new SQLParser(q);
        assertEquals(query,parser.parse());
    }

    @Test
    @SneakyThrows
    public void parseSimpleQueryWithAliasesTest() {
        String q = "SELECT col1 AS c1, col2 AS c2 FROM table t";
        Query query = new Query();
        query.addSource(new Source("table","t"));
        query.addColumn(new Column("col1","c1"));
        query.addColumn(new Column("col2","c2"));
        SQLParser parser = new SQLParser(q);
        assertEquals(query,parser.parse());
    }
    @Test
    @SneakyThrows
    public void parseSimpleQueryWithJoinTest() {
        String q = "SELECT col1 AS c1, col2 AS c2 FROM table1 " +
                "JOIN table2  ON table1.id = table2.id;";
        Query query = new Query();
        query.addSource(new Source("table1",null));
        query.addColumn(new Column("col1","c1"));
        query.addColumn(new Column("col2","c2"));
        query.addJoin(new Join(new Source("table2",null), JoinType.INNER,"table1.id=table2.id"));
        SQLParser parser = new SQLParser(q);
        assertEquals(query,parser.parse());
    }

    @Test
    @SneakyThrows
    public void parseQueryWithJoinTest() {
        String q = "SELECT col1 AS c1, col2 AS c2 FROM table1 t1 " +
                "LEFT JOIN table2 t2 ON t1.id = t2.id;";
        Query query = new Query();
        query.addSource(new Source("table1","t1"));
        query.addColumn(new Column("col1","c1"));
        query.addColumn(new Column("col2","c2"));
        query.addJoin(new Join(new Source("table2","t2"), JoinType.LEFT,"t1.id=t2.id"));
        SQLParser parser = new SQLParser(q);
        assertEquals(query,parser.parse());
    }

    @Test
    @SneakyThrows
    public void parseQueryWith2JoinTest() {
        String q = "SELECT col1 AS c1, col2 AS c2 FROM table1 t1 " +
                "LEFT JOIN table2 t2 ON t1.id = t2.id " +
                "RIGHT JOIN table3 t3 ON t3.id = t2.id";
        Query query = new Query();
        query.addSource(new Source("table1","t1"));
        query.addColumn(new Column("col1","c1"));
        query.addColumn(new Column("col2","c2"));
        query.addJoin(new Join(new Source("table2","t2"), JoinType.LEFT,"t1.id=t2.id"));
        query.addJoin(new Join(new Source("table3","t3"), JoinType.RIGHT,"t3.id=t2.id"));
        SQLParser parser = new SQLParser(q);
        assertEquals(query,parser.parse());
    }

    @Test
    @SneakyThrows
    public void parseSimpleQueryWithWhereClauseTest() {
        String q = "SELECT col1 AS c1, col2 AS c2 FROM table t " +
                "WHERE a>5;";
        Query query = new Query();
        query.addSource(new Source("table","t"));
        query.addColumn(new Column("col1","c1"));
        query.addColumn(new Column("col2","c2"));
        SQLParser parser = new SQLParser(q);
        assertEquals(query,parser.parse());
    }
}