package com.el.core.domain;

import java.util.Optional;

/**
 * @author danfeng
 * @since 2018/2/6.
 */
public interface Sortable {
     Optional<SortOption> getSortOption();
}
