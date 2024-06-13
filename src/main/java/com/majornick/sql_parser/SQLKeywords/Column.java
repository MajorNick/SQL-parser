package com.majornick.sql_parser.SQLKeywords;

import lombok.Builder;

@Builder
public record Column(String columnName, String alias) {
}
