package com.common.manager.handler;

import com.common.manager.enums.ResultCodeEnum;
import com.common.manager.exception.BizException;
import com.common.manager.info.commonInfo.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public CommonResult handleException(Exception e) {
        logger.error("system exception", e);
        return new CommonResult<>(null, ResultCodeEnum.ERROR.getCode(), ResultCodeEnum.ERROR.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({BizException.class})
    public CommonResult handleException(BizException e) {
        logger.error("biz exception", e);
        return new CommonResult<>(null, e.getCode(), e.getMessage());
    }

}
