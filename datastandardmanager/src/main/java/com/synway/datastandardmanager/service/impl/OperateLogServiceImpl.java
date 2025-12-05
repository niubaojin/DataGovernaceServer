package com.synway.datastandardmanager.service.impl;

import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.OperatorLogVO;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.enums.OperateLogFailReasonEnum;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.util.DateUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
     * 数据元管理
     * @param handleType
     * @param operateName
     * @param synlteFieldObject
     */
    public void synlteFieldSuccessLog(OperateLogHandleTypeEnum handleType, String operateName, SynlteFieldEntity synlteFieldObject){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s数据元[%s]，唯一编码是[%s]",
                handleType.getMessage(), synlteFieldObject.getFieldChineseName(), synlteFieldObject.getFieldId());
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void synlteFieldFailLog(){}

    /**
     * 限定词
     * @param handleType
     * @param operateName
     * @param data
     */
    public void fieldDeterminerSuccessLog(OperateLogHandleTypeEnum handleType,String operateName, FieldDeterminerEntity data){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s限定词%s，内部标识符是%s", handleType.getMessage(), data.getDchinseName(), data.getDeterminerId());
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void fieldDeterminerFailLog(){}

    /**
     * 数据标准/数据定义
     * @param handleType
     * @param operateName
     * @param standardObjectManage
     */
    public void standardManageSuccessLog(OperateLogHandleTypeEnum handleType,String operateName, ObjectManageDTO standardObjectManage){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s%s，数据集编码为%s", handleType.getMessage(), standardObjectManage.getObjectPojoTable().getDataSourceName(), standardObjectManage.getTableId());
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void standardManageFailLog(){}

    /**
     * 公共数据项管理
     * @param handleType
     * @param operateName
     * @param groupName
     */
    public void publicDataManageSuccessLog(OperateLogHandleTypeEnum handleType,String operateName, String groupName){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s公共数据项[%s]。", handleType.getMessage(), groupName);
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void publicDataManageFailLog(){}

    /**
     * 建表信息管理
     * @param handleType
     * @param operateName
     * @param objectStoreInfo
     */
    public void objectStoreInfoSuccessLog(OperateLogHandleTypeEnum handleType,String operateName, ObjectStoreInfoEntity objectStoreInfo){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s[%s]在%s的%s下[%s]表", handleType.getMessage(),
                objectStoreInfo.getObjectName(), objectStoreInfo.getResName(), objectStoreInfo.getProjectName(), objectStoreInfo.getTableName());
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void objectStoreInfoFailLog(){}

    /**
     * 标准字典代码集管理
     * @param handleType
     * @param operateName
     * @param fieldcode
     */
    public void elementCodeSuccessLog(OperateLogHandleTypeEnum handleType,String operateName, FieldCodeEntity fieldcode){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s标准字典%s，代码集为%s", handleType.getMessage(), fieldcode.getCodeText(), fieldcode.getCodeId());
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void elementCodeFailLog(OperateLogHandleTypeEnum handleType, OperateLogFailReasonEnum failReason, String operateName, FieldCodeEntity fieldcode){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s标准字典%s，代码集为%s", handleType.getMessage(), fieldcode.getCodeText(), fieldcode.getCodeId());
        String operateCondition = String.format("用户[%s]执行%s[%s]功能失败，原因: %s，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), failReason.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "0", failReason);
    }

    /**
     * 原始字典代码集管理
     * @param handleType
     * @param operateName
     * @param dictionaryPojo
     */
    public void originalDictSuccessLog(OperateLogHandleTypeEnum handleType,String operateName, StandardizeOriginalDictEntity dictionaryPojo){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s原始字典[%s]的[%s]。", handleType.getMessage(), dictionaryPojo.getFacturer(), dictionaryPojo.getDictionaryName());
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void originalDictFailLog(){}

    /**
     * 单位机构管理
     * @param handleType
     * @param operateName
     * @param unitOrganizationPojo
     */
    public void unitOrganizationSuccessLog(OperateLogHandleTypeEnum handleType, String operateName, StandardizeUnitManageEntity unitOrganizationPojo){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s单位机构[%s]，代码为%s。", handleType.getMessage(), unitOrganizationPojo.getUnitName(), unitOrganizationPojo.getUnitCode());
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void unitOrganizationFailLog(){}

    /**
     * 数据要素管理
     * @param handleType
     * @param operateName
     * @param name
     */
    public void synlteElementSuccessLog(OperateLogHandleTypeEnum handleType, String operateName, String name){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s数据要素[%s]。", handleType.getMessage(), name);
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void synlteElementFailLog(){}

    /**
     * 资源标签
     * @param handleType
     * @param operateName
     * @param labelName
     */
    public void labelManageSuccessLog(OperateLogHandleTypeEnum handleType,String operateName, String labelName){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s资源标签[%s]", handleType.getMessage(), labelName);
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void labelManageFailLog(){}

    /**
     * 数据语义管理
     * @param handleType
     * @param operateName
     * @param sameword
     */
    public void semanticTableSuccessLog(OperateLogHandleTypeEnum handleType,String operateName, SameWordEntity sameword){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s数据语义[%s]，语义是[%s]", handleType.getMessage(), sameword.getSameId(), sameword.getWordname());
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void semanticTableFailLog(OperateLogHandleTypeEnum handleType, OperateLogFailReasonEnum failReason, String operateName, SameWordEntity sameword){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String operateLogParam = String.format("%s数据语义[%s]，语义是[%s]", handleType.getMessage(), sameword.getSameId(), sameword.getWordname());
        String operateCondition = String.format("用户[%s]执行%s[%s]功能失败，原因：%s，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), failReason.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "0", failReason);
    }

    /**
     * 数据建表
     * @param handleType
     * @param operateName
     * @param buildTableInfoVo
     */
    public void createTableSuccessLog(OperateLogHandleTypeEnum handleType,String operateName, BuildTableInfoVO buildTableInfoVo){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String projectName = StringUtils.isBlank(buildTableInfoVo.getProjectName()) ? buildTableInfoVo.getSchema() : buildTableInfoVo.getProjectName();
        String operateLogParam = String.format("在%s的%s新建%s，表中文名为%s", buildTableInfoVo.getDataResourceName(), projectName,buildTableInfoVo.getTableName(), buildTableInfoVo.getTableNameCH());
        String operateCondition = String.format("用户[%s]执行%s[%s]功能成功，操作参数为：%s", loginUser.getUserName(), operateName, handleType.getMessage(), operateLogParam);
        sendOperatorLog(loginUser, handleType, operateName, operateCondition, "1", null);
    }
    public void createTableFailLog(OperateLogHandleTypeEnum handleType, OperateLogFailReasonEnum failReason, String operateName, BuildTableInfoVO buildTableInfoVo){
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        String projectName = StringUtils.isBlank(buildTableInfoVo.getProjectName()) ? buildTableInfoVo.getSchema() : buildTableInfoVo.getProjectName();
        String operateLogParam = String.format("在%s的%s新建%s，表中文名为%s", buildTableInfoVo.getDataResourceName(), projectName,buildTableInfoVo.getTableName(), buildTableInfoVo.getTableNameCH());
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
            OperatorLogVO operatorLog = new OperatorLogVO();
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
