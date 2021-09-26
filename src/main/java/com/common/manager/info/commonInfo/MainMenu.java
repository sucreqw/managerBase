package com.common.manager.info.commonInfo;

import java.util.List;

/**
 * 一级菜单类，
 */
public class MainMenu {
    /*{
    path: '/setting',
            name: '设置',
        icon: 'el-icon-setting',
        component: Wapper,//前端匹配，后端无此字段。
        children: []//二级菜单list
}*/
    private Integer id;
    private String name;
    private String path;
    private String icon;
    private List<MainMenu> children;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MainMenu> getChildren() {
        return children;
    }

    public void setChildren(List<MainMenu> children) {
        this.children = children;
    }


}
