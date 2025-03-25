package com.synway.property.config;

import com.synway.property.pojo.datastoragemonitor.NeedAddRealTimeTable;
import com.synway.property.service.UserAuthorityManageService;
import com.synway.common.bean.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/11 22:04
 */
@Aspect
@Component
@Slf4j
public class AopHelper {
    @Autowired
    private UserAuthorityManageService userAuthorityManageServiceImpl;

    @Autowired
    private TransactionUtil transactionUtils;

    @Pointcut("execution(public * com.synway.property.service.impl.DataStorageMonitorIndexServiceImpl.insertOracleAddTable(..)))")
    public void insertOracleAddTable(){

    }

    @Pointcut("execution(public * com.synway.property.service.impl.DataStorageMonitorIndexServiceImpl.delRealTableMonitor(..)))")
    public void delRealTableMonitor(){

    }


    @Around("insertOracleAddTable()")
    public Object insertOracleAddTable(ProceedingJoinPoint pjp) throws Throwable{
        Object returnStr = "";
        TransactionStatus transactionStatus = null;
        try{
            transactionStatus = transactionUtils.begin();
            String methodName = pjp.getSignature().getName();
            returnStr = (Object)pjp.proceed();
            // 这个表示插入成功
//            if(((ServerResponse)returnStr).getStatus() == 1){
//                // 需要去添加权限的信息
//                List<NeedAddRealTimeTable> list = (List<NeedAddRealTimeTable>) pjp.getArgs()[0];
//                String type = (String) pjp.getArgs()[1];
//                userAuthorityManageServiceImpl.addUserAddRealTimeTable(list,type);
//                transactionUtils.commit(transactionStatus);
//            }
        }catch (Exception e){
            if(transactionStatus != null){
                transactionUtils.rollback(transactionStatus);
            }
//            log.error("添加的实时告警表名报错：", e);
            throw new Exception("实时告警表名添加报错："+e.getMessage());
        }
        return returnStr;
    }


    @Around("delRealTableMonitor()")
    public Object delRealTableMonitor(ProceedingJoinPoint pjp) throws Throwable{
        Object returnStr = "";
        TransactionStatus transactionStatus = null;
        try{
            transactionStatus = transactionUtils.begin();
            String methodName = pjp.getSignature().getName();
            returnStr = (Object)pjp.proceed();
            if(((ServerResponse)returnStr).getStatus() == 1){
                // 需要删除的数据
                List<NeedAddRealTimeTable> list = (List<NeedAddRealTimeTable>) pjp.getArgs()[0];
                userAuthorityManageServiceImpl.delUserAddRealTimeTable(list);
                transactionUtils.commit(transactionStatus);
            }
        }catch (Exception e){
            if(transactionStatus != null){
                transactionUtils.rollback(transactionStatus);
            }
//            log.error("删除实时告警表名报错：", e);
            throw new NullPointerException("删除实时告警表名报错："+e.getMessage());
        }
        return returnStr;

    }

}
