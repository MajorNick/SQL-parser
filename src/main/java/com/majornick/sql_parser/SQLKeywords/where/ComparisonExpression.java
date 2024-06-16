package com.majornick.sql_parser.SQLKeywords.where;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComparisonExpression implements Expression{
    private Expression left;
    private Expression right;
    private String Operator;
}
