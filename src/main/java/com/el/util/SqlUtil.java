package com.el.util;

import com.el.core.domain.Paging;
import com.el.core.domain.SortOption;
import com.el.core.jdbc.dialect.SqlDialect;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author danfeng
 * @since 2018/1/25.
 */
public abstract class SqlUtil {
    public static SqlDialect SQL_DIALECT;
    private static final Pattern RE_WORD = Pattern.compile("^\\w+$");

    public static String validationQuery() {
        return SQL_DIALECT.validationQuery();
    }

    public static String toSqlWord(String input) {
        if (!RE_WORD.matcher(input).matches()) {
            throw new IllegalArgumentException("[CORE-JDBC] Illegal inputted sql word: " + input);
        } else {
            return input;
        }
    }

    public static String paging(String sql, Paging paging) {
        if (paging != null) {
            sql = SQL_DIALECT.paging(sql, paging);
        }
        return sql;
    }

    /**
     * 排序 A DESC|ASC
     *
     * @param options
     * @return
     */
    public static String toOrderByClause(SortOption... options) {
        if (options.length == 0) {
            throw new DevError("Parameter(options) is empty");
        } else {
            return (String) Stream.of(options).map(SortOption::toSqlSort).collect(Collectors.joining(","));
        }
    }

    /**
     * sql 字符值操作
     *
     * @param val
     * @return
     */
    public static String toSqlString(CharSequence val) {
        if (StringUtils.isEmpty(val)) {
            throw new DevError("Parameter(val) is null or empty");
        } else {
            return escapeString(new StringBuilder(), val).toString();
        }
    }

    /**
     * 转换成sql字符类型
     *
     * @param sb
     * @param input
     * @return
     */
    private static StringBuilder escapeString(StringBuilder sb, CharSequence input) {
        sb.append('\'');
        int i = 0;

        for (int n = input.length(); i < n; ++i) {
            char ch = input.charAt(i);
            sb = ch == 39 ? sb.append("''") : sb.append(ch);
        }

        sb.append('\'');
        return sb;
    }

    /**
     * 生成模糊查询SQL语句
     *
     * @param val
     * @param option
     * @return
     */
    public static String toSqlLikeString(CharSequence val, SqlUtil.SqlLikeOption option) {
        if (StringUtils.isEmpty(val)) {
            throw new DevError("Parameter(val) is null or empty");
        } else {
            return escapeLikeString(new StringBuilder(), val, option).toString();
        }
    }

    private static StringBuilder escapeLikeString(StringBuilder sb, CharSequence input, SqlUtil.SqlLikeOption option) {
        Map<Character, String> escapeTable = SQL_DIALECT.escapeTable();
        sb.append(option != SqlUtil.SqlLikeOption.RIGHT_SIDE ? "'%" : "'");
        int i = 0;

        for (int n = input.length(); i < n; ++i) {
            char ch = input.charAt(i);
            String escaped = (String) escapeTable.get(Character.valueOf(ch));
            sb = escaped != null ? sb.append(escaped) : sb.append(ch);
        }

        sb.append(option != SqlUtil.SqlLikeOption.LEFT_SIDE ? "%'" : "'");
        sb.append(SQL_DIALECT.escapeClause());
        return sb;
    }

    /**
     * ’%aaa%‘
     *
     * @param val
     * @return
     */
    public static String toSqlLikeString(String val) {
        return toSqlLikeString(val, SqlUtil.SqlLikeOption.BOTH_SIDES);
    }

    public enum SqlLikeOption {
        BOTH_SIDES,
        LEFT_SIDE,
        RIGHT_SIDE;
    }
}
