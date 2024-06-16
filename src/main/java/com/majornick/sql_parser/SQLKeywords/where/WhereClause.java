package com.majornick.sql_parser.SQLKeywords.where;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WhereClause {
    private Expression exp;
}
