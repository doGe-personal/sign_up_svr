package com.el.core.security.filters;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author DF
 */
@Slf4j
public abstract class CustomFilter extends AdviceFilter {
    /**
     * @return 过滤器名字
     */
    public abstract String getFilterName();

    /**
     * @param request  HTTP请求
     * @param response HTTP响应
     * @return pass if true
     * @throws Exception if an error occurs.
     */
    protected abstract boolean filter(HttpServletRequest request, HttpServletResponse response) throws Exception;

    @Override
    protected boolean preHandle(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        if (!isEnabled()) {
            log.trace("[CORE-AUTH] This Filter is disabled, so passthrough immediately.");
            return true;
        }

        val request = (HttpServletRequest) servletRequest;
        if (HttpMethod.resolve(request.getMethod()) == HttpMethod.OPTIONS) {
            log.trace("[CORE-AUTH] This is a preflight request, so passthrough immediately.");
            return true;
        }
        return filter(request, (HttpServletResponse) servletResponse);
    }
}
