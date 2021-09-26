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

public class MenuDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */

    private Integer id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单路径
     */
    private String path;
    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 父菜单id
     */
    private Integer pid;



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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }



    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "id=" + id +
                ", name=" + name +
                ", path=" + path +
                ", pid=" + pid +

                "}";
    }
}
