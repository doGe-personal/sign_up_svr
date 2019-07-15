package com.el.core.domain;

import com.el.util.SqlUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author danfeng
 * @since 2018/2/6.
 */
@Data
public class SortOption {
    @JsonProperty("by")
    private String sortBy;
    @JsonProperty("dir")
    private SortOption.Direction sortDirection;

    public static SortOption of(String sortBy, String sortDir) {
        SortOption sortOption = new SortOption();
        sortOption.sortBy = sortBy;
        sortOption.sortDirection = SortOption.Direction.of(sortDir);
        return sortOption;
    }

    public static SortOption of(String by, SortOption.Direction direction) {
        SortOption opt = new SortOption();
        opt.setSortBy(by);
        opt.setSortDirection(direction);
        return opt;
    }

    public String toSqlSort() {
        return SqlUtil.toSqlWord(this.sortBy) + " " + this.sortDirection.name();
    }

    static enum Direction {
        ASC,
        DESC;

        public static SortOption.Direction of(String direction) {
            return direction != null && direction.toUpperCase().equals(DESC.name()) ? DESC : ASC;
        }

        public boolean asc() {
            return this == ASC;
        }
    }
}
