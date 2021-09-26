package com.common.manager.controller.commonController;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.manager.annotation.RequiredRole;
import com.common.manager.common.RoleConstants;
import com.common.manager.info.commonInfo.CommonResult;
import com.common.manager.info.commonInfo.RoleInfo;
import com.common.manager.service.common.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.common.manager.dto.commonDTO.RoleDTO;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-04-30
 */
@RestController
@RequestMapping("/roleDO")
@RequiredRole(RoleConstants.SYSUSER)
public class RoleController {
    @Autowired
    private IRoleService roleService;

    /**
     * 对接前端接口，新增一条数据
     * @param roleDTO
     */
    @PostMapping("/")
    public CommonResult<Integer> create(@RequestBody RoleDTO roleDTO) {
        CommonResult<Integer> result = new CommonResult<>();
        Integer id = roleService.create(roleDTO);
        result.setData(id);
        return result;
    }
    /**
     * 对接前端接口，更新一条数据
     * @param id
     * @param roleDTO
     */
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {
        roleService.update(id, roleDTO);
    }
    /**
     * 对接前端接口，删除一条数据
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        roleService.delete(id);
    }

    /**
     * 取指定id的数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommonResult<RoleInfo> get(@PathVariable Integer id) {
        CommonResult<RoleInfo> result = new CommonResult<>();
        RoleInfo roleInfo = roleService.get(id);
        result.setData(roleInfo);
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
    public CommonResult<Page<RoleInfo>> listPage(@PathVariable Integer page, @PathVariable Integer pageSize, String query) {
        CommonResult<Page<RoleInfo>> result = new CommonResult<>();
        Page<RoleInfo> list = roleService.listPage(page, pageSize, query);
        result.setData(list);
        return result;
    }
}

