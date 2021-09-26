package com.common.manager.dto.commonDTO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sucre
 * @since 2020-04-30
 */

public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 登录密码
     */
    private String pass;


    /**
     * 登录验证码
     */
    private String verify;

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
