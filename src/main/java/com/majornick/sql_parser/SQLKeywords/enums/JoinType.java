package com.majornick.sql_parser.SQLKeywords.enums;

public enum JoinType {
    LEFT,
    RIGHT,
    INNER,
    OUTER,
    FULL,
    CROSS;

    public static boolean isJoinTypeKeyword(String s){
        for(JoinType k : JoinType.values()){
            if(k.toString().equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }
}
