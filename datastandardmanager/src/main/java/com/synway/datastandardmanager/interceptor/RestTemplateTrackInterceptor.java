package com.synway.datastandardmanager.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.pojo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Base64;

/**
 * http请求的拦截器 如果存在 用户信息，则将这些信息插入到 cookie中
 *
 * @author wdw
 * @version 1.0
 * @date 2021/6/2 13:15
 */
@Slf4j
public class RestTemplateTrackInterceptor implements ClientHttpRequestInterceptor {
    public static final String LOGIN_USER = "_login_user_";

    @Value("${isStartAuthority}")
    boolean isStartAuthority;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        try {
            LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
            if (loginUser != null) {
                HttpHeaders httpHeaders = request.getHeaders();
                httpHeaders.add(LOGIN_USER, Base64.getEncoder().encodeToString(JSONObject.toJSONString(loginUser).getBytes()));
            }
            if (isStartAuthority || (StringUtils.containsIgnoreCase(request.getURI().getPath(), Common.ROOT_URL) && loginUser == null)) {
                loginUser.setUserId("-1");
                loginUser.setUserName("测试管理员账号");
                loginUser.setUserLevel(1);
                HttpHeaders httpHeaders = request.getHeaders();
                httpHeaders.add(LOGIN_USER, Base64.getEncoder().encodeToString(JSONObject.toJSONString(loginUser).getBytes()));
            }
        } catch (Exception e) {
            log.error(">>>>>>在请求中添加用户信息报错：", e);
        }
        return execution.execute(request, body);
    }

}
