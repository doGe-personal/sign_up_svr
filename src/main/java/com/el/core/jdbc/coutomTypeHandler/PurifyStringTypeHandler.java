package com.el.core.jdbc.coutomTypeHandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author danfeng
 * @since 2018/1/31.
 */
@MappedJdbcTypes({JdbcType.NCHAR, JdbcType.CHAR})
public class PurifyStringTypeHandler extends CommonTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return nullToBlank(rs.getString(columnName));
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return nullToBlank(rs.getString(columnIndex));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return nullToBlank(cs.getString(columnIndex));
    }

    private static String nullToBlank(String value) {
        return value == null ? "" : StringUtils.trimTrailingCharacter(value, ' ');
    }
}
