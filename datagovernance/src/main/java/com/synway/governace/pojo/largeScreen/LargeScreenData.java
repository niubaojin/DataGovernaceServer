package com.synway.governace.pojo.largeScreen;


import lombok.Data;

/**
 * 数据大屏数据信息
 *
 * @author ywj
 * @date 2020/7/23 11:06
 */
@Data
public class LargeScreenData {
    // 模块标识
    private String moduleId;
    // 模块名称
    private String moduleName;
    // 数据信息json串
    private String dataInfo;
    // 创建时间
    private String createTime;

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
