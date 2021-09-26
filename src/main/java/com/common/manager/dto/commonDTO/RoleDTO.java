package com.common.manager.dto.commonDTO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sucre
 * @since 2020-04-30
 */

public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */

    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 菜单id json
     */
    private String menu;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }


    @Override
    public String toString() {
        return "RoleDTO{" +
            "id=" + id +
            ", name=" + name +
            ", menu=" + menu +

        "}";
    }
}
