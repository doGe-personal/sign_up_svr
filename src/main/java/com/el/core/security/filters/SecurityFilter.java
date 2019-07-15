package com.el.core.security.filters;

import com.el.core.security.checkers.AuthcChecker;
import com.el.core.security.checkers.RequestChecker;
import groovy.util.logging.Slf4j;
import lombok.val;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

import java.util.Map;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.anon;
import static org.apache.shiro.web.filter.mgt.DefaultFilter.authc;

/**
 * @author Danfeng
 * @since 2018/5/24
 */
@Slf4j
public class SecurityFilter extends AbstractShiroFilter {

    public SecurityFilter(DefaultWebSecurityManager webSecurityManager, Map<String, String> filterChains, RequestChecker antiCsrfChecker,AuthcChecker authcChecker) {
        super();
        setSecurityManager(webSecurityManager);
        setFilterChainResolver(createFilterChainResolver(filterChains, antiCsrfChecker,authcChecker));
    }

    private PathMatchingFilterChainResolver createFilterChainResolver(Map<String, String> filterChains,RequestChecker antiCsrfChecker,AuthcChecker authcChecker){
        val filterChainResolver = new PathMatchingFilterChainResolver();
        val filterChainManager = filterChainResolver.getFilterChainManager();
        filterChainManager.getFilters().clear(); // clear default filters
        // csrf 跨域检查
        addFilter(filterChainManager,  new AntiCsrfFilter(antiCsrfChecker));
        // auth 身份认证过滤器
        val authcFilter = new AuthcFilter(authcChecker);
        addFilter(filterChainManager, authcFilter);
        filterChainManager.addFilter(anon.name(), anon.newInstance());
        filterChains.forEach(filterChainManager::createChain);
        return filterChainResolver;
    }

    private void addFilter(FilterChainManager filterChainManager, CustomFilter filter) {
        filterChainManager.addFilter(filter.getFilterName(), filter);
    }
}
