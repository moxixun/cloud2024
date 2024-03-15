package com.atguigu.cloud.exception;

import com.atguigu.cloud.enumeration.ReturnCodeEnum;
import com.atguigu.cloud.response.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class globalExceptionHandle {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//不加该注释前端收到的状态码为200
    @ExceptionHandler(RuntimeException.class)
    public ResultData exceptionHandle(Exception e){

        log.warn("进入全局异常处理器：{}",e.getMessage(), e);

        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());

    }

}
