package com.common.manager.enums;


public enum ResultCodeEnum {

    SUCCESS(200, "success"),
    ERROR(500, "error"),
    ACCOUNT_ERROR(501, "账号或密码错误"),
    PASSWORD_ERROR(502, "账号或密码错误"),
    KAPTCHA_ERROR(505, "验证码错误"),
    AUTH_ERROR(503, "权限异常"),
    FILE_ERROR(504, "读写文件出错");

    private int code;

    private String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
