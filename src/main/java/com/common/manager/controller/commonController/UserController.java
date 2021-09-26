package com.common.manager.controller.commonController;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.manager.annotation.RequiredRole;
import com.common.manager.common.PasswordEncoderUtil;
import com.common.manager.common.RoleConstants;
import com.common.manager.common.UserUtil;
import com.common.manager.dto.commonDTO.LoginDTO;
import com.common.manager.dto.commonDTO.PassDTO;
import com.common.manager.dto.commonDTO.UserDTO;
import com.common.manager.enums.ResultCodeEnum;
import com.common.manager.exception.BizException;
import com.common.manager.info.commonInfo.CommonResult;
import com.common.manager.info.commonInfo.UserInfo;
import com.common.manager.service.common.IUserService;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-04-30
 */
@CrossOrigin()
@RestController
@RequestMapping("/userDO")
@RequiredRole(RoleConstants.SYSUSER)
public class UserController {
    @Autowired
    private IUserService userService;


    /**
     * 对接前端接口，新增一条数据
     * @param userDTO
     */
    @PostMapping("/")
    public CommonResult<Integer> create(@RequestBody UserDTO userDTO) {
        CommonResult<Integer> result = new CommonResult<>();
        Integer id = userService.create(userDTO);
        result.setData(id);
        return result;
    }
    /**
     * 对接前端接口，更新一条数据
     * @param id
     * @param userDTO
     */
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        userService.update(id, userDTO);
    }
    /**
     * 对接前端接口，删除一条数据
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    /**
     * 取指定id的数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommonResult<UserInfo> get(@PathVariable Integer id) {
        CommonResult<UserInfo> result = new CommonResult<>();
        UserInfo userInfo = userService.get(id);
        result.setData(userInfo);
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
    public CommonResult<Page<UserInfo>> listPage(@PathVariable Integer page, @PathVariable Integer pageSize, String query) {
        CommonResult<Page<UserInfo>> result = new CommonResult<>();
        Page<UserInfo> list = userService.listPage(page, pageSize, query);
        result.setData(list);
        return result;
    }

    /**
     * 验证 前端提交上来的账号，密码，验证码是否正确
     *
     */

    @RequiredRole(RoleConstants.ANONYMOUS)
    @PostMapping("/login")
    public CommonResult<Object> login(@RequestBody LoginDTO userDTO, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ){

        CommonResult<Object> ret = new CommonResult<>();
        String captchaId = (String) httpServletRequest.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        //清空验证码。
        httpServletRequest.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, null);
        //先检查验证码是否正确
        if (null==captchaId || null==userDTO.getVerify() || !captchaId.equals(userDTO.getVerify())) {
            //验证码错误,直接抛出异常给前端
            throw new BizException(ResultCodeEnum.KAPTCHA_ERROR);
        } else {
            //验证码正确，开始验证账号和密码
            UserInfo userInfo= userService.getUserByName(userDTO.getAccount());
            if(null !=userInfo && userInfo.getPass().equals(userDTO.getPass()) ){
                String token = JWT.create().withClaim("sysUser", JSON.toJSONString(userInfo)).sign(Algorithm.HMAC256(PasswordEncoderUtil.KEY));
                //返回到前端的用户信息类
                //UserInfo userInfo=new UserInfo();
                //userInfo.setName(userInfo.getName());//注入账号名称
                userInfo.setToken(token);//注入token
                //登录成功,返回info类到前端
                //commonException.setMessage(200,loginInfo);
                ret.setData(userInfo);
            }else{
                //账号或者密码错误,直接抛出异常给前端
                throw new BizException(ResultCodeEnum.ACCOUNT_ERROR);
            }

        }

        return ret;
    }

    /**
     *
     * @param passDTO
     * @return
     */
    @RequiredRole(RoleConstants.EVERYONE)
    @PostMapping("/ChangePass")
    public CommonResult<String> ChangePass(@RequestBody PassDTO passDTO){
        //取出当前用户的角色id
        Integer id = UserUtil.getBaseUser().getRoleId();
        CommonResult<String> ret=new CommonResult<>();

        if(id!=null && passDTO!=null && ! passDTO.getOldPass().equals("") && ! passDTO.getNewPass().equals("")){
            UserInfo userInfo=userService.get(id);
            if(passDTO.getOldPass().equals(userInfo.getPass())){
                UserDTO userDTO=new UserDTO();
                BeanUtils.copyProperties(userInfo, userDTO);

                //设置新密码
                userDTO.setPass(passDTO.getNewPass());
                //更新数据库
                userService.update(id,userDTO);
                ret.setData("密码修改成功");
            }else{
                ret.setData("密码验证失败");
            }
        }
        return ret;
    }
}

