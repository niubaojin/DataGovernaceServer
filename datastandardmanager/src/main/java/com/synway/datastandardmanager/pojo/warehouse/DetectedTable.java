package com.synway.datastandardmanager.pojo.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class DetectedTable implements Serializable {

    //数据源ID
    private String resId;
    //探查ID,新增无，更新有
    private String detectId;
    //项目空间或ftp父目录
    private String projectName;

    //-----------------接入方式信息------------------------

    //表/视图英文名/ftp当前目录/当前文件名
    private String tableNameEN;
    //表中文名
    private String tableNameCN;
    //0：未知      1：表 2：视图     11:文件 12：文件夹
    private int tableType;

    //数据库类型
    private String resType;
    //数据源名称
    private String resName;

    //数据中心id
    private String centerId;
    //数据中心名称
    private String centerName;

    //文件系统相关
    //通配符
    private String wildcard;
    //递归子目录
    private int isRecursion;
    //字段分隔符
    private String separator;
    //提供数据格式
    private String dataFormat;
    //提供方式
    private String provideType;

    //--------------------接入服务信息---------------------
    //提供人员
    private String provider;
    //提供厂商
    private String provideUnit;
    //提供人电话
    private String provideTel;
    //提供时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date provideTime;
    //接入服务人
    private String inceptor;
    //接入人服务方
    private String inceptUnit;
    //接入人电话
    private String inceptTel;
    //接入时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date inceptTime;
    //接入说明
    private String inceptDesc;

    //--------------------数据业务信息---------------------
    //获取方式
    private String inceptType;
    //数据分级
    private String dataLevel;
    //行业分类
    private String tradeClassify;
    //业务号段
    private String businessMark;
    //事权单位
    private String routineUnit;
    //事权单位编码
    private String routineCode;
    //事权单位权联系人
    private String routineLinkMan;
    //事权单位权联系电话
    private String routineTel;
    //管理单位名称
    private String manageUnit;
    //管理单位代码
    private String manageUnitCode;
    //数据资源位置
    private String resPostion;
    //存储位置描述
    private String postionDesc;
    //应用系统名称
    private String appName;
    //应用系统编号
    private String appCode;
    //应用系统一级编号
    private String parentAppCode;
    //应用管理单位
    private String appManageUnit;
    //应用建设单位 (来源厂商)
    private String appBuildUnit;
    //来源分级
    private String sourceClassify;
    //来源协议编码
    private String sourceCode;
    //数据资源描述
    private String resDesc;

    //--------------------数据集规模探查---------------------
    //数据存储记录数
    private String recordCounts;
    //数据存储记录数(单位)
    private String recordUnit;
    //增量数据存储记录数
    private String incRecordCounts;
    //增量数据存储记录数((单位))
    private String incRecordUnit;
    //当前存储数据物理大小(
    private String physicalSize;
    //数据存储总量单位
    private String physicalUnit;
    //增量数据存储总量
    private String incPhysicalSize;
    //增量数据存储总量单位
    private String incPhysicalUnit;
    //资源存储周期
    private String storeCycle;
    //资源存储周期单位
    private String storeCycleUnit;
    //资源更新周期
    private String updateCycle;
    //资源更新模式
    private String updateType;
    //资源更新描述
    private String updateDesc;

    //ds_project_table获取--------------------
    //分区表 0：非   1：是
    private int isPartitioned;
    //最后表结构修改时间
    private String lastDDLTime;
    //最后数据操作时间
    private String lastDMLTime;


    //--------------------其他---------------------
    //注册
    private int isRegistered;
    //审批
    private int isApproved;
    //创建时间，新增必无
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    //更新时间,新增必无，更新必有
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;



    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getDetectId() {
        return detectId;
    }

    public void setDetectId(String detectId) {
        this.detectId = detectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTableNameEN() {
        return tableNameEN;
    }

    public void setTableNameEN(String tableNameEN) {
        this.tableNameEN = tableNameEN;
    }

    public String getTableNameCN() {
        return tableNameCN;
    }

    public void setTableNameCN(String tableNameCN) {
        this.tableNameCN = tableNameCN;
    }

    public int getTableType() {
        return tableType;
    }

    public void setTableType(int tableType) {
        this.tableType = tableType;
    }

    public String getWildcard() {
        return wildcard;
    }

    public void setWildcard(String wildcard) {
        this.wildcard = wildcard;
    }

    public int getIsRecursion() {
        return isRecursion;
    }

    public void setIsRecursion(int isRecursion) {
        this.isRecursion = isRecursion;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getProvideType() {
        return provideType;
    }

    public void setProvideType(String provideType) {
        this.provideType = provideType;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvideUnit() {
        return provideUnit;
    }

    public void setProvideUnit(String provideUnit) {
        this.provideUnit = provideUnit;
    }

    public String getProvideTel() {
        return provideTel;
    }

    public void setProvideTel(String provideTel) {
        this.provideTel = provideTel;
    }

    public Date getProvideTime() {
        return provideTime;
    }

    public void setProvideTime(Date provideTime) {
        this.provideTime = provideTime;
    }

    public String getInceptor() {
        return inceptor;
    }

    public void setInceptor(String inceptor) {
        this.inceptor = inceptor;
    }

    public String getInceptUnit() {
        return inceptUnit;
    }

    public void setInceptUnit(String inceptUnit) {
        this.inceptUnit = inceptUnit;
    }

    public String getInceptTel() {
        return inceptTel;
    }

    public void setInceptTel(String inceptTel) {
        this.inceptTel = inceptTel;
    }

    public Date getInceptTime() {
        return inceptTime;
    }

    public void setInceptTime(Date inceptTime) {
        this.inceptTime = inceptTime;
    }

    public String getInceptDesc() {
        return inceptDesc;
    }

    public void setInceptDesc(String inceptDesc) {
        this.inceptDesc = inceptDesc;
    }

    public String getInceptType() {
        return inceptType;
    }

    public void setInceptType(String inceptType) {
        this.inceptType = inceptType;
    }

    public String getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(String dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getTradeClassify() {
        return tradeClassify;
    }

    public void setTradeClassify(String tradeClassify) {
        this.tradeClassify = tradeClassify;
    }

    public String getBusinessMark() {
        return businessMark;
    }

    public void setBusinessMark(String businessMark) {
        this.businessMark = businessMark;
    }

    public String getRoutineUnit() {
        return routineUnit;
    }

    public void setRoutineUnit(String routineUnit) {
        this.routineUnit = routineUnit;
    }

    public String getRoutineCode() {
        return routineCode;
    }

    public void setRoutineCode(String routineCode) {
        this.routineCode = routineCode;
    }

    public String getRoutineLinkMan() {
        return routineLinkMan;
    }

    public void setRoutineLinkMan(String routineLinkMan) {
        this.routineLinkMan = routineLinkMan;
    }

    public String getRoutineTel() {
        return routineTel;
    }

    public void setRoutineTel(String routineTel) {
        this.routineTel = routineTel;
    }

    public String getManageUnit() {
        return manageUnit;
    }

    public void setManageUnit(String manageUnit) {
        this.manageUnit = manageUnit;
    }

    public String getManageUnitCode() {
        return manageUnitCode;
    }

    public void setManageUnitCode(String manageUnitCode) {
        this.manageUnitCode = manageUnitCode;
    }

    public String getResPostion() {
        return resPostion;
    }

    public void setResPostion(String resPostion) {
        this.resPostion = resPostion;
    }

    public String getPostionDesc() {
        return postionDesc;
    }

    public void setPostionDesc(String postionDesc) {
        this.postionDesc = postionDesc;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppManageUnit() {
        return appManageUnit;
    }

    public void setAppManageUnit(String appManageUnit) {
        this.appManageUnit = appManageUnit;
    }

    public String getAppBuildUnit() {
        return appBuildUnit;
    }

    public void setAppBuildUnit(String appBuildUnit) {
        this.appBuildUnit = appBuildUnit;
    }

    public String getSourceClassify() {
        return sourceClassify;
    }

    public void setSourceClassify(String sourceClassify) {
        this.sourceClassify = sourceClassify;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public String getRecordUnit() {
        return recordUnit;
    }

    public void setRecordUnit(String recordUnit) {
        this.recordUnit = recordUnit;
    }

    public String getIncRecordUnit() {
        return incRecordUnit;
    }

    public void setIncRecordUnit(String incRecordUnit) {
        this.incRecordUnit = incRecordUnit;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getRecordCounts() {
        return recordCounts;
    }

    public void setRecordCounts(String recordCounts) {
        this.recordCounts = recordCounts;
    }

    public String getIncRecordCounts() {
        return incRecordCounts;
    }

    public void setIncRecordCounts(String incRecordCounts) {
        this.incRecordCounts = incRecordCounts;
    }

    public String getPhysicalSize() {
        return physicalSize;
    }

    public void setPhysicalSize(String physicalSize) {
        this.physicalSize = physicalSize;
    }

    public String getPhysicalUnit() {
        return physicalUnit;
    }

    public void setPhysicalUnit(String physicalUnit) {
        this.physicalUnit = physicalUnit;
    }

    public String getIncPhysicalSize() {
        return incPhysicalSize;
    }

    public void setIncPhysicalSize(String incPhysicalSize) {
        this.incPhysicalSize = incPhysicalSize;
    }

    public String getIncPhysicalUnit() {
        return incPhysicalUnit;
    }

    public void setIncPhysicalUnit(String incPhysicalUnit) {
        this.incPhysicalUnit = incPhysicalUnit;
    }

    public String getStoreCycle() {
        return storeCycle;
    }

    public void setStoreCycle(String storeCycle) {
        this.storeCycle = storeCycle;
    }

    public String getStoreCycleUnit() {
        return storeCycleUnit;
    }

    public void setStoreCycleUnit(String storeCycleUnit) {
        this.storeCycleUnit = storeCycleUnit;
    }

    public String getUpdateCycle() {
        return updateCycle;
    }

    public void setUpdateCycle(String updateCycle) {
        this.updateCycle = updateCycle;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getUpdateDesc() {
        return updateDesc;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public int getIsPartitioned() {
        return isPartitioned;
    }

    public void setIsPartitioned(int isPartitioned) {
        this.isPartitioned = isPartitioned;
    }

    public String getLastDDLTime() {
        return lastDDLTime;
    }

    public void setLastDDLTime(String lastDDLTime) {
        this.lastDDLTime = lastDDLTime;
    }

    public String getLastDMLTime() {
        return lastDMLTime;
    }

    public void setLastDMLTime(String lastDMLTime) {
        this.lastDMLTime = lastDMLTime;
    }

    public int getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(int isRegistered) {
        this.isRegistered = isRegistered;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
