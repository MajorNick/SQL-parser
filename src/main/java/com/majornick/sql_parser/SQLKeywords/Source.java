package com.majornick.sql_parser.SQLKeywords;

import lombok.Builder;

@Builder
public record Source(String tableName,String alias) {

}
