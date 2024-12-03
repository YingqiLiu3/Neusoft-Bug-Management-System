package com.dlut.bugtestmanage.handler;

import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.enums.AppHttpCodeEnum;
import com.dlut.bugtestmanage.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * RestControllerAdvice：这是 Spring 特有的一个注解，用于定义全局控制器增强器。
 * 它结合了@ControllerAdvice 和 @ResponseBody，意味着这个类可以处理全局的异常，并且返回的结果会自动序列化为 JSON 格式。
 * 当应用程序中发生异常时，这个类能够拦截异常并进行处理，返回适当地响应给客户端，从而提供用户友好的错误信息而不是默认的错误页面或信息。
 * Slf4j：这是来自 Lombok 的注解，用于自动生成一个 org.slf4j.Logger 实例，名为 log。
 * 通过这个日志实例，可以方便地记录错误信息和调试信息，无需手动创建和配置日志记录器。
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
            // 打印异常信息
            log.error("出现了异常! {}", e);

            // 从异常对象中获取提示信息封装返回
            return ResponseResult.errorResult(e.getCode(), e.getMsg());
        }


        @ExceptionHandler(Exception.class)
        public ResponseResult exceptionHandler(Exception e){
            // 打印异常信息
            log.error("出现了异常! {}", e);

            // 从异常对象中获取提示信息封装返回
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}