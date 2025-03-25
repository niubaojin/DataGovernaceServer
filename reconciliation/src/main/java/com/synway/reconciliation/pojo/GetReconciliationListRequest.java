package com.synway.reconciliation.pojo;

/**
 * 账单列表查询条件
 * @author ym
 */
public class GetReconciliationListRequest {
    private int pageNum;
    private int pageSize;
    /**
     * 账单环节1.接入2.入库3.标准化接入4.标准化入库
     */
    private int billTache;
    /**
     * 账单类型 1.提供方对账单2.接入方对账单
     */
    private int billType;
    /**
     * 来源数据类型1.数据包2.数据文件3.数据库
     */
    private int dataSourceType;
    /**
     * 对账单编号（账单类型是提供方时就是提供方对账单编号，账单类型是接入方时就是接入方对账单编号 ）
     */
    private String billNo;
    /**
     * 账单类型是提供方时就是提供方编号，账单类型是接入方时就是接入方编号
     */
    private String partyNo;
    /**
     * 数据来源名称
     */
    private String dataSourceName;
    /**
     * 数据来源
     */
    private String sourceLocation;
    /**
     * 数据管理员
     */
    private String administrator;
    /**
     * 数据管理员联系电话
     */
    private String administratorTel;
    /**
     * 起始位置
     */
    private String startNo;
    /**
     * 结束位置
     */
    private String endNo;
    /**
     * 数据大小
     */
    private String dataSize;
    /**
     * 数据资源编号
     */
    private String resourceId;
    /**
     * 接入系统
     */
    private String accessSystem;
    /**
     * 账单状态0.未对账1.对账成功2.对账失败3.已销账
     */
    private Integer billState;
    private String dataTime;
    private String startTime;
    private String endTime;
    private String primaryDatasource;
    private String secondaryDataSource;
    private String primaryOrganization;
    private String firstOrganization;
    private String secondaryOrganization;
    private String factorOne;
    private String factorTwo;
    private int type;
    private String nodeName;
    private String dataName;
    private String userId;
    private String userName;
    private int checkMethod;

    public String getFirstOrganization() {
        return firstOrganization;
    }

    public void setFirstOrganization(String firstOrganization) {
        this.firstOrganization = firstOrganization;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getPrimaryDatasource() {
        return primaryDatasource;
    }

    public void setPrimaryDatasource(String primaryDatasource) {
        this.primaryDatasource = primaryDatasource;
    }

    public String getSecondaryDataSource() {
        return secondaryDataSource;
    }

    public void setSecondaryDataSource(String secondaryDataSource) {
        this.secondaryDataSource = secondaryDataSource;
    }

    public String getPrimaryOrganization() {
        return primaryOrganization;
    }

    public void setPrimaryOrganization(String primaryOrganization) {
        this.primaryOrganization = primaryOrganization;
    }

    public String getSecondaryOrganization() {
        return secondaryOrganization;
    }

    public void setSecondaryOrganization(String secondaryOrganization) {
        this.secondaryOrganization = secondaryOrganization;
    }

    public String getFactorOne() {
        return factorOne;
    }

    public void setFactorOne(String factorOne) {
        this.factorOne = factorOne;
    }

    public String getFactorTwo() {
        return factorTwo;
    }

    public void setFactorTwo(String factorTwo) {
        this.factorTwo = factorTwo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getBillState() {
        return billState;
    }

    public void setBillState(Integer billState) {
        this.billState = billState;
    }

    public int getBillTache() {
        return billTache;
    }

    public void setBillTache(int billTache) {
        this.billTache = billTache;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public int getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(int dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getPartyNo() {
        return partyNo;
    }

    public void setPartyNo(String partyNo) {
        this.partyNo = partyNo;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getAdministratorTel() {
        return administratorTel;
    }

    public void setAdministratorTel(String administratorTel) {
        this.administratorTel = administratorTel;
    }

    public String getStartNo() {
        return startNo;
    }

    public void setStartNo(String startNo) {
        this.startNo = startNo;
    }

    public String getEndNo() {
        return endNo;
    }

    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getAccessSystem() {
        return accessSystem;
    }

    public void setAccessSystem(String accessSystem) {
        this.accessSystem = accessSystem;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(int checkMethod) {
        this.checkMethod = checkMethod;
    }
}
