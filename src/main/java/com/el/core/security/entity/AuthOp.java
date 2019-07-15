package com.el.core.security.entity;

import com.el.util.OpResult;

/**
 * @author Danfeng
 * @since 2018/9/15
 */
public enum AuthOp implements OpResult {
    NG_CAPTCHA("验证码输入错误"),
    NG_ACCOUNT("帐户不存在或凭证不符"),
    NG_PRINCIPAL("帐户不存在"),
    NG_CREDENTIAL("凭证不符"),
    NG_UNSUPPORTED("不支持的认证请求");

    private final String desc;

    @Override
    public String getCode() {
        return this.name();
    }

    private AuthOp(String desc) {
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
