package com.synway.datastandardmanager.exceptionhandler;

import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author wangdongwei
 * @date 2021/5/27 9:06
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ServerResponse<String> exceptionHandler(HttpServletRequest req, Exception e){
        log.error("异常报错:"+ ExceptionUtil.getExceptionTrace(e));
        return ServerResponse.asErrorResponse("异常报错:"+e.getMessage());
    }
}
