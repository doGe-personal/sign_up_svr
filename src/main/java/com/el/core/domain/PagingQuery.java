package com.el.core.domain;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author danfeng
 * @since 2018/2/6.
 */
@Data
public class PagingQuery implements Paging, Sortable {
    private static final int DEF_LIMIT = 20;
    private int offset = 0;
    private int limit = 20;
    private String sortBy;
    private SortOption.Direction sortDirection;

    public Optional<SortOption> getSortOption() {
        return StringUtils.isEmpty(this.sortBy) ? Optional.empty() : Optional.of(SortOption.of(this.sortBy, this.sortDirection));
    }

}
