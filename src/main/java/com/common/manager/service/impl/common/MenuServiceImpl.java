package com.common.manager.service.impl.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.manager.common.PageUtil;
import com.common.manager.dto.commonDTO.MenuDTO;
import com.common.manager.info.commonInfo.MainMenu;
import com.common.manager.info.commonInfo.MenuInfo;
import com.common.manager.repository.dao.commonDao.MenuMapper;
import com.common.manager.repository.entity.common.MenuDO;

import com.common.manager.service.common.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2020-04-30
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDO> implements IMenuService {
    /**
     * 具体的接口方法实现，增加一条数据
     * @param menuDTO
     * @return
     */
    @Override
    public Integer create(MenuDTO menuDTO) {
        //建立一个Vo
        MenuDO menuDO = new MenuDO();
        //把前端提交的DTO 复制到vo用来操作数据库，以清洗无用数据
        BeanUtils.copyProperties(menuDTO, menuDO);
        //插入数据
        baseMapper.insert(menuDO);
        //返回新增的id
        return menuDO.getId();
    }
    /**
     * 删除数据
     * @param id
     */
    @Override
    public void delete(Integer id) {
        baseMapper.deleteById(id);
    }

    /**
     * 更新一条数据
     * @param id
     * @param menuDTO
     */
    @Override
    public void update(Integer id, MenuDTO menuDTO) {
        //先在数据库取出数据，建立VO
        MenuDO menuDO = baseMapper.selectById(id);
        //判断数据是否存在，不存在返回null
        if (menuDO == null) {
            return;
        }
        //数据存在，把前端提交上来的DTO 复制到Vo,保存到数据库
        BeanUtils.copyProperties(menuDTO, menuDO);
        baseMapper.updateById(menuDO);
    }

    /**
     * 返回分页数据
     * @param page
     * @param pageSize
     * @param query
     * @return
     */
    @Override
    public Page<MenuInfo> listPage(Integer page, Integer pageSize, String query) {
        //创建查询器
        QueryWrapper<MenuDO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query)) {
            //wrapper.like("card_name", query);
        }
        //建立排序条件
        wrapper.orderByDesc("id");
        //建立分页类
        Page<MenuDO> menuDOPage = new Page<>(page, pageSize);
        //把数据装入分页类并返回
        return PageUtil.buildPage(baseMapper.selectPage(menuDOPage, wrapper), MenuInfo.class);
    }

    /**
     * 根据指定id 取数据
     * @param id
     * @return
     */
    @Override
    public MenuInfo get(Integer id) {
        //取出指定id的数据装入Vo
        MenuDO menuDO = baseMapper.selectById(id);
        //不存在数据则返回null
        if (menuDO == null) {
            return null;
        }
        //建立info类，返回数据
        MenuInfo menuInfo = new MenuInfo();
        //把数据库取出来的数据装入info类，并返回到前端
        BeanUtils.copyProperties(menuDO, menuInfo);
        return menuInfo;
    }

    /**
     * 列出指定的菜单列表
     *
     * @param ids
     * @return
     */
    @Override
    public List<MainMenu> listMenu(List<Integer> ids) {
        //创建查询器
        QueryWrapper<MenuDO> wrapper = new QueryWrapper<>();
        if (ids != null && ids.size() > 0) {
            //查询指定的id
            wrapper.in("id", ids);
            //先查询一级菜单
            wrapper.eq("pid", "0");
        }

        return listByWapper(wrapper,ids);
    }

    /**
     * 列出所有菜单，只能超级管理员访问，用来做角色编辑
     */
    @Override
    public List<MainMenu> listAllMenu() {
        //创建查询器
        QueryWrapper<MenuDO> wrapper = new QueryWrapper<>();
        //先查询一级菜单
        wrapper.eq("pid", "0");
        return listByWapper(wrapper,null);
    }

    /**
     * 列表指定wapper查询的菜单列表，然后组合好子菜单返回
     */
    private List<MainMenu> listByWapper(QueryWrapper<MenuDO> wrapper,List<Integer> ids) {
        //查询出所有权限内的一级菜单。
        List<MenuDO> result = baseMapper.selectList(wrapper);
        //一菜菜单
        List<MainMenu> ret = new ArrayList<>();
        //循环取子菜单。
        //把结果集 VO 转为info类
        if (result != null && result.size() > 0) {
            for (MenuDO key : result) {
                MainMenu tempSub = new MainMenu();
                BeanUtils.copyProperties(key, tempSub);
                //取出当前菜单的所有子菜单
                MainMenu temp = new MainMenu();
                wrapper = new QueryWrapper<>();
                //接口需要判断权限
                if(null!=ids){
                    //查询指定的id
                    wrapper.in("id", ids);
                }
                //构建条件，取出所有子菜单
                wrapper.eq("pid", key.getId());
                //取出当前主菜单的子菜单列表（pid为当前id）的数据 递归
                List<MainMenu> sub=listByWapper(wrapper,ids);
                if(sub.size()>0){
                    tempSub.setChildren(sub);
                }
                /*List<MenuDO> sub = baseMapper.selectList(wrapper);
                //循环最终菜单，注入到当前菜单类中。
                if (sub != null && sub.size() > 0) {
                    List<MainMenu> allChild = new ArrayList<>();
                    for (MenuDO keys : sub) {
                        MainMenu tempChild = new MainMenu();
                        BeanUtils.copyProperties(keys, tempChild);
                        allChild.add(tempChild);
                    }
                    tempSub.setChildren(allChild);
                }*/


                ret.add(tempSub);
            }
        }
        return ret ;
    }

}
