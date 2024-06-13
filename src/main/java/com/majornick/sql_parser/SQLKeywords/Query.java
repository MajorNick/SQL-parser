package com.majornick.sql_parser.SQLKeywords;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Query {
    private List<Column> columns;
    private List<Source> fromSources;
    private List<Join> joins;
    private List<WhereClause> whereClauses;
    private List<String> groupByColumns;
    private List<Sort> sortColumns;
    @Setter
    private Integer limit;
    @Setter
    private Integer offset;

    public Query() {
        columns = new ArrayList<>();
        fromSources = new ArrayList<>();
        joins = new ArrayList<>();
        whereClauses = new ArrayList<>();
        groupByColumns = new ArrayList<>();
        sortColumns = new ArrayList<>();
    }

    public void addColumn(Column column){
        columns.add(column);
    }
    public void addSource(Source source){
        fromSources.add(source);
    }
    public void addJoin(Join join){
        joins.add(join);
    }
    public void addGroupBy(String groupBycolumns){
        groupByColumns.add(groupBycolumns);
    }
    public void addSort(Sort s){
        sortColumns.add(s);
    }


}