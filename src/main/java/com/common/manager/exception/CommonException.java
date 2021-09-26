package com.common.manager.exception;

import com.common.manager.enums.ResultCodeEnum;

/**
 * 通用返回异常类，例如验证码错误，账号密码错误等不需要打印后台错误信息的异常。
 */
public class CommonException {
    private int code;
    private String message;

    public CommonException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonException(ResultCodeEnum resultCodeEnum) {
        this.code=resultCodeEnum.getCode();
        this.message=resultCodeEnum.getMessage();
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
    public void setResult(ResultCodeEnum resultCodeEnum){
        this.code=resultCodeEnum.getCode();
        this.message=resultCodeEnum.getMessage();
    }
    public void setMessage(int code,String message){
        this.code = code;
        this.message = message;
    }
}
