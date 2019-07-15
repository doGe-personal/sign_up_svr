package com.el.core.jdbc.dialect;

import com.el.core.domain.Paging;

import java.util.HashMap;
import java.util.Map;

/**
 * @author danfeng
 * @since 2018/1/25.
 */
public class MysqlDialect implements SqlDialect {

    private static final Map<Character, String> SQL_ESC = new HashMap<>();
    @Override
    public Map<Character, String> escapeTable() {
        return SQL_ESC;
    }

    @Override
    public String escapeClause() {
        return "";
    }

    @Override
    public String validationQuery() {
        return "SELECT 1";
    }

    @Override
    public String paging(String querySql, Paging paging) {
        return querySql + " LIMIT " + paging.getOffset() + "," + paging.getLimit();
    }
    static {
        SQL_ESC.put(Character.valueOf('\''), "''");
        SQL_ESC.put(Character.valueOf('%'), "\\%");
        SQL_ESC.put(Character.valueOf('_'), "\\_");
    }
}
