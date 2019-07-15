package com.el.app.menu.api;

import com.el.core.security.entity.User;
import com.el.app.menu.service.MenuService;
import com.el.util.WebResultUtils;
import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Danfeng
 * @since 2018/9/15
 */
@RestController
@RequestMapping("api/")
@AllArgsConstructor
public class MenuApi {


    private final MenuService menuService;

    @GetMapping("menus")
    public ResponseEntity menus() {
        val subject = (User) SecurityUtils.getSubject().getPrincipal();
        return WebResultUtils.toOkReqEntity(menuService.menusOf(subject.getUserId().intValue()));
    }

}
