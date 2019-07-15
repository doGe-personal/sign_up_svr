package com.el.core.jdbc.coutomTypeHandler;

import org.apache.ibatis.executor.result.ResultMapException;
import org.apache.ibatis.type.BaseTypeHandler;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author danfeng
 * @since 2018/1/25.
 */
public abstract class CommonTypeHandler<T> extends BaseTypeHandler<T> {
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        try {
            return this.getNullableResult(rs, columnName);
        } catch (Exception var4) {
            throw new ResultMapException("Error attempting to get column '" + columnName + "' from result set.  Cause: " + var4, var4);
        }
    }

    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            return this.getNullableResult(rs, columnIndex);
        } catch (Exception var4) {
            throw new ResultMapException("Error attempting to get column #" + columnIndex + " from result set.  Cause: " + var4, var4);
        }
    }

    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            return this.getNullableResult(cs, columnIndex);
        } catch (Exception var4) {
            throw new ResultMapException("Error attempting to get column #" + columnIndex + " from callable statement.  Cause: " + var4, var4);
        }
    }
}
