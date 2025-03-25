package com.synway.property.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.synway.property.pojo.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import java.lang.reflect.Method;
import java.util.Base64;

/**
 *  拦截所有的  请求，并将token解析为currentUser ,最终放在request
 *  如果是后台请求，是否也需要增加用户权限的内容
 *  用户id与用户信息存储在　map中
 * @author wangdongwei
 */
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {
    /** 用户信息key */
    public static final String LOGIN_USER = "_login_user_";

    @Value("${enableAuthorized}")
    private String enableAuthorized;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        log.info("接收http请求：{}", request.getRequestURI());
        // 用户信息是放在 Cookie 里面的
        LoginUser loginUser = new LoginUser();
        try{
            if (handler instanceof HandlerMethod) {
                Method method = ((HandlerMethod) handler).getMethod();
                if (method.isAnnotationPresent(IgnoreSecurity.class)) {
                    return true;
                }
            } else {
                return true;
            }
            String userCode = request.getHeader(LOGIN_USER);
            //解码
            byte[] bytes = Base64.getDecoder().decode(userCode);
            String userStr = new String(bytes);
            loginUser = JSONObject.parseObject(userStr,LoginUser.class);

        }catch (Exception e){
            log.error("获取用户信息报错:\n", e);
            return false;
        }
//        loginUser.setUserLevel(1);
//        loginUser.setUserId("153");
//        loginUser.setUserName("测试");
        // 如果请求中的 用户信息为空，则返回报错 是否需要重定向到登录页面再说
        if(loginUser == null || StringUtils.isBlank(loginUser.getUserId())){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print("用户信息为空，请先登录");
            log.error("请求的requestPath:"+request.getRequestURI()+"用户信息为"+JSONObject.toJSONString(loginUser) +"，信息缺失，没法获取数据");
            return false;
        }
        AuthorizedUserUtils.getInstance().setAuthor(loginUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex){
        log.info("完成http请求：{}", request.getRequestURI());
    }

}
