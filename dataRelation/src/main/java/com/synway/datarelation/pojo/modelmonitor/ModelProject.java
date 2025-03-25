package com.synway.datarelation.pojo.modelmonitor;

import com.synway.datarelation.util.DateUtil;

import java.util.Objects;

/**
 * @author
 * @date 2019/4/19 13:51
 */
public class ModelProject {

    private String description;
    private String createTime;
    private String createUser;
    private int isAuto;
    private String modifyTime;
    private String modifyUser;
    private String name;
    private String owner;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public int getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(int isAuto) {
        this.isAuto = isAuto;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelProject that = (ModelProject) o;
        boolean createTimeSame = false;
        try {
            createTimeSame = DateUtil.parseDate(createTime,DateUtil.DEFAULT_PATTERN_DATETIME)
                    .compareTo(DateUtil.parseDate(that.createTime,DateUtil.DEFAULT_PATTERN_DATETIME))==0;
        }catch (Exception e){
            return false;
        }
        return createTimeSame && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(description, createTime, createUser, isAuto, modifyTime, modifyUser, name, owner);
    }
}
