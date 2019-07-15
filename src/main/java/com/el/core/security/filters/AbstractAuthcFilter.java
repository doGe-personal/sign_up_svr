package com.el.core.security.filters;

import com.el.core.security.checkers.AuthcChecker;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author DF
 */
@Slf4j
public abstract class AbstractAuthcFilter extends CustomFilter {

    private final AuthcChecker checker;

    protected AbstractAuthcFilter(AuthcChecker checker) {
        this.checker = checker;
    }

    @Override
    protected boolean filter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpStatus status = this.confirmAccessState(request, response);
        if (status.is2xxSuccessful()) {
            return true;
        } else {
            this.handleAccessDenied(request, response, status);
            return false;
        }
    }

    protected HttpStatus confirmAccessState(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return SecurityUtils.getSubject().isAuthenticated() ? this.checker.postAuthc(request) : HttpStatus.UNAUTHORIZED;
    }

    protected void handleAccessDenied(HttpServletRequest request, HttpServletResponse response, HttpStatus status) throws Exception {
        response.setStatus(status.value());
    }

}
