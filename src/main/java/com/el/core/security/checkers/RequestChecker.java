package com.el.core.security.checkers;

import javax.servlet.http.HttpServletRequest;

/**
 * 检查HTTP请求是否许可
 *
 * @author Danfeng
 * @since 2018/5/24
 */
@FunctionalInterface
public interface RequestChecker {
    /**
     * @param request HTTP请求
     * @return 通过？
     */
    boolean check(HttpServletRequest request);

    /**
     * 不做检查
     */
    RequestChecker PASS = (r) -> true;
}
