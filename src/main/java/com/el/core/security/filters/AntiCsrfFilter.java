package com.el.core.security.filters;

import com.el.core.security.checkers.RequestChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Danfeng
 * @since 2018/5/24
 */
@Slf4j
public class AntiCsrfFilter extends CustomFilter {
    public static final String NAME = "anticsrf";

    @Override
    public String getFilterName() {
        return NAME;
    }

    private final RequestChecker checker;

    public AntiCsrfFilter(RequestChecker checker) {
        this.checker = checker;
    }

    @Override
    protected boolean filter(HttpServletRequest request, HttpServletResponse response) {
        if (checker.check(request)) {
            return true;
        } else {
            log.trace("[CORE-AUTH] request anti-csrf check failed, and response with NOT FOUND.");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return false;
        }
    }

}
