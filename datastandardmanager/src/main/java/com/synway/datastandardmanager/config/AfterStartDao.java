package com.synway.datastandardmanager.config;

import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.pojo.AuthorizedUser;
import com.synway.datastandardmanager.pojo.LoginUser;
import com.synway.datastandardmanager.scheduler.SwitchFlagQuery;
import com.synway.datastandardmanager.service.SynlteFieldService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AfterStartDao implements CommandLineRunner {
    @Autowired
    private SwitchFlagQuery  switchFlagQuery;

    @Override
    public void run(String... args) throws Exception {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserLevel(1);
        loginUser.setUserId("153");
        loginUser.setUserName("default_admin");
        AuthorizedUserUtils.getInstance().setAuthor(loginUser);

        // 程序启动后的事件
        switchFlagQuery.getApprovalInfoSwitchFlag();
        switchFlagQuery.getDataCenterVersion();

        AuthorizedUserUtils.getInstance().setAuthor(null);
//        try{
////            synlteFieldServiceImpl.updateSimChinese();
//        }catch (Exception e){
//            log.error(ExceptionUtil.getExceptionTrace(e));
//        }
    }
}
