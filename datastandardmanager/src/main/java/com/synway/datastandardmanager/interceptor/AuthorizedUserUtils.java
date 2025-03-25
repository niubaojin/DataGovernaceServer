package com.synway.datastandardmanager.interceptor;

import com.synway.datastandardmanager.pojo.LoginUser;
import com.synway.datastandardmanager.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 存储每个请求的 线程id与用户信息
 * @author wangdongwei
 * @date 2021/5/27 18:05
 */
@Slf4j
public class AuthorizedUserUtils {
    private static final Object lock = new Object();

    private static AuthorizedUserUtils authorizedUserUtils;

    private ThreadLocal<LoginUser> threadLocal = null;

    public AuthorizedUserUtils() {
        this.threadLocal = new InheritableThreadLocal<LoginUser>(){
            @Override
            protected LoginUser initialValue() {
                return new LoginUser();
            }
        };
    }

    public static AuthorizedUserUtils getInstance(){
        if (authorizedUserUtils == null) {
            synchronized (lock) {
                if (authorizedUserUtils == null) {
                    log.info("程序第一次运行，初始化对象");
                    authorizedUserUtils = new AuthorizedUserUtils();
                }
            }
        }
        return  authorizedUserUtils;
    }

    public LoginUser getAuthor(){
        return threadLocal.get();
    }

    public void setAuthor(LoginUser obj){
        threadLocal.set(obj);
    }

    public void removeAuthor(){
        threadLocal.remove();
    }

    public String getUserId(){
        LoginUser authorizedUser = threadLocal.get();
        if(authorizedUser == null){
            return null;
        }
        return authorizedUser.getUserId();
    }

//    public String getRequestUuid(){
//        try{
//            LoginUser authorizedUser = threadLocal.get();
//            if(authorizedUser == null){
//                return "";
//            }
//            return authorizedUser.getRequestUuid();
//        }catch (Exception e){
//            log.error(ExceptionUtil.getExceptionTrace(e));
//            return "";
//        }
//    }
}
