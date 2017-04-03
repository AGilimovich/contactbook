package com.itechart.data.query;

/**
 * Created by Aleksandr on 03.04.2017.
 */
public class DynamicQueryBuilder {

    private StringBuilder query;
    private boolean isFirst = true;


    public DynamicQueryBuilder(String query) {
        this.query = new StringBuilder(query);
    }

    public void appendClause(String name, String value) {
        if (isFirst) {
            query.append(" WHERE ").append(name).append("=").append(value);
            isFirst = false;
        } else
            query.append(" AND ").append(name).append("=").append(value);
    }

    public void appendBetween(String name) {
        if (isFirst) {
            query.append(" WHERE ").append(name).append(" BETWEEN ");
            isFirst = false;
        } else
            query.append(name).append(" BETWEEN ");
    }

    public void appendBetweenFirstValue(String value) {
        query.append(value).append(" AND ");
    }

    public void appendBetweenSecondValue(String value) {
        query.append(value);
    }

    public void appendLimit(int from, int count) {
        query.append(" LIMIT ").append(from).append(",").append(count);
    }


    public StringBuilder getQuery() {
        return query;
    }
}
