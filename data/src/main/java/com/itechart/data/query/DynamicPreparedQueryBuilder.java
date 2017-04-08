package com.itechart.data.query;

import org.apache.commons.lang3.StringUtils;

/**
 * Class for creating dynamic query for using in prepared statements.
 */
public class DynamicPreparedQueryBuilder {

    private StringBuilder query;
    private boolean isFirst = true;


    public DynamicPreparedQueryBuilder(String query) {
        this.query = new StringBuilder(query);
    }

    public void appendWhereClause(String name) {
        if (StringUtils.isBlank(name)) return;
        if (isFirst) {
            query.append(" WHERE ").append(name).append("=").append("?");
            isFirst = false;
        } else
            query.append(" AND ").append(name).append("=").append("?");
    }

    public void appendBetween(String name) {
        if (StringUtils.isBlank(name)) return;
        if (isFirst) {
            query.append(" WHERE ").append(name).append(" BETWEEN ").append("?").append(" AND ").append("?");
            isFirst = false;
        } else
            query.append(" AND ").append(name).append(" BETWEEN ").append("?").append(" AND ").append("?");
    }

    public void appendLimit(int from, int count) {
        query.append(" LIMIT ").append(from).append(",").append(count);
    }


    public StringBuilder getQuery() {
        return query;
    }

}
