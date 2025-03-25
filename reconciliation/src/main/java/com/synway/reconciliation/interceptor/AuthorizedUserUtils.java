package com.synway.reconciliation.interceptor;

import com.synway.reconciliation.pojo.AuthorizedUser;
import lombok.extern.slf4j.Slf4j;

/**
 * 存储每个请求的 线程id与用户信息
 * @author
 */
@Slf4j
public class AuthorizedUserUtils {
    private static final Object lock = new Object();

    private static AuthorizedUserUtils authorizedUserUtils;

    private ThreadLocal<AuthorizedUser> threadLocal = null;

    public AuthorizedUserUtils() {
        this.threadLocal = new ThreadLocal<AuthorizedUser>(){
            @Override
            protected AuthorizedUser initialValue() {
                return new AuthorizedUser();
            }
        };
    }

    public static AuthorizedUserUtils getInstance(){
        if (authorizedUserUtils == null) {
            synchronized (lock) {
                if (authorizedUserUtils == null) {
                    authorizedUserUtils = new AuthorizedUserUtils();
                }
            }
        }
        return  authorizedUserUtils;
    }

    public AuthorizedUser  getAuthor(){
        return threadLocal.get();
    }

    public void setAuthor(AuthorizedUser author){
        threadLocal.set(author);
    }

    public void removeAuthor(){
        threadLocal.remove();
    }

    public String getUserId(){
        AuthorizedUser authorizedUser = threadLocal.get();
        if(authorizedUser == null){
            return null;
        }
        return authorizedUser.getUserId();
    }
}
