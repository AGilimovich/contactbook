package com.itechart.data.query;

/**
 * Created by Aleksandr on 03.04.2017.
 */
public class DynamicPreparedQueryBuilder {

    private StringBuilder query;
    private boolean isFirst = true;


    public DynamicPreparedQueryBuilder(String query) {
        this.query = new StringBuilder(query);
    }

    public void appendWhereClause(String name) {
        if (isFirst) {
            query.append(" WHERE ").append(name).append("=").append("?");
            isFirst = false;
        } else
            query.append(" AND ").append(name).append("=").append("?");
    }

    public void appendBetween(String name) {
        if (isFirst) {
            query.append(" WHERE ").append(name).append(" BETWEEN ").append("?").append(" AND ").append("?");
            isFirst = false;
        } else
            query.append(name).append(" BETWEEN ").append("?").append(" AND ").append("?");
    }

    public void appendLimit(int from, int count) {
        query.append(" LIMIT ").append(from).append(",").append(count);
    }


    public StringBuilder getQuery() {
        return query;
    }

}