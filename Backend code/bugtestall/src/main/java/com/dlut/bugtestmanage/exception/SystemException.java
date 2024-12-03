package com.dlut.bugtestmanage.exception;

import com.dlut.bugtestmanage.enums.AppHttpCodeEnum;

/**
 * 异常类，未检查异常，可以选择捕获和处理，非强制
 */
public class SystemException extends RuntimeException{
    private int code;
    private String msg;
    public int getCode() {
    return code;
    }
    public String getMsg() {
    return msg;
    }
    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}