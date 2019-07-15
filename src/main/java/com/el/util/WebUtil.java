package com.el.util;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.el.util.OpResult.HTTP_HEADER_ATTR;

/**
 * 通用的 Web 工具方法
 *
 * @author neo.pan
 * @since 16/12/15
 */
@Slf4j
public abstract class WebUtil {

    private static final String RESULT_CODE = "el-result-code";
    private static final String XSRF_NAME = "el-xsrf";
    private static final Cookie XSRF_NULL = new Cookie(XSRF_NAME, null);

    private static final String CT_MULTIPART = "multipart/form-data;";

    private static final String OP_RESULT_OK = "OK";
    private static final String OP_RESULT_NG = "NG";

    static CorsConfiguration buildCorsConfiguration() {
        val config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader(RESULT_CODE);
        return config;
    }

    /**
     * 创建 anti-CSRF 令牌
     *
     * @param request  request
     * @param response response
     * @return token
     */
    public static String createCsrfToken(HttpServletRequest request, HttpServletResponse response) {
        val token = UUID.randomUUID().toString();
        val tokenCookie = new Cookie(XSRF_NAME, token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath(request.getContextPath() + "/");
        response.addCookie(tokenCookie);
        response.setHeader(RESULT_CODE, token);
        log.debug("[CORE-WAF] Request token created: {} @ {}", tokenCookie.getValue(), tokenCookie.getPath());
        return token;
    }

    public static boolean isPostRequest(HttpServletRequest request) {
        return HttpMethod.resolve(request.getMethod()) == HttpMethod.POST;
    }

    public static boolean isApiRequest(HttpServletRequest request) {
        val uri = request.getRequestURI();
        return (uri.contains("/api/") || uri.contains("/demo/"))
            && !(HttpMethod.resolve(request.getMethod()) == HttpMethod.GET && uri.contains("/files/"));
    }

    public static void outputStatus(ServletResponse servletResponse, HttpStatus status) {
        val response = (HttpServletResponse) servletResponse;
        response.setStatus(status.value());
    }

    public static void outputResult(ServletResponse servletResponse, OpResult result) {
        val response = (HttpServletResponse) servletResponse;
        response.setStatus(HttpStatus.OK.value());
        response.setHeader(RESULT_CODE, result.getCode());
    }

    public static void logRequest(HttpServletRequest request) {
        if (!log.isDebugEnabled()) return;
        logRequestPrincipal(request);
        logRequestParams(request);
        logRequestHeaders(request);
    }
    /**
     * 成功返回
     *
     * @return HTTP响应
     */
    public static ResponseEntity toResponseEntity() {
        return okBuilder().build();
    }

    /**
     * @return HTTP响应构建器
     */
    public static ResponseEntity.BodyBuilder okBuilder() {
        return ResponseEntity.status(HttpStatus.OK).header(HTTP_HEADER_ATTR, OP_RESULT_OK);
    }

    private static void logRequestPrincipal(HttpServletRequest request) {
        log.debug("[CORE-LOG] PRINCIPAL: {}", request.getUserPrincipal());
    }

    private static void logRequestParams(HttpServletRequest request) {
        val paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            val paramName = paramNames.nextElement();
            log.debug("[CORE-LOG] PARAM: {} = {}", paramName, request.getParameter(paramName));
        }
    }

    private static void logRequestHeaders(HttpServletRequest request) {
        val headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            val headerName = headerNames.nextElement();
            log.debug("[CORE-LOG] HEADER: {} = {}", headerName, request.getHeader(headerName));
        }
    }

    /**
     * 判断来着哪个客户端
     * @param request
     * @return
     */
    public static String whichClient(HttpServletRequest request) {
        return request.getHeader("clent");
    }
}
