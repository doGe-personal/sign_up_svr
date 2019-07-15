package com.el.app.menu.service;

import com.el.core.security.entity.Menu;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/7/10
 */
public interface MenuService {
    /**
     * 获取菜单
     *
     * @param userId 用戶ID
     * @return 菜单集合
     */
    List<Menu> menusOf(Integer userId);

    void testProp(Menu menu);

}
