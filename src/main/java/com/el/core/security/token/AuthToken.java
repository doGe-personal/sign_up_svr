package com.el.core.security.token;

import org.apache.shiro.authc.AuthenticationToken;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Danfeng
 * @since 2018/7/25
 */
public interface AuthToken extends AuthenticationToken {
    /**
     * 1. 解析请求
     *
     * @param request HTTP请求
     */
    void parse(HttpServletRequest request);

    /**
     * 2. 判断就绪(即判断认证请求解析出来的信息是否已包含该令牌所需要的所有必要属性)
     *
     * @return true if ready
     */
    boolean ready();
}
