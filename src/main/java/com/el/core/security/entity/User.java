package com.el.core.security.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author danfeng
 * @since 2018/4/24
 */
@Data
@EqualsAndHashCode(of = "userId")
public class User implements Serializable {
    /**
     *  用户ID
     */
    private Long userId;
    /**
     *  用户名
     */
    private String username;
    /**
     * 加密密码
     */
    private String password;
    /**
     * 加密盐值
     */
    private String salt;

    /**
     * 角色Id
     */
    private Long roleId;
    /**
     * 记住我
     */
    private Boolean rememberMe;
    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime updateTime;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime createTime;

}
