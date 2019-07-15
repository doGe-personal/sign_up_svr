package com.el.core.security.filters;

import com.el.core.security.checkers.AuthcChecker;
import org.apache.shiro.web.filter.mgt.DefaultFilter;

/**
 * @author Danfeng
 * @since 2018/5/24
 */
public class AuthcFilter extends AbstractAuthcFilter {

    protected AuthcFilter(AuthcChecker authcChecker) {
        super(authcChecker);
    }

    @Override
    public String getFilterName() {
        return DefaultFilter.authc.name();
    }
}
