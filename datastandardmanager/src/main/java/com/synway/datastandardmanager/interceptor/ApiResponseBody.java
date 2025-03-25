package com.synway.datastandardmanager.interceptor;

import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 在每个请求返回之前，将 ThreadLocal里面的uuid放到返回体里面
 * @author wangdongwei
 * @date 2021/9/23 11:22
 */
@ControllerAdvice
@Slf4j
public class ApiResponseBody implements ResponseBodyAdvice<ServerResponse> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        try{
            return returnType.getExecutable().getAnnotatedReturnType().getType().getTypeName().contains("ServerResponse");
        }catch (Exception e){
            log.error("判断是否需要切点报错："+ExceptionUtil.getExceptionTrace(e));
            return false;
        }
    }

    @Override
    public ServerResponse beforeBodyWrite(ServerResponse body, MethodParameter returnType, MediaType selectedContentType,
                                          Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                          ServerHttpRequest request, ServerHttpResponse response) {
        try{
//            body.setRequestUuid(AuthorizedUserUtils.getInstance().getAuthor().getRequestUuid());
        }catch (Exception e){
            log.error("返回体中添加uuid报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return body;
    }
}
