package com.common.manager.service.impl.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.manager.common.PageUtil;
import com.common.manager.dto.commonDTO.UserDTO;
import com.common.manager.info.commonInfo.UserInfo;
import com.common.manager.repository.entity.common.UserDO;
import com.common.manager.repository.dao.commonDao.UserMapper;
import com.common.manager.service.common.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2020-04-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements IUserService {
    /**
     * 具体的接口方法实现，增加一条数据
     * @param userDTO
     * @return
     */
    @Override
    public Integer create(UserDTO userDTO) {
        //建立一个Vo
        UserDO userDO = new UserDO();
        //把前端提交的DTO 复制到vo用来操作数据库，以清洗无用数据
        BeanUtils.copyProperties(userDTO, userDO);
        //插入数据
        baseMapper.insert(userDO);
        //返回新增的id
        return userDO.getId();
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
     * @param userDTO
     */
    @Override
    public void update(Integer id, UserDTO userDTO) {
        //先在数据库取出数据，建立VO
        UserDO userDO = baseMapper.selectById(id);
        //判断数据是否存在，不存在返回null
        if (userDO == null) {
            return;
        }
        //数据存在，把前端提交上来的DTO 复制到Vo,保存到数据库
        BeanUtils.copyProperties(userDTO, userDO);
        baseMapper.updateById(userDO);
    }

    /**
     * 返回分页数据
     * @param page
     * @param pageSize
     * @param query
     * @return
     */
    @Override
    public Page<UserInfo> listPage(Integer page, Integer pageSize, String query) {
        //创建查询器
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query)) {
            //wrapper.like("card_name", query);
        }
        //建立排序条件
        wrapper.orderByDesc("id");
        //建立分页类
        Page<UserDO> userDOPage = new Page<>(page, pageSize);
        //把数据装入分页类并返回
        return PageUtil.buildPage(baseMapper.selectPage(userDOPage, wrapper), UserInfo.class);
    }

    /**
     * 根据指定id 取数据
     * @param id
     * @return
     */
    @Override
    public UserInfo get(Integer id) {
        //取出指定id的数据装入Vo
        UserDO userDO = baseMapper.selectById(id);
        //不存在数据则返回null
        if (userDO == null) {
            return null;
        }
        //建立info类，返回数据
        UserInfo userInfo = new UserInfo();
        //把数据库取出来的数据装入info类，并返回到前端
        BeanUtils.copyProperties(userDO, userInfo);
        return userInfo;
    }

    /**
     * 根据账号返回用户类，给controller 判断登录用！
     * @param name
     * @return
     */
    @Override
    public UserInfo getUserByName(String name) {
        //创建查询器
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.eq("account", name);
            //取出指定id的数据装入Vo
            UserDO userDO = baseMapper.selectOne(wrapper);
            if(null != userDO){
                //建立info类，返回数据
                UserInfo userInfo = new UserInfo();
                //把数据库取出来的数据装入info类，并返回到前端
                BeanUtils.copyProperties(userDO, userInfo);
                return userInfo;
            }

        }
        return null;
    }
}
