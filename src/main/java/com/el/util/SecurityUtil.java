package com.el.util;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author danfeng
 * @since 2018/5/8
 */
@Slf4j
public abstract class SecurityUtil {

    private static final String XSRF_NAME = "el-xsrf";
    private static final String XSRF_PARAM_NAME = "_xsrf_";
    private static final Cookie XSRF_COOKIE_NULL = new Cookie(XSRF_NAME, null);

    /**
     *  创建 anti-CSRF 令牌，并放入 coookie 和 header 中
     * @param request
     * @param response
     */
    public static void createXsrfToken(HttpServletRequest request, HttpServletResponse response) {
        String token = UUID.randomUUID().toString();
        Cookie tokenCookie = new Cookie("el-xsrf", token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath(request.getContextPath() + "/");
        response.addCookie(tokenCookie);
        response.setHeader("el-result-code", token);
        log.trace("[CORE-XSRF] token created: {}@{}", tokenCookie.getValue(), tokenCookie.getPath());
    }
    /**
     * 校对 cookie 和 header 中的 anti-csrf 令牌
     *
     * @param request request
     * @return true if ok
     */
    public static boolean checkXsrfToken(HttpServletRequest request) {

        val cookies = request.getCookies();
        if (cookies == null) {
            log.trace("[CORE-XSRF] SHOULD fetch anti-CSRF token before {}", request.getRequestURI());
            return false;
        }

        val tokenCookie = Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(XSRF_NAME))
            .findAny().orElse(XSRF_COOKIE_NULL);
        if (tokenCookie == XSRF_COOKIE_NULL) {
            log.trace("[CORE-XSRF] no token in cookie - {}", request.getRequestURI());
            return false;
        }
        val tokenInCookie = tokenCookie.getValue();
        String tokenInRequest = request.getHeader(XSRF_NAME);
        if (tokenInRequest == null) {
            tokenInRequest = request.getParameter(XSRF_PARAM_NAME);
            if (tokenInRequest == null) {
                log.trace("[CORE-XSRF] no token in headers or parameters - {}", request.getRequestURI());
                return false;
            }
        }
        if (!tokenInRequest.equals(tokenInCookie)) {
            log.trace("[CORE-XSRF] header({}) != cookie({})", tokenInRequest, tokenInCookie);
            return false;
        }

        return true;
    }

}
