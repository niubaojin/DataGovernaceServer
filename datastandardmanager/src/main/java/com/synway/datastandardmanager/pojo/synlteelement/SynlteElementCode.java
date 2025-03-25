package com.synway.datastandardmanager.pojo.synlteelement;


import org.apache.commons.lang3.StringUtils;

/**
 * @author obito
 * @version 1.0
 * @date
 */
public enum SynlteElementCode {
    /**
     * 数据元编码
     */
    SJYBM("1_1","数据元编码"),
    /**
     *  数据元+数据字典
     */

    SJYSJZD("1_2","数据元+数据字典"),
    /**
     * 公司内部制作
     */

    ISNB("2_1","标准"),
    /**
     *  客户现场制作
     */
    NONB("2_2","私有"),

    /**
     * 人
     */
    PEOPLE("3_1","人员"),

    /**
     * 物
     */
    OBJ("3_2","物"),
    /**
     * 组织
     */

    GROUP("3_3","组织"),
    /**
     * 地
     */

    DI("3_4","地"),
    /**
     * 事
     */

    MATTER("3_5","事"),
    /**
     * 时间
     */

    SJ("3_6","时间"),
    /**
     *  信息
     */
    INFO("3_7","信息");

    private String id;
    private String value;

    SynlteElementCode(String id, String value){
        this.id = id;
        this.value = value;
    }

    public static String getValueById(String id){
        if(StringUtils.isBlank(id)){
            return "";
        }
        for (SynlteElementCode element : values()) {
            if(element.id.equalsIgnoreCase(id)){
                return element.value;
            }
        }
        return id;
    }
}
