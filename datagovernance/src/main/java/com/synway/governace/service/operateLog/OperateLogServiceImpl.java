package com.synway.governace.service.operateLog;

import com.synway.governace.enums.OperateLogFailReasonEnum;
import com.synway.governace.enums.OperateLogHandleTypeEnum;
import com.synway.governace.interceptor.AuthorizedUserUtils;
import com.synway.governace.pojo.loginInfo.LoginUser;
import com.synway.governace.pojo.operatorLog.OperatorLog;
import com.synway.governace.util.DateUtil;
import com.synway.governace.util.RestTemplateHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

/**
 * 零信任日志实现类
 */
@Slf4j
@Service
public class OperateLogServiceImpl {

    @Autowired
    private RestTemplateHandle restTemplateHandle;


    /**
     * 数据建表
     * @param handleType
     * @param operateName
     * @param settingName
     */
    public void updateGeneralSettingSuccessLog(OperateLogHandleTypeEnum handleType,String operateName, String settingName){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s通用配置的[%s]", handleType.getMessage(), settingName);
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void updateGeneralSettingFailLog(OperateLogHandleTypeEnum handleType, OperateLogFailReasonEnum failReason, String operateName, String settingName){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s通用配置的[%s]", handleType.getMessage(), settingName);
        String operateCondition = String.format("用户[%s]执行%s[%s]功能失败，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "0", failReason);
    }

    private void sendOperatorLog(LoginUser loginUser,
                                 OperateLogHandleTypeEnum handleType,
                                 String operateName,
                                 String operateCondition,
                                 String OperateResult,
                                 OperateLogFailReasonEnum failReason){
        try{
            OperatorLog operatorLog = new OperatorLog();
            operatorLog.setOperateTime(DateUtil.formatDateTimeSimple(new Date()));
            operatorLog.setTerminalId(loginUser.getIp());
            operatorLog.setOperateType(handleType.getCode());
            operatorLog.setOperateResult(OperateResult);
            if(failReason != null){
                operatorLog.setErrorCode(failReason.getCode());
            }
            operatorLog.setOperateName(operateName);
            operatorLog.setOperateCondition(operateCondition);
            operatorLog.setDataLevel(1);
            operatorLog.setDisplay("");
            operatorLog.setUserName(loginUser.getUserName());
            operatorLog.setUserId(loginUser.getIdCard());
            operatorLog.setUserNum(loginUser.getUserId());

            restTemplateHandle.saveOperatorLog(Collections.singletonList(operatorLog));
        }catch (Exception exception){
            log.error("发送操作日志出现错误:{}", operateCondition, exception);
        }
    }

}
