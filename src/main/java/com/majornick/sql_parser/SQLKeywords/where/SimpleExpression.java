package com.majornick.sql_parser.SQLKeywords.where;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleExpression implements Expression {
    private String Value;


}
