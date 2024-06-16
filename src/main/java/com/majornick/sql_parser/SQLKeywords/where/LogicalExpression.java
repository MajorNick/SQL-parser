package com.majornick.sql_parser.SQLKeywords.where;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LogicalExpression implements Expression{
    private Expression left;
    private Expression right;
    private String Operator;

}
