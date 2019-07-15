package com.el.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author danfeng
 * @since 2018/5/9
 */
@Data
@ConfigurationProperties("security")
public class SecurityProperties {
    /**
     * csrf 开关
     */
    private boolean xsrf;

    public boolean isXsrfEnabled(){
        return xsrf;
    }

    /**
     * 验证码开关
     */
    private boolean captcha;

    /**
     * @return 验证码开启了吗？
     */
    public boolean captchaEnabled() {
        return captcha;
    }

    /**
     * CORS 许可的域名
     */
    private String cors;

    /**
     * @return CORS 许可的域名
     */
    public String allowedOrigin() {
        return cors;
    }
}
