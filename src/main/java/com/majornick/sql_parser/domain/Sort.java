package com.majornick.sql_parser.domain;

import com.majornick.sql_parser.domain.enums.SortDir;
import lombok.Builder;

@Builder
public record Sort(String column,SortDir direction){
}
