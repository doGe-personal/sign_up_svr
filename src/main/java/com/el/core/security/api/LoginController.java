package com.el.core.security.api;

import com.el.common.SecurityProperties;
import com.el.core.security.token.FormToken;
import com.el.util.CaptchaUtil;
import com.el.util.SecurityUtil;
import com.el.util.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.SessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import static com.el.core.security.entity.AuthOp.NG_ACCOUNT;
import static com.el.core.security.entity.AuthOp.NG_CAPTCHA;
import static com.el.util.OpResult.OK;

/**
 * @author danfeng
 * @since 2018/4/24
 */
@Slf4j
@RestController
@RequestMapping("${security.apis:/}")
@Api(description = "用户权限")
public class LoginController {

    @Autowired
    private SecurityProperties securityProperties;

    @ApiOperation(value = "用户登陆", notes = "验证码开发环境可选")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
        @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
        @ApiImplicitParam(name = "captcha", value = "验证码", dataType = "String")
    })
    @PostMapping("/login")
    public void login(HttpServletResponse response, HttpServletRequest request) {
        FormToken token = new FormToken();
        token.parse(request);

        val subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }

        if (securityProperties.isCaptcha()) {
            if (!token.getCaptcha().check(false)) {
                WebUtil.outputResult(response, NG_CAPTCHA);
                return;
            }
        }

        try {
            subject.login(token);
            WebUtil.outputResult(response, OK);
        } catch (AuthenticationException e) {
            log.debug("[CORE-AUTH] authc failed because of {}", e.getMessage());
            WebUtil.outputResult(response, NG_ACCOUNT);
        }
    }

    @ApiOperation(value = "获取验证码", notes = "建议请求带上时间戳")
    @GetMapping({"/captcha"})
    public String generateCaptcha(HttpServletRequest request) throws IOException {
        byte[] captchaImageBytes = CaptchaUtil.generate(request);
        return Base64.getEncoder().encodeToString(captchaImageBytes);
    }

    @ApiOperation("登陆实体")
    @GetMapping({"/principal"})
    public ResponseEntity principal() {
        val subject = SecurityUtils.getSubject();
        return subject.isAuthenticated() ? ResponseEntity.ok(subject.getPrincipal()) : ResponseEntity.noContent().build();
    }

    @ApiOperation("跨域请求")
    @GetMapping("/xsrf")
    public void xsrf(HttpServletRequest request, HttpServletResponse response) {
        SecurityUtil.createXsrfToken(request, response);
    }

    @ApiOperation("用户退出")
    @PostMapping("/logout")
    public ResponseEntity logout() {
        try {
            val subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                subject.logout();
            }
        } catch (SessionException ex) {
            log.trace("[CORE-AUTH] Encountered session exception during logout." +
                " This can generally safely be ignored.");
        }
        return WebUtil.toResponseEntity();
    }


}
