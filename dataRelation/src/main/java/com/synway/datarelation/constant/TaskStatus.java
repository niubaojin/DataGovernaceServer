package com.synway.datarelation.constant;

public enum TaskStatus {
    NOT_RUN(1, "未运行"),
    WAIT_TIME(2, "等待时间"),
    WAIT_RESOURCE(3, "等待资源"),
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

    TaskStatus(Integer statusNum , String statusValue ){
        this.statusNum = statusNum;
        this.statusValue = statusValue;
    }

    // 根据数字状态返回中文注释
    //得到中文名称
    public static String getValue(Integer num){
        String returnStr="";
        for (TaskStatus exp : TaskStatus.class.getEnumConstants()) {
            if(exp.getStatusNum().equals(num)){
                returnStr=exp.getStatusValue();
                break;
            }
        }
        return returnStr;
    }
}
