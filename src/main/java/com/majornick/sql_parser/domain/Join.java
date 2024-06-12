package com.majornick.sql_parser.domain;

import com.majornick.sql_parser.domain.enums.JoinType;
import lombok.Builder;

@Builder
public record Join(Source toSource,JoinType joinType){

}
