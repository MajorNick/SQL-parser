package com.majornick.sql_parser.parser;

import com.majornick.sql_parser.SQLKeywords.Column;
import com.majornick.sql_parser.SQLKeywords.Query;
import com.majornick.sql_parser.SQLKeywords.Source;
import com.majornick.sql_parser.exceptions.ParseException;
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
        while(true){

            if (!tokenizer.hasNext() || SQLTokenizer.isMainSQLKeyword(tokenizer.nextTokenValue())){
                String tok = currentlyParsing.pop();

                List<Token> curKeywordToken = currentlyParsingTokenSeq.pop();
                switch(tok){
                    case "SELECT" -> parseSelectedColumns(query,curKeywordToken);
                    case "FROM" -> parseSource(query,curKeywordToken);
                }
                if(!tokenizer.hasNext()) break;
                currentlyParsing.push(tokenizer.nextToken().getValue());
            }else{
                if(currentlyParsing.size()!= currentlyParsingTokenSeq.size()){
                    currentlyParsingTokenSeq.push(new ArrayList<>());
                }
                var tok = tokenizer.nextToken();
                currentlyParsingTokenSeq.peek().add(tok);
            }
        }
        return query;
    }

    private void parseSelectedColumns(Query query, List<Token> curKeywordTokens) {
        var column = Column.builder();
        int i = 0;
        int last = 0;
        if(curKeywordTokens.size() == 1){
            query.addColumn(column.columnName("*").build());
            return;
        }
        curKeywordTokens.add(new Token(TokenType.OPERATOR,","));

        while(i<curKeywordTokens.size()){
            if(curKeywordTokens.get(i).getValue().equals(",") ){
                if(i-last==3){
                    column.columnName(curKeywordTokens.get(last).getValue())
                            .alias(curKeywordTokens.get(i-1).getValue());
                    query.addColumn(column.build());
                }else{
                    column.columnName(curKeywordTokens.get(last).getValue())
                            .alias(null);
                    query.addColumn(column.build());
                }
                last = ++i;
            }else{
                i++;
            }
        }
    }

    private void parseSource(Query query, List<Token> curKeywordTokens) {
        var s = Source.builder();
        s.tableName(curKeywordTokens.get(0).getValue()).alias(null);
        if(curKeywordTokens.size()>1 && !curKeywordTokens.get(1).getValue().equals(";")){
            s.alias(curKeywordTokens.get(1).getValue());
        }
        query.addSource(s.build());
    }


    private void expectKeyword(String keyword) throws SQLKeywordExpectedException {
        if (!tokenizer.hasNext() || !tokenizer.nextTokenValue().equalsIgnoreCase(keyword)) {
            throw new SQLKeywordExpectedException("Expected keyword " + keyword + " but found " + tokenizer.nextTokenValue());
        }
        tokenizer.nextToken();
    }
}
