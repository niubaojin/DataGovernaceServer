package com.synway.datastandardmanager.aop;
import com.synway.datastandardmanager.config.TransactionUtils;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.message.RecordService;
import com.synway.datastandardmanager.pojo.DataProcess.DataProcess;
import com.synway.datastandardmanager.pojo.OperatorLog;
import com.synway.datastandardmanager.pojo.StandardObjectManage;
import com.synway.datastandardmanager.service.ObjectStoreInfoService;
import com.synway.datastandardmanager.service.OtherModuleManageService;
import com.synway.datastandardmanager.service.ResourceManageAddService;
import com.synway.datastandardmanager.service.impl.OperateLogServiceImpl;
import com.synway.datastandardmanager.util.DateUtil;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.Date;

/**
 * aop代码
 * @author wangdongwei
 */
@Aspect
@Component
@Slf4j
public class AOPHelper {

    @Autowired()
    @Qualifier("MsgHandlerChain")
    private RecordService recordServiceChain;
    @Autowired
    private ObjectStoreInfoService objectStoreInfoServiceImpl;

    @Autowired
    private ResourceManageAddService resourceManageAddServiceImpl;

    @Autowired
    private OtherModuleManageService otherModuleManageServiceImpl;

    @Autowired
    private TransactionUtils transactionUtils;

    @Autowired
    RestTemplateHandle restTemplateHandle;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    @Pointcut("execution(public * com.synway.datastandardmanager.service.impl.ResourceManageServiceImpl.saveResourceFieldRelationService(..)))")
    public void saveResourceFieldRelationService(){

    }

    @Pointcut("execution(public * com.synway.datastandardmanager.service.impl.DbManageServiceImpl.createHuaWeiTableService(..)))")
    public void createTableHuaWei(){

    }

    @Pointcut("execution(public * com.synway.datastandardmanager.service.impl.DbManageServiceImpl.buildAdsOrOdpsTable(..)))")
    public void buildAdsOrOdpsTable(){

    }

    /**
     * 20210531 新增了权限控制的内容 如果 ThreadLocal
     * 这个里面存在用户信息，则表示需要把信息插入到 表 USER_AUTHORITY
     * 使用声明式事务控制
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("saveResourceFieldRelationService()")
    public Object saveResourceFieldRelationService(ProceedingJoinPoint pjp) throws Throwable{
        Object returnStr = "";
        TransactionStatus transactionStatus = null;
        try{
            transactionStatus = transactionUtils.begin();
            String methodName = pjp.getSignature().getName();
            returnStr = (Object)pjp.proceed();
            StandardObjectManage standardObjectManage = (StandardObjectManage) pjp.getArgs()[0];
            // 如果 moduleName不为空，则将数据添加到表 MODULE_CREATED_OBJECT
            otherModuleManageServiceImpl.addOtherModuleCreated(standardObjectManage);
            /**
             * 20210331 标准保存之后调用质量检测的接口，自动生成检测规则
             * 20240729 chenfei：已与mjn、ckw确认，此功能再需要，屏蔽
             */
//            resourceManagePropertyServiceImpl.createQualityConfigByTableId(standardObjectManage.getTableId());

            // 20210531 新增权限控制表，如果存在用户信息，则将信息插入或更新到用户权限表 USER_AUTHORITY 中
            resourceManageAddServiceImpl.addUserAuthorityData(standardObjectManage);

            // 发送操作日志
            int operatiteType = standardObjectManage.getOperateType();
            operateLogServiceImpl.standardManageSuccessLog(operatiteType == 2 ? OperateLogHandleTypeEnum.ADD : OperateLogHandleTypeEnum.ALTER, "标准管理", standardObjectManage);

            transactionUtils.commit(transactionStatus);
        }catch (Exception e){
            log.error("保存标准信息报错：", e);
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
     * @return
     * @throws Throwable
     */
    @Around("buildAdsOrOdpsTable()")
    public Object buildAdsOrOdpsTableAround(ProceedingJoinPoint pjp) throws Throwable{
        String methodName = pjp.getSignature().getName();
        Object returnStr = (Object)pjp.proceed();
        BuildTableInfoVo buildTableInfoVo = (BuildTableInfoVo) pjp.getArgs()[0];
        try{
            log.info("建表成功之后将相关信息保存到OBJECT_STORE_INFO、操作日志入库");
            objectStoreInfoServiceImpl.saveObjectStoreInfoFromAliyun(buildTableInfoVo);
            // 发送操作日志
            operateLogServiceImpl.createTableSuccessLog(OperateLogHandleTypeEnum.CREATETABLE, "数据建表", buildTableInfoVo);
        }catch (Exception e){
            log.error("保存信息到OBJECT_STORE_INFO或发送操作日志报错：{}", e);
        }
        try{
            String targetDbType = buildTableInfoVo.getDsType();
            DataProcess dataProcess = new DataProcess();
            dataProcess.setDataBaseType(targetDbType);
            dataProcess.setTableNameEn((buildTableInfoVo.getSchema()+"."+buildTableInfoVo.getTableName()).toUpperCase());
            dataProcess.setTableId(buildTableInfoVo.getTableId());
            dataProcess.setModuleId("BZGL");
            dataProcess.setOperateTime(DateUtil.formatDate(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME));
            dataProcess.setLogType("BZGL001");
            dataProcess.setDigest("开始在"+targetDbType+"创建表"+
                    (buildTableInfoVo.getSchema()+"."+buildTableInfoVo.getTableName()).toLowerCase()+",表协议id为"+buildTableInfoVo.getTableId());
            recordServiceChain.sendMessage(dataProcess,buildTableInfoVo.getUserId());
        }catch (Exception e){
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
        return returnStr;
    }


    /**
     *  hive 建表的之后把建表信息发送到流程中
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("createTableHuaWei()")
    public String Around(ProceedingJoinPoint pjp) throws Throwable{
        String methodName = pjp.getSignature().getName();
        String returnStr = (String)pjp.proceed();
        BuildTableInfoVo buildTableInfoVo = (BuildTableInfoVo) pjp.getArgs()[0];
        try{
            log.info("建表成功之后将相关信息保存到OBJECT_STORE_INFO、操作日志入库");
            objectStoreInfoServiceImpl.saveObjectStoreInfoFromHuaWei(buildTableInfoVo);
            // 发送操作日志
            operateLogServiceImpl.createTableSuccessLog(OperateLogHandleTypeEnum.CREATETABLE, "数据建表", buildTableInfoVo);
        }catch (Exception e){
            log.error("保存信息到OBJECT_STORE_INFO或发送操作日志报错：{}", e);
        }
        try{
            String dataBaseType = buildTableInfoVo.getDsType();
            DataProcess dataProcess = new DataProcess();
            dataProcess.setDataBaseType(dataBaseType);
            dataProcess.setTableNameEn((buildTableInfoVo.getProjectName()+"."+buildTableInfoVo.getTableName()).toUpperCase());
            dataProcess.setTableId(buildTableInfoVo.getTableId());
            dataProcess.setModuleId("BZGL");
            dataProcess.setOperateTime(DateUtil.formatDate(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME));
            dataProcess.setLogType("BZGL001");
            dataProcess.setDigest("开始在"+dataBaseType+"中创建表"+
                    (buildTableInfoVo.getProjectName()+"."+buildTableInfoVo.getTableName()).toLowerCase()+",表协议id为"
                    +buildTableInfoVo.getTableId());
            recordServiceChain.sendMessage(dataProcess,buildTableInfoVo.getUserId());
        }catch (Exception e){
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
        return returnStr;
    }
}
