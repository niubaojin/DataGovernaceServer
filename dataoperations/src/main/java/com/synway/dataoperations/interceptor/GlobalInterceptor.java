package com.synway.dataoperations.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.dataoperations.pojo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception{
        // 这个获取请求中的相关信息
        // 用户信息是放在 Cookie 里面的
        LoginUser loginUser = new LoginUser();
        try{
            Method method = ((HandlerMethod)handler).getMethod();
            if(method.isAnnotationPresent(IgnoreSecurity.class)){
                return true;
            }
            String userCode = request.getHeader(LOGIN_USER);
            //解码
            byte[] bytes = Base64.getDecoder().decode(userCode);
            String userStr = new String(bytes);
            loginUser = JSONObject.parseObject(userStr,LoginUser.class);
        }catch (Exception e){
            log.error("获取用户信息报错"+ e.getMessage());
        }
//        loginUser.setUserLevel(1);
//        loginUser.setUserId("153");
//        loginUser.setUserName("测试");
        // 如果请求中的 用户信息为空，则返回报错 是否需要重定向到登录页面再说
        if(loginUser == null){
            ServerResponse<String> result= ServerResponse.asErrorResponse("用户信息为空，请先登录");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSONObject.toJSONString(result));
            log.error("请求的requestPath:"+request.getRequestURI()+" ，用户信息缺失，没法获取数据");
            return false;
        }
        AuthorizedUserUtils.getInstance().setAuthor(loginUser);
        return true;
    }

}
