package com.majornick.sql_parser.domain;

import lombok.Builder;

@Builder
public record Source(String tableName,String alias) {

}
