package com.el.core.jdbc.interceptor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.session.RowBounds;

/**
 * @author Danfeng
 * @since 2018/9/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PagingBounds extends RowBounds {
    /**
     * 总记录数
     */
    private int total;
    /**
     * 查询的起始位置
     */
    private int offset;
    /**
     * 查询多少行记录
     */
    private int limit;

    public PagingBounds() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public PagingBounds(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public void setMeToDefault() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public int getSelectCount() {
        return limit + offset;
    }
}
