package com.el.core.security.token;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Danfeng
 * @since 2018/7/25
 */
public class WechatToken implements AuthToken {

    @Override
    public void parse(HttpServletRequest request) {

    }

    @Override
    public boolean ready() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
