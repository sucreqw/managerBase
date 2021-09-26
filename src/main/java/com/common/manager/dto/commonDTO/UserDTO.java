package com.common.manager.dto.commonDTO;

public class UserDTO {

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
        private String pass;

        /**
         * 头像图片路径
         */
        private String pPic;

        /**
         * 是否管理员
         */
        private Boolean admin;

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

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
