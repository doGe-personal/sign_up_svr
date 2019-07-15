package com.el.core.jdbc.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;

/**
 * @author Danfeng
 * @since 2018/9/27
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}))
public class MysqlPagingInterceptor extends AbstractPagingInterceptor {

    @Override
    protected String getSelectPagingSql(String targetSql, PagingBounds bounds) {
        String sql = targetSql.toLowerCase();
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(" limit ").append(bounds.getOffset()).append(",").append(bounds.getLimit());
        return sqlBuilder.toString();
    }

    @Override
    protected String getSelectTotalSql(String targetSql) {
        String sql = targetSql.toLowerCase();
        StringBuilder sqlBuilder = new StringBuilder(sql);
        int orderByPos;
        if ((orderByPos = sqlBuilder.lastIndexOf(ORDER_BY)) != -1) {
            sqlBuilder.delete(orderByPos, sqlBuilder.length());
        }
        sqlBuilder.insert(0, "select count(1) as total from ( ").append(" )");
        return sqlBuilder.toString();
    }
}
