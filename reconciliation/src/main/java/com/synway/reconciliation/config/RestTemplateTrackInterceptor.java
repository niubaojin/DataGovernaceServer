package com.synway.reconciliation.config;

import com.synway.reconciliation.interceptor.AuthorizedUserUtils;
import com.synway.reconciliation.pojo.AuthorizedUser;
import com.synway.reconciliation.util.AesEncryptUtil;
import com.synway.reconciliation.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * RestTemplate拦截
 *
 * @Author
 **/
public class RestTemplateTrackInterceptor implements ClientHttpRequestInterceptor {

    private static Logger logger = LoggerFactory.getLogger(RestTemplateTrackInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        try {
            AuthorizedUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
            if (StringUtils.isNotBlank(authorizedUser.getUserId())) {
                List<String> cookies = new ArrayList<>();
                cookies.add("userId="+ AesEncryptUtil.encrypt(authorizedUser.getUserId()));
                cookies.add("userName="+ AesEncryptUtil.encrypt(authorizedUser.getUserName()));
                cookies.add("isAdmin="+AesEncryptUtil.encrypt(String.valueOf(authorizedUser.getIsAdmin())));
                HttpHeaders httpHeaders = request.getHeaders();
                httpHeaders.add("Cookie", StringUtils.join(cookies, "; "));
            }
        } catch (Exception e) {
            logger.error("在请求中添加用户信息报错" + ExceptionUtil.getExceptionTrace(e));
        }
        return execution.execute(request, body);
    }
}