package com.synway.datastandardmanager.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synway.datastandardmanager.util.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * 操作日志
 */
@Data
@Slf4j
public class OperatorLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private long numId;

    /** 用户操作时的系统日期时间，采用格式YYYYMMDDhhmmss，24小时制格式 */
    private String operateTime;

    /** 访问的IP地址，如：10.118.64.71 */
    private String terminalId;

    /** 操作类型: 0：登录；1：查询；2：新增；3：修改；4：删除；5：登出；6：导出；7：注册；8：建表
     * @see com.synway.datastandardmanager.enums.SysCodeEnum
     */
    private int operateType;

    /** 操作结果:1：成功；0：失败 */
    private String operateResult;

    /**
     * 失败原因代码: 操作成功:0000,操作失败:0001,令牌失效:0002,权限冻结:0003,用户令牌解密失败:1000,
     * 用户令牌不存在:1001,用户令牌不存在:1002,应用令牌不存在:1003,用户令牌不需要续期:1004,应用令牌不需要续期:1005
     */
    private String errorCode;

    /** 功能模块名称:数据接入、数据质检、标准管理、数据仓库、数据探查、数据资产等 */
    private String operateName;

    /** 操作条件(操作描述),如: 用户[张三]在[20230103043623]的时候在[数据接入]模块，新增任务[测试-1] */
    private String operateCondition;

    /** 返回结果:暂时为空 */
    private String display;

    /** 数据敏感等级：默认1 */
    private int dataLevel;

    /** 用户名 */
    private String userName;
    /** 身份证 */
    private String userId;
    /** 原userId */
    private String userNum;
    /** 单位名称 */
    private String organization;
    /** 单位机构号码 */
    private String organizationId;

    private Date createTime;
    private String createTimeStr;
    private Date updateTime;
    /** 是否已经推送 */
    private boolean ifOutSend;

    private static final Random random = new Random();


    public String getCreateTimeStr() {
        if(this.createTime!=null){
            return DateUtil.formatDateTime(this.createTime);
        }
        return createTimeStr;
    }

    public OperatorLog(){
        numId = System.currentTimeMillis() * 10000 + random.nextInt(10000);
        Date now = new Date();
        createTime = now;
        updateTime = now;
        ifOutSend = false;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
        try {
            createTime = DateUtil.parseDate(operateTime, DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE);
        }catch (Exception ex){
            log.error("在转换[{}]时间时出错，错误信息为:",operateTime,ex);
        }
    }

    @Override
    public String toString() {
        return "OperatorLog{" +
                "numId=" + numId +
                ", operateTime='" + operateTime + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", operateType=" + operateType +
                ", operateResult='" + operateResult + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", operateName='" + operateName + '\'' +
                ", operateCondition='" + operateCondition + '\'' +
                ", display='" + display + '\'' +
                ", dataLevel=" + dataLevel +
                '}';
    }

}
