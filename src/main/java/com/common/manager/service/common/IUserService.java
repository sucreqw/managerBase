package com.common.manager.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.manager.dto.commonDTO.UserDTO;
import com.common.manager.info.commonInfo.UserInfo;
import com.common.manager.repository.entity.common.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2020-04-30
 */
public interface IUserService extends IService<UserDO> {
    //新增数据
    Integer create(UserDTO userDTO);
    //删除数据
    void delete(Integer id);
    //更新数据
    void update(Integer id, UserDTO userDTO);
    //分页数据
    Page<UserInfo> listPage(Integer page, Integer pageSize, String query);
    //取指定id数据
    UserInfo get(Integer id);
    //用账号id取数据
    UserInfo getUserByName(String name);
}
