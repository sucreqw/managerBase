package com.common.manager.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.manager.dto.commonDTO.RoleDTO;
import com.common.manager.info.commonInfo.RoleInfo;
import com.common.manager.repository.entity.common.RoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2020-04-30
 */
public interface IRoleService extends IService<RoleDO> {
    //新增数据
    Integer create(RoleDTO roleDTO);
    //删除数据
    void delete(Integer id);
    //更新数据
    void update(Integer id, RoleDTO roleDTO);
    //分页数据
    Page<RoleInfo> listPage(Integer page, Integer pageSize, String query);
    //取指定id数据
    RoleInfo get(Integer id);
}
