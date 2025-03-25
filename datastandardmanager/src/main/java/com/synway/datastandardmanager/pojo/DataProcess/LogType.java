package com.synway.datastandardmanager.pojo.DataProcess;


/**
 *  操作类型的代码值
 */
public enum LogType {
    YWTC("SJTC001","业务探查"),
    JRRWXJ("SJJR001","接入任务(新建)"),
    JRRWSC("SJJR002","接入任务(删除)"),
    JRRWXG("SJJR003","接入任务(修改)"),
    BZJB("BZGL001","标准建表"),
    BZDJ("BZGL002","标准登记"),
    ZJRWXJ("ZLJC001","质检任务(新建)"),
    ZJRWSC("ZLJC002","质检任务(删除)"),
    ZJRWXG("ZLJC003","质检任务(修改)");

    private String index;
    private String name;

    LogType(String index , String name){
        this.index = index;
        this.name = name;
    }

    public static String getIndexByName(String name){
        for (LogType element : values()) {
            if(element.name == name ){
                return element.index;
            }
        }
        return "";
    }
}
