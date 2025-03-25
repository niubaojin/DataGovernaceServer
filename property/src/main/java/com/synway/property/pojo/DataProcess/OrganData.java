package com.synway.property.pojo.DataProcess;

import java.io.Serializable;
import java.util.List;

/**
 * @author wdw
 */
public class OrganData implements Serializable{
    private Organ organ;
    private List<Role> role;
    private User user;

    public static class Organ{
        private String cityindex;
        private Integer orderno;
        private Integer organFather;
        // 组织ID
        private Integer organId;
        //  组织名称 部门   部门名称
        private String organName;
        private Integer organlevel;
        private String symbol;
        // 地区行政编号
        private String symbol12;

        public String getCityindex() {
            return cityindex;
        }

        public void setCityindex(String cityindex) {
            this.cityindex = cityindex;
        }

        public Integer getOrderno() {
            return orderno;
        }

        public void setOrderno(Integer orderno) {
            this.orderno = orderno;
        }

        public Integer getOrganFather() {
            return organFather;
        }

        public void setOrganFather(Integer organFather) {
            this.organFather = organFather;
        }

        public Integer getOrganId() {
            return organId;
        }

        public void setOrganId(Integer organId) {
            this.organId = organId;
        }

        public String getOrganName() {
            return organName;
        }

        public void setOrganName(String organName) {
            this.organName = organName;
        }

        public Integer getOrganlevel() {
            return organlevel;
        }

        public void setOrganlevel(Integer organlevel) {
            this.organlevel = organlevel;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol12() {
            return symbol12;
        }

        public void setSymbol12(String symbol12) {
            this.symbol12 = symbol12;
        }
    }

    public static class Role{
        private Integer roleGroupId;
        private Integer roleId;
        private String roleType;
        private String roleValue;

        public Integer getRoleGroupId() {
            return roleGroupId;
        }

        public void setRoleGroupId(Integer roleGroupId) {
            this.roleGroupId = roleGroupId;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public String getRoleType() {
            return roleType;
        }

        public void setRoleType(String roleType) {
            this.roleType = roleType;
        }

        public String getRoleValue() {
            return roleValue;
        }

        public void setRoleValue(String roleValue) {
            this.roleValue = roleValue;
        }
    }

    public static class User{
        private Integer caType;
        private Integer cancel;
        // 用户ID
        private Integer id;
        private String idcard;
        private String isDelete;
        private String isOnLine;
        private Integer leverIndex;
        // 用户名称
        private String loginCode;
        private String loginPass;
        private String mobit;
        //  用户名称 操作人名称
        private String name;
        private Integer organ;
        private String pinHash;
        // 警号
        private String policeNo;
        private Integer policeSort;

        public Integer getCaType() {
            return caType;
        }

        public void setCaType(Integer caType) {
            this.caType = caType;
        }

        public Integer getCancel() {
            return cancel;
        }

        public void setCancel(Integer cancel) {
            this.cancel = cancel;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getIsOnLine() {
            return isOnLine;
        }

        public void setIsOnLine(String isOnLine) {
            this.isOnLine = isOnLine;
        }

        public Integer getLeverIndex() {
            return leverIndex;
        }

        public void setLeverIndex(Integer leverIndex) {
            this.leverIndex = leverIndex;
        }

        public String getLoginCode() {
            return loginCode;
        }

        public void setLoginCode(String loginCode) {
            this.loginCode = loginCode;
        }

        public String getLoginPass() {
            return loginPass;
        }

        public void setLoginPass(String loginPass) {
            this.loginPass = loginPass;
        }

        public String getMobit() {
            return mobit;
        }

        public void setMobit(String mobit) {
            this.mobit = mobit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getOrgan() {
            return organ;
        }

        public void setOrgan(Integer organ) {
            this.organ = organ;
        }

        public String getPinHash() {
            return pinHash;
        }

        public void setPinHash(String pinHash) {
            this.pinHash = pinHash;
        }

        public String getPoliceNo() {
            return policeNo;
        }

        public void setPoliceNo(String policeNo) {
            this.policeNo = policeNo;
        }

        public Integer getPoliceSort() {
            return policeSort;
        }

        public void setPoliceSort(Integer policeSort) {
            this.policeSort = policeSort;
        }
    }

    public Organ getOrgan() {
        return organ;
    }

    public void setOrgan(Organ organ) {
        this.organ = organ;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
