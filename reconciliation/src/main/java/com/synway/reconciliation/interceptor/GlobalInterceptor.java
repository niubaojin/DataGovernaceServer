package com.synway.reconciliation.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.pojo.AuthorizedUser;
import com.synway.reconciliation.util.AesEncryptUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;



import java.lang.reflect.Method;
/**
 *  拦截所有的请求
 *  如果是后台请求，是否也需要增加用户权限的内容
 *  用户id与用户信息存储在　map中
 *  @author
 */
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    @Value("${enableAuthorized}")
    private String enableAuthorized;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception{
        // 获取请求中的相关信息 用户信息是放在 Cookie 里面的
        AuthorizedUser authorizedUser = null;
        try{
            if (handler instanceof HandlerMethod) {
                Method method = ((HandlerMethod) handler).getMethod();
                if (method.isAnnotationPresent(IgnoreSecurity.class)) {
                    return true;
                }
            } else {
                return true;
            }
            authorizedUser = new AuthorizedUser();
            if (Boolean.parseBoolean(enableAuthorized)) {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    switch (cookie.getName()) {
                        case Constants.USER_ID:
                            String userId = AesEncryptUtil.desEncrypt(cookie.getValue());
                            authorizedUser.setUserId(userId);
                            break;
                        case Constants.USER_NAME:
                            String userName = AesEncryptUtil.desEncrypt(cookie.getValue());
                            authorizedUser.setUserName(userName);
                            break;
                        case Constants.IS_ADMIN:
                            String isAdmin = AesEncryptUtil.desEncrypt(cookie.getValue());
                            authorizedUser.setIsAdmin(Boolean.parseBoolean(isAdmin));
                            break;
                        default:
                            break;
                    }
                }
            } else {
                authorizedUser.setUserId("153");
                authorizedUser.setUserName("admin");
                authorizedUser.setIsAdmin(true);
            }
        } catch (Exception e) {
            log.error("获取用户信息报错"+ e.getMessage());
        }
        // 如果请求中的 用户信息为空，则返回报错
        if(authorizedUser == null || StringUtils.isBlank(authorizedUser.getUserId())|| StringUtils.isBlank(authorizedUser.getUserName())
               || authorizedUser.getIsAdmin() == null){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print("用户信息为空，请先登录");
            log.error("请求的requestPath:"+request.getRequestURI()+"用户信息为"+JSONObject.toJSONString(authorizedUser)
                    +"，信息缺失，没法获取数据");
            return false;
        }
        AuthorizedUserUtils.getInstance().setAuthor(authorizedUser);
        return true;
    }

}
