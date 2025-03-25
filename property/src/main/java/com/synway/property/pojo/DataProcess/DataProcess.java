package com.synway.property.pojo.DataProcess;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *  数据历程的实体类
 * @author wdw
 */
public class DataProcess implements Serializable {
    public static final String ODPS = "odps";
    public static final String ADS = "ads";
    public static final String HBASE = "hbase";
    public static final String HIVE = "hive";

    // 如果这个不为null 则会从dubbo接口查询数据
    private Integer userId;

    // 单位编号   地区行政编号（参考标准 附录一）
    private String areaId = " ";
    //  部门   部门名称
    private String dept = " ";
    //  操作人名称
    private String operator = " ";
    //   警号
    private String policeno = " ";
    //   系统代码
    private String appId = " ";
    //   模块代码 (大写)
    @NotNull
    private String moduleId = " ";
    //   操作时间   YYYY-MM-DD HH:mm:ss
    @NotNull
    private String operateTime = "";
    //   IP地址/操作设备地址
    @NotNull
    private String ip = " ";
    //   操作类型代码　(大写)
    @NotNull
    private String logType = " ";
    //  日志摘要   操作行为的简要说明　不同模块对应的格式不同
    @NotNull
    private String digest = " ";

    // 操作类型名称
    private String logTypeStr = " ";
    //   模块代码对应的名称
    private String moduleIdStr = " ";
    // 主键key需要对应的字段信息
    private String dataBaseType = " ";
    // 包括  项目名.表名
    @NotNull
    private String tableNameEn = " ";
    // 表协议ID
    private String tableId = " ";
    // 任务名
    private String taskName = " ";

    private Integer id ;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getLogTypeStr() {
        return logTypeStr;
    }

    public void setLogTypeStr(String logTypeStr) {
        this.logTypeStr = logTypeStr;
    }

    public String getModuleIdStr() {
        return moduleIdStr;
    }

    public void setModuleIdStr(String moduleIdStr) {
        this.moduleIdStr = moduleIdStr;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPoliceno() {
        return policeno;
    }

    public void setPoliceno(String policeno) {
        this.policeno = policeno;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}
