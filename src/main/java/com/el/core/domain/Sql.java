package com.el.core.domain;

import com.el.util.SqlUtil;
import org.apache.ibatis.jdbc.AbstractSQL;

/**
 * @author danfeng
 * @since 2018/2/6.
 */
public abstract class Sql extends AbstractSQL<Sql> {
    @Override
    public Sql getSelf() {
        return this;
    }

    protected String toPagingSql(PagingQuery query) {
        return SqlUtil.paging(this.toString(), query);
    }

    protected void ORDER_BY(Sortable query) {
        query.getSortOption().ifPresent((option) -> {
            Sql var10000 = this.ORDER_BY(SqlUtil.toOrderByClause(new SortOption[]{option}));
        });
    }
}
