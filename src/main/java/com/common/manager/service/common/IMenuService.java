package com.common.manager.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.manager.dto.commonDTO.MenuDTO;
import com.common.manager.info.commonInfo.MainMenu;
import com.common.manager.info.commonInfo.MenuInfo;
import com.common.manager.repository.entity.common.MenuDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2020-04-30
 */
public interface IMenuService extends IService<MenuDO> {
    //新增数据
    Integer create(MenuDTO menuDTO);
    //删除数据
    void delete(Integer id);
    //更新数据
    void update(Integer id, MenuDTO menuDTO);
    //分页数据
    Page<MenuInfo> listPage(Integer page, Integer pageSize, String query);
    //取指定id数据
    MenuInfo get(Integer id);
    //列出指权限下的所有菜单，装入mainmenu类
    List<MainMenu> listMenu(List<Integer> ids);
    //列出所有菜单
    List<MainMenu> listAllMenu();
}
