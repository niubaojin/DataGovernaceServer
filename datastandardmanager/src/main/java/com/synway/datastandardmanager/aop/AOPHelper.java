package com.synway.datastandardmanager.aop;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.service.BuildTableInfoManageService;
import com.synway.datastandardmanager.service.UserAuthorityService;
import com.synway.datastandardmanager.service.impl.OperateLogServiceImpl;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.datastandardmanager.util.TransactionUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;


/**
 * aop代码
 * @author wangdongwei
 */
@Aspect
@Component
@Slf4j
public class AOPHelper {

    @Autowired
    private BuildTableInfoManageService buildTableInfoManageService;

    @Autowired
    private UserAuthorityService addUserAuthorityData;

    @Autowired
    private TransactionUtils transactionUtils;

    @Autowired
    RestTemplateHandle restTemplateHandle;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;


    @Pointcut("execution(public * com.synway.datastandardmanager.service.impl.DataSetStandardServiceImpl.saveResourceFieldRelation(..)))")
    public void saveResourceFieldRelationService(){

    }

    @Pointcut("execution(public * com.synway.datastandardmanager.service.impl.CreateTableServiceImpl.createHuaWeiTableService(..)))")
    public void createTableHuaWei(){

    }

    @Pointcut("execution(public * com.synway.datastandardmanager.service.impl.CreateTableServiceImpl.buildAdsOrOdpsTable(..)))")
    public void buildAdsOrOdpsTable(){

    }

    /**
     * 20210531 新增了权限控制的内容 如果 ThreadLocal
     * 这个里面存在用户信息，则表示需要把信息插入到 表 USER_AUTHORITY
     * 使用声明式事务控制
     * @param pjp
     */
    @Around("saveResourceFieldRelationService()")
    public Object saveResourceFieldRelationService(ProceedingJoinPoint pjp) throws Throwable{
        Object returnStr = "";
        TransactionStatus transactionStatus = null;
        try{
            transactionStatus = transactionUtils.begin();
            String methodName = pjp.getSignature().getName();
            returnStr = (Object)pjp.proceed();
            ObjectManageDTO standardObjectManage = (ObjectManageDTO) pjp.getArgs()[0];
//
            // 20210531 新增权限控制表，如果存在用户信息，则将信息插入或更新到用户权限表 USER_AUTHORITY 中
            addUserAuthorityData.addUserAuthorityData(standardObjectManage);

            // 发送操作日志
            int operatiteType = standardObjectManage.getOperateType();
            operateLogServiceImpl.standardManageSuccessLog(operatiteType == 2 ? OperateLogHandleTypeEnum.ADD : OperateLogHandleTypeEnum.ALTER, "标准管理", standardObjectManage);

            transactionUtils.commit(transactionStatus);
        }catch (Exception e){
            log.error(">>>>>>保存标准信息报错：", e);
            if(transactionStatus != null){
                transactionUtils.rollback(transactionStatus);
            }
            throw new Exception("保存标准信息后报错："+ e.getMessage());
        }
        return returnStr;
    }

    /**
     * 建表时的切片 需要将信息插入到表 OBJECT_STORE_INFO 中
     * @param pjp
     */
    @Around("buildAdsOrOdpsTable()")
    public Object buildAdsOrOdpsTableAround(ProceedingJoinPoint pjp) throws Throwable{
        String methodName = pjp.getSignature().getName();
        Object returnStr = (Object)pjp.proceed();
        try{
            BuildTableInfoVO buildTableInfoVo = (BuildTableInfoVO) pjp.getArgs()[0];
            log.info(">>>>>>建表成功之后将相关信息保存到OBJECT_STORE_INFO、操作日志入库：" + JSONObject.toJSONString(buildTableInfoVo));
            buildTableInfoManageService.saveObjectStoreInfoFromAliyun(buildTableInfoVo);

            // 发送操作日志
            operateLogServiceImpl.createTableSuccessLog(OperateLogHandleTypeEnum.CREATETABLE, "数据建表", buildTableInfoVo);

        }catch (Exception e){
            log.error(">>>>>>建表时把信息保存到objectStoreInfo报错：", e);
        }
        return returnStr;
    }


    /**
     *  hive 建表的之后把建表信息发送到流程中
     * @param pjp
     */
    @Around("createTableHuaWei()")
    public String createTableHuaWeiAround(ProceedingJoinPoint pjp) throws Throwable{
        String methodName = pjp.getSignature().getName();
        String returnStr = (String)pjp.proceed();
        BuildTableInfoVO buildTableInfoVo = (BuildTableInfoVO) pjp.getArgs()[0];
        try{
            log.info(">>>>>>建表成功之后将相关信息保存到OBJECT_STORE_INFO、操作日志入库");
            buildTableInfoManageService.saveObjectStoreInfoFromHuaWei(buildTableInfoVo);

            // 发送操作日志
            operateLogServiceImpl.createTableSuccessLog(OperateLogHandleTypeEnum.CREATETABLE, "数据建表", buildTableInfoVo);

        }catch (Exception e){
            log.error(">>>>>>建表时把信息保存到objectStoreInfo报错：：{}", e);
        }
        return returnStr;
    }

}
