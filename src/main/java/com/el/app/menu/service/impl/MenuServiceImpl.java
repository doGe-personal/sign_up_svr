package com.el.app.menu.service.impl;

import com.el.app.menu.mapper.MenuMapper;
import com.el.app.menu.service.MenuService;
import com.el.core.security.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/7/23
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {


    private final MenuMapper menuMapper;

    @Override
    public List<Menu> menusOf(Integer userId) {
        return menuMapper.menusOf(userId);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void testProp(Menu menu) {
        List<String> tabIcons = menuMapper.countOfTabs();
        System.out.println("1,===tabs===>" + tabIcons.toString());
        menuMapper.updateMenu();
        // new trans
        menuMapper.insertMenu(menu);
        List<String> afterUpdate = menuMapper.countOfTabs();
        System.out.println("2,===tabs===>" + afterUpdate.toString());
    }

}
