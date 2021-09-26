package com.common.manager.common;

import org.springframework.util.DigestUtils;

public class PasswordEncoderUtil {

    //上线更换密钥！
    public static final String KEY="common_key_2020-common.d";

    public static String encoder(String password){
        return DigestUtils.md5DigestAsHex((password+KEY).getBytes());
    }

    public static boolean eq(String password,String hexPassword){
       return hexPassword.equals(encoder(password));
    }

}
