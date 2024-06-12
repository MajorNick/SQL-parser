package com.majornick.sql_parser.SQLKeywords;

import com.majornick.sql_parser.SQLKeywords.enums.SortDir;
import lombok.Builder;

@Builder
public record Sort(String column,SortDir direction){
}
