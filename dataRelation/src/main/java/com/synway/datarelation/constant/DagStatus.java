package com.synway.datarelation.constant;

/**
 * @author majia
 * @version 1.0
 * @date 2021/1/27 14:26
 */
public enum DagStatus {
    CREATED(1, "已创建"),
    RUNNING(4, "运行中"),
    FAILURE(5, "运行失败"),
    SUCCESS(6, "运行成功");

    private Integer statusNum;
    private String statusValue;

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public Integer getStatusNum() {
        return statusNum;
    }

    public void setStatusNum(Integer statusNum) {
        this.statusNum = statusNum;
    }

    DagStatus(Integer statusNum , String statusValue ){
        this.statusNum = statusNum;
        this.statusValue = statusValue;
    }

    // 根据数字状态返回中文注释
    //得到中文名称
    public static String getValue(Integer num){
        String returnStr="";
        for (DagStatus exp : DagStatus.class.getEnumConstants()) {
            if(exp.getStatusNum().equals(num)){
                returnStr=exp.getStatusValue();
                break;
            }
        }
        return returnStr;
    }
}
