package com.majornick.sql_parser.SQLKeywords;

import com.majornick.sql_parser.SQLKeywords.enums.JoinType;
import lombok.Builder;

@Builder
public record Join(Source toSource,JoinType joinType){

}
