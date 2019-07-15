package com.el.core.security.entity;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author danfeng
 * @since 2018/5/9
 */
public class AuthAccount implements AuthenticationInfo {
    @Override
    public PrincipalCollection getPrincipals() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
