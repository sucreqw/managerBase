package com.common.manager.service.impl.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.manager.common.PageUtil;
import com.common.manager.dto.commonDTO.RoleDTO;
import com.common.manager.info.commonInfo.RoleInfo;
import com.common.manager.repository.entity.common.RoleDO;
import com.common.manager.repository.dao.commonDao.RoleMapper;
import com.common.manager.service.common.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements IRoleService {
    /**
     * 具体的接口方法实现，增加一条数据
     * @param roleDTO
     * @return
     */
    @Override
    public Integer create(RoleDTO roleDTO) {
        //建立一个Vo
        RoleDO roleDO = new RoleDO();
        //把前端提交的DTO 复制到vo用来操作数据库，以清洗无用数据
        BeanUtils.copyProperties(roleDTO, roleDO);
        //插入数据
        baseMapper.insert(roleDO);
        //返回新增的id
        return roleDO.getId();
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
     * @param roleDTO
     */
    @Override
    public void update(Integer id, RoleDTO roleDTO) {
        //先在数据库取出数据，建立VO
        RoleDO roleDO = baseMapper.selectById(id);
        //判断数据是否存在，不存在返回null
        if (roleDO == null) {
            return;
        }
        //数据存在，把前端提交上来的DTO 复制到Vo,保存到数据库
        BeanUtils.copyProperties(roleDTO, roleDO);
        baseMapper.updateById(roleDO);
    }

    /**
     * 返回分页数据
     * @param page
     * @param pageSize
     * @param query
     * @return
     */
    @Override
    public Page<RoleInfo> listPage(Integer page, Integer pageSize, String query) {
        //创建查询器
        QueryWrapper<RoleDO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query)) {
            wrapper.like("name", query);
        }
        //建立排序条件
        wrapper.orderByDesc("id");
        //建立分页类
        Page<RoleDO> roleDOPage = new Page<>(page, pageSize);
        //把数据装入分页类并返回
        return PageUtil.buildPage(baseMapper.selectPage(roleDOPage, wrapper), RoleInfo.class);
    }

    /**
     * 根据指定id 取数据
     * @param id
     * @return
     */
    @Override
    public RoleInfo get(Integer id) {
        //取出指定id的数据装入Vo
        RoleDO roleDO = baseMapper.selectById(id);
        //不存在数据则返回null
        if (roleDO == null) {
            return null;
        }
        //建立info类，返回数据
        RoleInfo roleInfo = new RoleInfo();
        //把数据库取出来的数据装入info类，并返回到前端
        BeanUtils.copyProperties(roleDO, roleInfo);
        return roleInfo;
    }
}
