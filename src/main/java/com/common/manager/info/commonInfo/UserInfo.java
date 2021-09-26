package com.common.manager.info.commonInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author sucre
 * @since 2020-04-30
 */

public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 登录密码
     */
    @JsonIgnore
    private String pass;

    /**
     * 头像图片路径
     */
    private String pPic;

    /**
     * 登录成功后的token
     */
    private String token;

    /**
     * 是否为管理员
     */
    private Boolean isAdmin;

    /**
     * 创建时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT")
    private LocalDateTime updateTime;

    public String getToken() {
        return token;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
    public String getpPic() {
        return pPic;
    }

    public void setpPic(String pPic) {
        this.pPic = pPic;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "id=" + id +
            ", name=" + name +
            ", roleId=" + roleId +
            ", account=" + account +
            ", pass=" + pass +
            ", pPic=" + pPic +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
