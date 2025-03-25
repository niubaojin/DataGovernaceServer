package com.synway.datastandardmanager.interceptor;

import com.synway.datastandardmanager.pojo.AuthorizedUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 方法参数解析器
 * 弃用
 * @author wangdongwei
 * @date 2021/5/24 20:25
 */
@Deprecated
public class CurrentUserHandlerMethodArgReslover implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 如果参数注解有 @CurrentUser 且数据类型是 AuthorizedUser
        return parameter.getParameterAnnotation(CurrentUser.class) != null
                && parameter.getParameterType() == AuthorizedUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return (AuthorizedUser)request.getSession().getAttribute("currentUser");
    }
}
