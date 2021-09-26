package com.common.manager.controller.commonController;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.manager.annotation.RequiredRole;
import com.common.manager.common.RoleConstants;
import com.common.manager.common.UserUtil;
import com.common.manager.dto.commonDTO.MenuDTO;
import com.common.manager.info.commonInfo.CommonResult;
import com.common.manager.info.commonInfo.MainMenu;
import com.common.manager.info.commonInfo.MenuInfo;
import com.common.manager.info.commonInfo.RoleInfo;
import com.common.manager.service.common.IMenuService;
import com.common.manager.service.common.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-04-30
 */
@RestController
@RequestMapping("/menuDO")
@RequiredRole(RoleConstants.EVERYONE)
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleService iRoleService;

    /**
     * 对接前端接口，新增一条数据
     */
    @PostMapping("/")
    public CommonResult<Integer> create(@RequestBody MenuDTO menuDTO) {
        CommonResult<Integer> result = new CommonResult<>();
        Integer id = menuService.create(menuDTO);
        result.setData(id);
        return result;
    }
    /**
     * 对接前端接口，更新一条数据
     * @param id
     * @param menuDTO
     */
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody MenuDTO menuDTO) {
        menuService.update(id, menuDTO);
    }
    /**
     * 对接前端接口，删除一条数据
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        menuService.delete(id);
    }

    /**
     * 取指定id的数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommonResult<MenuInfo> get(@PathVariable Integer id) {
        CommonResult<MenuInfo> result = new CommonResult<>();
        MenuInfo menuInfo = menuService.get(id);
        result.setData(menuInfo);
        return result;
    }
    /**
     * 取分页面数据。
     * @param page
     * @param pageSize
     * @param query
     * @return
     */
    @GetMapping("/page/{page}/{pageSize}")
    public CommonResult<Page<MenuInfo>> listPage(@PathVariable Integer page, @PathVariable Integer pageSize, String query) {
        CommonResult<Page<MenuInfo>> result = new CommonResult<>();
        Page<MenuInfo> list = menuService.listPage(page, pageSize, query);
        result.setData(list);
        return result;
    }

    /**
     * 取当前用户角色权限内的 一二级菜单
     * @return
     */
    @RequiredRole(RoleConstants.EVERYONE)
    @GetMapping("/getMenu")
    public CommonResult<List<MainMenu>> getMenu(){
        //取出当前用户的角色id
        Integer id = UserUtil.getBaseUser().getRoleId();
        return getMenuById(id);
    }

    /**
     * 根据id返回角色权限内的 一二级菜单
     * @param id
     * @return
     */
    private CommonResult<List<MainMenu>> getMenuById(Integer id){
        //根据当前用户角色id取出角色类
        RoleInfo roleInfo=iRoleService.get(id);
        //取出当前角色下的菜单列表,转换为json
        //把字符串 用fastjson转成数组
        Integer[] ids = JSON.parseObject(roleInfo.getMenu(),new TypeReference<Integer[]>(){});
        List<Integer> list = Arrays.asList(ids);
        //用list数组查出所有权限内的菜单。 //把菜单分成一二三级装入info类，返回到前端。
        List<MainMenu> menu=menuService.listMenu(list);

        CommonResult<List<MainMenu>> ret=new CommonResult<>();
        ret.setData(menu);
        return ret;
    }

    /**
     * 列出所有菜单给管理员，新增角色时或修改角色时，分配菜单用，
     * @return
     */
    @RequiredRole(RoleConstants.EVERYONE)
    @GetMapping("/getAllMenu")
    public CommonResult<List<MainMenu>> getAllMenu(){
        //取出当前用户的角色id
        Integer id = UserUtil.getBaseUser().getRoleId();
        if(UserUtil.getBaseUser().getAdmin()){
            //管理员返回全部菜单
            List<MainMenu> menu=menuService.listAllMenu();

            CommonResult<List<MainMenu>> ret=new CommonResult<>();
            ret.setData(menu);
            return ret;
        }else{
            //非超级管理员，只返回id内的权限菜单
            return getMenuById(id);
        }

    }
}

