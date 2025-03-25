//package com.synway.datarelation.pojo.datawork.v3;
//
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.synway.datarelation.util.DateDeserializer;
//import com.synway.datarelation.util.DateSerializer;
//import org.codehaus.jackson.map.annotate.JsonSerialize;
//
//import java.util.Date;
//
//public class RelationNode {
//    private Long appId;
//    private Integer createRelation;
//    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
//    @JsonDeserialize(using= DateDeserializer.class)
//    private Date createTime; // 创建时间
//    private String createUser; // 创建人
//    private String data;
//    private Long flowId;
//    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
//    @JsonDeserialize(using= DateDeserializer.class)
//    private Date modifyTime; // 最新修改时间
//    private String modifyUser; // 最新修改人
//    private Long nodeId;
//    private String projectEnv;
//    private Long tenantId;
//    private Integer type;
//
//    public Long getAppId() {
//        return appId;
//    }
//
//    public void setAppId(Long appId) {
//        this.appId = appId;
//    }
//
//    public Integer getCreateRelation() {
//        return createRelation;
//    }
//
//    public void setCreateRelation(Integer createRelation) {
//        this.createRelation = createRelation;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public String getCreateUser() {
//        return createUser;
//    }
//
//    public void setCreateUser(String createUser) {
//        this.createUser = createUser;
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    public Long getFlowId() {
//        return flowId;
//    }
//
//    public void setFlowId(Long flowId) {
//        this.flowId = flowId;
//    }
//
//    public Date getModifyTime() {
//        return modifyTime;
//    }
//
//    public void setModifyTime(Date modifyTime) {
//        this.modifyTime = modifyTime;
//    }
//
//    public String getModifyUser() {
//        return modifyUser;
//    }
//
//    public void setModifyUser(String modifyUser) {
//        this.modifyUser = modifyUser;
//    }
//
//    public Long getNodeId() {
//        return nodeId;
//    }
//
//    public void setNodeId(Long nodeId) {
//        this.nodeId = nodeId;
//    }
//
//    public String getProjectEnv() {
//        return projectEnv;
//    }
//
//    public void setProjectEnv(String projectEnv) {
//        this.projectEnv = projectEnv;
//    }
//
//    public Long getTenantId() {
//        return tenantId;
//    }
//
//    public void setTenantId(Long tenantId) {
//        this.tenantId = tenantId;
//    }
//
//    public Integer getType() {
//        return type;
//    }
//
//    public void setType(Integer type) {
//        this.type = type;
//    }
//}
