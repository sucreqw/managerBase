package com.common.manager.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.common.manager.annotation.RequiredRole;
import com.common.manager.common.PasswordEncoderUtil;
import com.common.manager.common.RoleConstants;
import com.common.manager.common.UserUtil;
import com.common.manager.enums.ResultCodeEnum;
import com.common.manager.exception.BizException;

import com.common.manager.info.commonInfo.UserInfo;
import com.common.manager.service.common.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    IUserService iUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(response.getHeader("Access-Control-Allow-Origin")==null){
            response.addHeader("Access-Control-Allow-Origin","*");
            response.setHeader("Access-Control-Allow-Methods",  "*");
        }
        //response.addHeader("Access-Control-Request-Headers","uuid");
        if(response.getHeader("Access-Control-Allow-Headers")==null){
            response.addHeader("Access-Control-Allow-Headers","*");
        }
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }
        //通过注解的检查返回可以访问
        if (this.hasPermission(handler, request)) {
            return true;
        }

        //默认情况下，直接返回不可以访问。
        throw new BizException(ResultCodeEnum.AUTH_ERROR);
        //return false;
    }

    /**
     * 是否有权限
     *
     * @param handler
     * @return
     */
    private boolean hasPermission(Object handler, HttpServletRequest request) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiredRole requiredRole = handlerMethod.getMethod().getAnnotation(RequiredRole.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredRole == null) {
                requiredRole = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredRole.class);
            }
            // 如果标记了注解，则判断权限
            if (requiredRole != null && StringUtils.isNotBlank(requiredRole.value())) {
                //可以匿名访问，直接返回true
                if(requiredRole.value().equals(RoleConstants.ANONYMOUS)){return true;}
                //当前用户是否已经登录
                if (this.hasJWT(handler, request)) {
                    //用户已经登录，取出当前用户的id
                    Integer id = UserUtil.getUserId();
                    //根据token里的id取出当前用户信息。
                    UserInfo userInfo = iUserService.get(id);
                    switch (requiredRole.value()) {
                        case RoleConstants.SYSUSER:
                            //当前用户是否为超级管理员，不是则抛出异常。
                            if (userInfo.getAdmin() != true) {//还是直接在user表里 表现出是否为超级管理员吧。
                                throw new BizException(ResultCodeEnum.AUTH_ERROR);
                            }
                            break;
                        case RoleConstants.EVERYONE:
                            //任何人都可以访问，返回true.
                            return true;
                    }
                }
                return true;
            }
        }
        //没注解，或者情况有错误的情况下，不允许访问。
        return false;
    }

    private boolean hasJWT(Object handler, HttpServletRequest request) {
        // redis或数据库 中获取该用户的权限信息 并判断是否有权限
        String token = request.getHeader("X-Auth");
        if (token == null) {
            throw new BizException(ResultCodeEnum.AUTH_ERROR);
        }
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.decode(token);
            Algorithm.HMAC256(PasswordEncoderUtil.KEY).verify(decodedJWT);
        } catch (Exception e) {//SignatureVerificationException
            throw new BizException(ResultCodeEnum.AUTH_ERROR);
        }
        String sysUser = decodedJWT.getClaim("sysUser").asString();
        if (sysUser != null) {
            UserUtil.setCurrUser(JSON.parseObject(sysUser, UserInfo.class));
            UserInfo userInfo = (UserInfo) UserUtil.getBaseUser();
            UserUtil.setCurrUser(userInfo);
        }
        return true;
    }
}
