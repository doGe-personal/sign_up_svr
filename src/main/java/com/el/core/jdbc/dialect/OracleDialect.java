package com.el.core.jdbc.dialect;

import com.el.core.domain.Paging;

import java.util.HashMap;
import java.util.Map;

/**
 * @author danfeng
 * @since 2018/2/6.
 */
public class OracleDialect implements SqlDialect {
    private static final Map<Character, String> SQL_ESC = new HashMap<>();

    @Override
    public Map<Character, String> escapeTable() {
        return SQL_ESC;
    }

    @Override
    public String escapeClause() {
        return " escape '\\'";
    }

    @Override
    public String validationQuery() {
        return "select 1 from dual";
    }

    @Override
    public String paging(String querySql, Paging paging) {
        int idx = paging.getOffset();
        int rn0 = idx + 1;
        int rn1 = idx + paging.getLimit();
        return "SELECT * FROM ( SELECT all_.*, rownum rn_ from ( " + querySql + " ) all_ where rownum <= " + rn1 + " ) where rn_ >= " + rn0;
    }

    static {
        SQL_ESC.put(Character.valueOf('\''), "''");
        SQL_ESC.put(Character.valueOf('%'), "\\%");
        SQL_ESC.put(Character.valueOf('_'), "\\_");
        SQL_ESC.put(Character.valueOf('&'), "\\&");
    }
}
