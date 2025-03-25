package com.synway.datarelation.pojo.databloodline.impactanalysis;

/**
 * @author wangdongwei
 * @ClassName WorkFlowInformation
 * @description 工作流信息的统计信息
 * @date 2020/12/3 10:06
 */
public class WorkFlowInformation {

    /**
     * 序号
     */
    private int recno;
    /**
     *     关系的层级  1 2 3 这种
      */
    private int levels;

    /**
     * 层级的状态 直接/间接
     */
    private String levelCh;

    // 项目名
    private String projectName;
    // 工作流名称
    private String workFlowName;
    // 工作流的id值
    private String id;
    //  创建时间
    private String createTime;

    public String getLevelCh() {
        return levelCh;
    }

    public void setLevelCh(String levelCh) {
        this.levelCh = levelCh;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public int getRecno() {
        return recno;
    }

    public void setRecno(int recno) {
        this.recno = recno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getWorkFlowName() {
        return workFlowName;
    }

    public void setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
