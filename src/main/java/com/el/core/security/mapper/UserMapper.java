package com.el.core.security.mapper;

import com.el.core.domain.Sql;
import com.el.core.security.entity.Menu;
import com.el.core.security.entity.User;
import com.el.util.SqlUtil;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.jdbc.core.SqlProvider;

import java.util.List;

/**
 * @author Danfeng
 * @since 2018/7/9
 */
public interface UserMapper {


    class SqlBuilder extends Sql {
        private void SELECT_SQL() {
            SELECT("T.");
        }
        static final String SYS_USER_SQL = "sysUserSql";
        public String sysUserSql() {
            return toString();
        }

    }

    @SelectProvider(type = SqlBuilder.class, method = SqlBuilder.SYS_USER_SQL)
    List<User> usersOf();

    /**
     * 根据用户名查询用户
     * @param uname
     * @return
     */
    @Select({
        "select user_id userId,",
        "username username,",
        "password password,",
        "salt salt,",
        "update_time updateTime,",
        "create_time createTime",
        "from s_user",
        "where username = #{uname}"
    })
    User findUserByName(String uname);


}
