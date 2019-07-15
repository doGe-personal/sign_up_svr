package com.el.app.menu.mapper;

import com.el.core.domain.Sql;
import com.el.core.security.entity.Menu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

/**
 * @author Danfeng
 * @since 2018/7/23
 */
public interface MenuMapper {
    class SqlBuilder extends Sql {

        private static final String MENUS_OF_SQL = "menusOfSql";

        public String menusOfSql() {
            SELECT("distinct sm.menu_Id menuId, sm.upper_Id upperId, sm.menu_Uri menuUri, " +
                "sm.menu_Ico menuIco, sm.menu_Label menuLabel, sm.menu_Type menuType");
            FROM("S_MENU sm");
            INNER_JOIN("S_ROLE_MENU rm on rm.menu_id = sm.MENU_ID and rm.role_id = (select role_id from  S_ROLE_USER ru where ru.USER_ID = #{userId})");
            ORDER_BY("menuId");
            return toString();
        }

    }

    /**
     * 根据用户ID获取能访问的菜单一览
     *
     * @param userId 用户ID
     * @return 菜单一览
     */
    @SelectProvider(type = SqlBuilder.class, method = SqlBuilder.MENUS_OF_SQL)
    List<Menu> menusOf(Integer userId);

    @Select({"SELECT menu_Ico from S_MENU"})
    List<String> countOfTabs();

    @Update({
        "UPDATE S_MENU set menu_Ico = 'home'"
    })
    int updateMenu();

    @Insert({
        "insert into S_MENU (menu_Id,upper_Id,menu_Uri,menu_Ico,menu_Label,menu_Type) ",
        "values (" ,
        "#{menuId},#{upperId},#{menuUri},#{menuIco},#{menuLabel},#{menuType}",
        ")"
    })
    @Transactional(propagation= Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ)
    int insertMenu(Menu menu);

}
