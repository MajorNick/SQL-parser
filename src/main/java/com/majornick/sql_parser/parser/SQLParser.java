package com.majornick.sql_parser.parser;

import com.majornick.sql_parser.SQLKeywords.Column;
import com.majornick.sql_parser.SQLKeywords.Join;
import com.majornick.sql_parser.SQLKeywords.Query;
import com.majornick.sql_parser.SQLKeywords.Source;
import com.majornick.sql_parser.SQLKeywords.enums.JoinType;
import com.majornick.sql_parser.exceptions.SQLKeywordExpectedException;
import com.majornick.sql_parser.token.SQLTokenizer;
import com.majornick.sql_parser.token.Token;
import com.majornick.sql_parser.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SQLParser {
    private final SQLTokenizer tokenizer;

    private final Stack<String> currentlyParsing = new Stack<>();
    private final Stack<List<Token>> currentlyParsingTokenSeq = new Stack<>();

    public SQLParser(String query) {
        tokenizer = new SQLTokenizer(query);
    }

    public Query parse() throws SQLKeywordExpectedException {
        Query query = new Query();
        expectKeyword("SELECT");
        currentlyParsing.push("SELECT");
        List<Token> list = new ArrayList<>();
        while (true) {
            if (!tokenizer.hasNext() || SQLTokenizer.isMainSQLKeyword(tokenizer.nextTokenValue())) {

                String tok = currentlyParsing.pop();
                List<Token> curKeywordTokens = currentlyParsingTokenSeq.pop();

                switch (tok) {
                    case "SELECT" -> parseSelectedColumns(query, curKeywordTokens);
                    case "FROM" -> parseSource(query, curKeywordTokens);
                    case "JOIN" -> parseJoin(query,curKeywordTokens);
                }
                if (!tokenizer.hasNext()) break;
                Token keyword = tokenizer.nextToken();

                if (JoinType.isJoinTypeKeyword(keyword.getValue())) {
                    expectKeyword("JOIN");
                    currentlyParsingTokenSeq.push(new ArrayList<>());
                    currentlyParsingTokenSeq.peek().add(keyword);
                    currentlyParsing.push("JOIN");
                }else{
                    currentlyParsing.push(keyword.getValue());
                }
            } else {
                if (currentlyParsing.size() != currentlyParsingTokenSeq.size()) {
                    currentlyParsingTokenSeq.push(new ArrayList<>());
                }
                currentlyParsingTokenSeq.peek().add(tokenizer.nextToken());
            }
        }
        return query;
    }

    private void parseSelectedColumns(Query query, List<Token> curKeywordTokens) {
        var column = Column.builder();
        int i = 0;
        int last = 0;
        if (curKeywordTokens.size() == 1) {
            query.addColumn(column.columnName("*").build());
            return;
        }
        curKeywordTokens.add(new Token(TokenType.OPERATOR, ","));
        while (i < curKeywordTokens.size()) {
            if (curKeywordTokens.get(i).getValue().equals(",")) {
                if (i - last == 3) {
                    column.columnName(curKeywordTokens.get(last).getValue())
                            .alias(curKeywordTokens.get(i - 1).getValue());
                    query.addColumn(column.build());
                } else {
                    column.columnName(curKeywordTokens.get(last).getValue())
                            .alias(null);
                    query.addColumn(column.build());
                }
                last = ++i;
            } else {
                i++;
            }
        }
    }

    private void parseSource(Query query, List<Token> curKeywordTokens) {
        var s = Source.builder();
        s.tableName(curKeywordTokens.get(0).getValue()).alias(null);
        if (curKeywordTokens.size() > 1 && !curKeywordTokens.get(1).getValue().equals(";")) {
            s.alias(curKeywordTokens.get(1).getValue());
        }
        query.addSource(s.build());
    }
    private void parseJoin(Query query, List<Token> curKeywordTokens) {
        var jb = Join.builder();

        int i =0;
        if(JoinType.isJoinTypeKeyword(curKeywordTokens.get(0).getValue())){
            jb.joinType(JoinType.valueOf(curKeywordTokens.get(0).getValue()));
            i=1;
        }else{
            jb.joinType(JoinType.INNER);
        }
        var s = Source.builder();
        s.tableName(curKeywordTokens.get(i++).getValue());
        if(curKeywordTokens.get(i).getType() == TokenType.IDENTIFIER){
            s.alias(curKeywordTokens.get(i).getValue());
            i++;
        }
        jb.toSource(s.build());
        i++; // ON
        var cb = new  StringBuilder();
        while(i<curKeywordTokens.size() && !";".equals(curKeywordTokens.get(i).getValue())){
            cb.append(curKeywordTokens.get(i++).getValue());
        }
        jb.condition(cb.toString());
        query.addJoin(jb.build());
    }


    private void expectKeyword(String keyword) throws SQLKeywordExpectedException {
        if (!tokenizer.hasNext() || !tokenizer.nextTokenValue().equalsIgnoreCase(keyword)) {
            throw new SQLKeywordExpectedException("Expected keyword " + keyword + " but found " + tokenizer.nextTokenValue());
        }
        tokenizer.nextToken();
    }
}
