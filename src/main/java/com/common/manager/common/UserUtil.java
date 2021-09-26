package com.common.manager.common;


import com.common.manager.info.commonInfo.UserInfo;

public class UserUtil {
    private static final ThreadLocal<UserInfo> USER = new ThreadLocal<>();

    public static Integer getUserId() {
        return USER.get().getId();
    }

    public static UserInfo getBaseUser() {
        return USER.get();
    }

    public static void setCurrUser(UserInfo o) {
        USER.set(o);
    }

}
