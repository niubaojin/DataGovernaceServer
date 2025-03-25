package com.synway.datastandardmanager.pojo.enums;

/**
 * 字段类型枚举类
 * @author admin
 */
public enum FieldType {
    INTEGER("integer"),
    FLOAT("float"),
    STRING("string"),
    DATE("date"),
    DATETIME("datetime"),
    LONG("long"),
    DOUBLE("double"),
    VARCHAR("varchar"),
    BLOB("blob");

    private String value = null;

    FieldType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getFieldType(int value) {
        if (value == 0) {
            return FieldType.INTEGER.getValue();
        } else if (value == 1) {
            return FieldType.FLOAT.getValue();
        } else if (value == 2) {
            return FieldType.STRING.getValue();
        } else if (value == 3) {
            return FieldType.DATE.getValue();
        } else if (value == 4) {
            return FieldType.DATETIME.getValue();
        }else if(value == 5){
            return FieldType.BLOB.getValue();
        } else if (value == 6) {
            return FieldType.LONG.getValue();
        } else if (value == 7) {
            return FieldType.DOUBLE.getValue();
        }
        return null;
    }

    // 根据传入的具体字段中文类型获取数字类型
    public static int getFieldTypeNum(String value) {
        if (value.equalsIgnoreCase(FieldType.INTEGER.getValue())) {
            return 0;
        } else if (value.equalsIgnoreCase(FieldType.FLOAT.getValue()) || value.equalsIgnoreCase(FieldType.DOUBLE.getValue())) {
            return 1;
        } else if (value.equalsIgnoreCase(FieldType.STRING.getValue()) || value.equalsIgnoreCase(FieldType.VARCHAR.getValue())) {
            return 2;
        } else if (value.equalsIgnoreCase(FieldType.DATE.getValue())) {
            return 3;
        } else if (value.equalsIgnoreCase(FieldType.DATETIME.getValue())) {
            return 4;
        } else if (value.equalsIgnoreCase(FieldType.BLOB.getValue())){
            return 5;
        }else if (value.equalsIgnoreCase(FieldType.LONG.getValue())) {
            return 6;
        }
        return -1;
    }
}
