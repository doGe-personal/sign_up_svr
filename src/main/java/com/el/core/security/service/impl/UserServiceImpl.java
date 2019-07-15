package com.el.core.security.service.impl;

import com.el.core.security.entity.User;
import com.el.core.security.mapper.UserMapper;
import com.el.core.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author danfeng
 * @since 2018/4/24
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 用户登录
     * @param uname
     * @return
     */
    @Override
    public User findUserByName(String uname){
        return userMapper.findUserByName(uname);
    }

}
