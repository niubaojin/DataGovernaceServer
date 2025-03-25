package com.synway.datarelation.constant;

/**
 * @author majia
 * @version 1.0
 * @date 2021/1/27 14:26
 */
public enum PrgType {
    IDE_SHELL(6, "SHELL"),
    ODPS_SQL(10, "SQL"),
    ODPS_MR(11, "MR"),
    POL_SYNC(23, "同步"),
    START(99,"START");

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

    PrgType(Integer statusNum , String statusValue ){
        this.statusNum = statusNum;
        this.statusValue = statusValue;
    }

    // 根据数字状态返回中文注释
    //得到中文名称
    public static String getValue(Integer num){
        String returnStr="";
        for (PrgType exp : PrgType.class.getEnumConstants()) {
            if(exp.getStatusNum().equals(num)){
                returnStr=exp.getStatusValue();
                break;
            }
        }
        return returnStr;
    }
}
