package com.common.manager.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.common.manager.common.UserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime date = LocalDateTime.now();
        Integer userId=UserUtil.getBaseUser()==null?0:UserUtil.getUserId();
        //新增时填充的字段
        metaObject.setValue("createUser",  userId);
        metaObject.setValue("createTime",  date);

        metaObject.setValue("updateUser",  userId);
        metaObject.setValue("updateTime",  date);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Integer userId=UserUtil.getBaseUser()==null?0:UserUtil.getUserId();
        //更新时 需要填充字段
        metaObject.setValue("updateUser",  userId);
        metaObject.setValue("updateTime",  LocalDateTime.now());
    }
}
