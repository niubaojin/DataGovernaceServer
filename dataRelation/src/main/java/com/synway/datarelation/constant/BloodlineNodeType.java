package com.synway.datarelation.constant;

/**
 *   数据血缘节点的类型
 */
public enum BloodlineNodeType {
    DATAWARE("dataware" , "数据探查", 1),
    DATAACCESS("dataaccess" , "数据接入",2),
    DATASTANDARD("datastandard" , "数据处理",3),
    DATAPROCESS("dataprocess" , "数据加工",4),
    OPERATINGSYSTEM("operatingsystem" , "应用系统",5);

    private final String code;
    private final String message;
    private final int num;
    BloodlineNodeType(String code , String message, int num){
        this.code = code;
        this.message = message;
        this.num = num;
    }

    public static String getCodeByNum(int num){
        for (BloodlineNodeType element : values()) {
            if(element.num == num){
                return element.code;
            }
        }
        return "";
    }

    public static int getNumByCode(String code){
        for (BloodlineNodeType element : values()) {
            if(element.code.equalsIgnoreCase(code)){
                return element.num;
            }
        }
        return 0;
    }

    public static String getCodeByName(String name){
        for (BloodlineNodeType element : values()) {
            if(element.message.equalsIgnoreCase(name)){
                return element.code;
            }
        }
        return "";
    }

    public static String getNameByCode(String code){
        for (BloodlineNodeType element : values()) {
            if(element.code.equalsIgnoreCase(code)){
                return element.message;
            }
        }
        return "";
    }
}
