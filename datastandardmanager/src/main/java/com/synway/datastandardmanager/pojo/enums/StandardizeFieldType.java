package com.synway.datastandardmanager.pojo.enums;

public enum StandardizeFieldType {
    STRING(0 ,2),                             //字符型(string)
    NUMERIC(1,0),                         //数值型(numeric)
    DATE(2 , 3),
    DATETIME(3 ,4),
//    TIMESTAMP(4,-1),
//    TIME(5,-1),
//    BOOLEAN(6,-1),
//    BINARY(7,-1),
    FLOAT(8,1),
    DOUBLE(9,7);
//    BLOB(10,-1),
//    CLOB(11,-1),
//    NCLOB(12,-1);
    private int value = 0;
    private int name=-1;
    StandardizeFieldType(int value,int name) {
        this.value = value;
        this.name=name;
    }

    public void setName(int name) {
        this.name = name;
    }
    public int getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    //得到右边的数据 即标准管理自己定义的数据
    public static int getOwerNum(Class<StandardizeFieldType> classType,int owner){
        int returnStr= -1;
        for (StandardizeFieldType exp : classType.getEnumConstants()) {
            if(exp.getValue() == owner){
                returnStr=exp.getName();
                break;
            }
        }
        return returnStr;
    }
    //标准化程序对应到的数值
    public static int getStandardizeNum(Class<StandardizeFieldType> classType,int str){
        int returnStr= 0;
        for (StandardizeFieldType exp : classType.getEnumConstants()) {
            if(exp.getName() == str){
                returnStr=exp.getValue();
                break;
            }
        }
        return returnStr;
    }

}

