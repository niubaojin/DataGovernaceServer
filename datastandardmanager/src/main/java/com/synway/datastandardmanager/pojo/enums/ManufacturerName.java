package com.synway.datastandardmanager.pojo.enums;

import java.util.ArrayList;
import java.util.List;

public enum  ManufacturerName {

    ALL("Source_ALL","全部",0),
    PT("Source_PT","普天",1),
    HZ("Source_HZ","汇智",2),
    SS("Source_SS","三所",3),
    FH("Source_FH","烽火",4),
    SH("Source_SH","三汇",5),
    RA("Source_RA","锐安",6),
    BZX("Source_BZX","部中心",7),
    BZXMQ("Source_BZXMQ","部中心mq",8),
    HK("Source_HK","海康",9);

    private String protocol;//协议
    private String nameZH;//中文名
    private int index;//顺序

    ManufacturerName(String protocol,String nameZH,int index){
        this.protocol=protocol;
        this.nameZH=nameZH;
        this.index=index;
    }

    public static int getIndexByName(String name){
        for (ManufacturerName element : values()) {
            if(element.nameZH.equals(name)){
                return element.index;
            }
        }
        return 0;
    }

    public static String getNameByIndex(int index){
        for (ManufacturerName element : values()) {
            if(element.index == index ){
                return element.nameZH;
            }
        }
        return "全部";
    }
    public static List<String> getAllManufacturerName(){//获取所有的厂商的名字
        List<String> resultList = new ArrayList<String>(10);
        for (ManufacturerName element : values()) {
            resultList.add(element.nameZH);
        }
        return resultList;
    }



}
