package com.el.core.security.entity;

import com.el.util.CaptchaUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Danfeng
 * @since 2018/9/15
 */
public class FormCaptcha {
    private static final String P_CAPTCHA = "captcha";
    private final String captcha;
    private final String captchaInSession;

    public FormCaptcha(HttpServletRequest request) {
        this.captcha = request.getParameter("captcha");
        this.captchaInSession = CaptchaUtil.captchaOf(request.getSession());
    }

    public boolean check(boolean caseSensitive) {
        boolean var10000;
        label27: {
            if (this.captcha != null && this.captchaInSession != null) {
                if (caseSensitive) {
                    if (this.captcha.equals(this.captchaInSession)) {
                        break label27;
                    }
                } else if (this.captcha.equalsIgnoreCase(this.captchaInSession)) {
                    break label27;
                }
            }

            var10000 = false;
            return var10000;
        }

        var10000 = true;
        return var10000;
    }

}
