package com.synway.dataoperations.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.pojo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Base64;

@Slf4j
public class RestTemplateTrackInterceptor implements ClientHttpRequestInterceptor {
    public static final String LOGIN_USER = "_login_user_";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        try {
            LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
            if(loginUser != null){
                HttpHeaders httpHeaders = request.getHeaders();
                httpHeaders.add(LOGIN_USER, Base64.getEncoder().encodeToString(JSONObject.toJSONString(loginUser).getBytes()));
            }
            // 暂时不进行权限控制
            if(loginUser == null){
                loginUser.setUserId("-1");
                loginUser.setUserName("测试管理员账号");
                loginUser.setUserLevel(1);
                HttpHeaders httpHeaders = request.getHeaders();
                httpHeaders.add(LOGIN_USER, Base64.getEncoder().encodeToString(JSONObject.toJSONString(loginUser).getBytes()));
            }
        }catch (Exception e){
            log.error("在请求中添加用户信息报错：\n"+ ExceptionUtil.getExceptionTrace(e));
        }
        return execution.execute(request,body);
    }

}
