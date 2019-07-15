package com.el.core.security.checkers;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Danfeng
 * @since 2018/5/20
 */
@FunctionalInterface
public interface AuthcChecker {
    AuthcChecker OK = (r) -> HttpStatus.OK;

    /**
     * 用户认证确认后再做进一步的状态确认
     *
     * @param request HTTP请求
     * @return 通过HTTP状态反映判断结果
     */
    HttpStatus postAuthc(HttpServletRequest request);
}
