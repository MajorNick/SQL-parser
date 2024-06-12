package com.majornick.sql_parser.token;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public  class Token {
    private  TokenType type;
    private  String value;
}